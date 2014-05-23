package com.bondstone.finalJavaProject.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.bondstone.finalJavaProject.Commands;
import com.bondstone.finalJavaProject.UnknownCommandException;

public class FileShareClient
{
	private int				port;
	private Socket			socket			= null;
	private InputStream		inputStream		= null;
	private OutputStream	outputStream	= null;
	private String			downloadDirectoryLocation;

	public FileShareClient(int port, String downloadDirectoryLocation)
	{
		this.port = port;
		this.downloadDirectoryLocation = downloadDirectoryLocation;

		File downloadDirectory = new File(downloadDirectoryLocation);

		if (!downloadDirectory.exists())
			downloadDirectory.mkdir();
	}

	public String connect(String ipAddress) throws UnknownHostException, IOException
	{
		socket = new Socket(ipAddress, port);

		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();

		System.out.println("Connected, waiting for name...");

		int character;

		String developerName = "";

		while ((character = inputStream.read()) != Commands.END)
		{
			developerName += (char) character;

			System.out.println("Recieved: " + character);
		}

		System.out.println("Name = " + developerName);

		return developerName;
	}

	public ArrayList<String> getListOfFiles() throws NoConnectionException, IOException
	{
		checkForNoConnection();

		ArrayList<String> listOfFiles = new ArrayList<>();

		outputStream.write(Commands.LIST_FILES);

		int character;

		while ((character = inputStream.read()) != Commands.END_OF_LIST)
		{
			String fileName = "" + (char) character;

			while ((character = inputStream.read()) != Commands.END)
				fileName += (char) character;

			System.out.println("Recieved: '" + fileName + "'");

			listOfFiles.add(fileName);
		}

		return listOfFiles;
	}

	public void downloadFile(String filename) throws NoConnectionException, IOException, UnknownCommandException, FileNotFoundException
	{
		checkForNoConnection();

		outputStream.write(Commands.SEND_FILE);

		outputStream.write(filename.getBytes());

		outputStream.write(Commands.END);

		int character;

		character = inputStream.read();

		System.out.println("Server responded with " + (char) character);

		if (character == Commands.FILE_EXISTS)
		{
			String lengthString = "";

			while ((character = inputStream.read()) != Commands.END)
				lengthString += (char) character;

			long length = 0;

			try
			{
				length = Long.parseLong(lengthString);
			}
			catch (NumberFormatException e)
			{
				throw new UnknownCommandException('\0');
			}

			File file = new File(downloadDirectoryLocation + "\\" + filename);

			file.createNewFile();

			OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(file));

			byte[] buffer = new byte[1024];

			int numberOfBytesRead;
			long totalNumberOfBytesRead = 0;

			while (totalNumberOfBytesRead < length
					&& (numberOfBytesRead = inputStream.read(buffer, 0, Math.min((int) (length - totalNumberOfBytesRead), buffer.length))) > 0)
			{
				fileOutputStream.write(buffer, 0, numberOfBytesRead);

				totalNumberOfBytesRead += numberOfBytesRead;
			}

			fileOutputStream.close();
		}
		else if (character == Commands.FILE_DOES_NOT_EXIST)
			throw new FileNotFoundException(filename);
		else
			throw new UnknownCommandException((char) character);
	}

	public void disconnect() throws NoConnectionException, IOException
	{
		checkForNoConnection();

		socket.close();
	}

	private void checkForNoConnection() throws NoConnectionException
	{
		if (socket == null || inputStream == null || outputStream == null || !socket.isConnected())
			throw new NoConnectionException();
	}
}
