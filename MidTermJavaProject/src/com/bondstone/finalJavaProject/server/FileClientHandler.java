package com.bondstone.finalJavaProject.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.bondstone.finalJavaProject.Commands;

public class FileClientHandler implements Runnable
{
	private FileShareServer		server;
	private Socket				socket;
	private OutputStream		outputStream;
	private InputStream			inputStream;
	private volatile boolean	run	= true;

	public FileClientHandler(FileShareServer server, Socket socket)
	{
		this.server = server;
		this.socket = socket;

		System.out.println("New Client on " + socket.getInetAddress().getHostAddress());

		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public void run()
	{
		int command;

		try
		{
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			outputStream.write((server.getUserName() + "\n").getBytes());
		}
		catch (IOException e1)
		{
			e1.printStackTrace();

			return;
		}

		while (run)
		{
			try
			{
				System.out.println("Ready");
				command = inputStream.read();

				if (command == -1)
					break;

				System.out.println("Recieved '" + (char) command + "'");

				switch (command)
				{
					case Commands.LIST_FILES:
						sendFileList();
						break;

					case Commands.SEND_FILE:
						sendFile();
						break;

					default:
						break;
				}
			}
			catch (IOException e)
			{
				break;
			}
		}

		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			// Hmmm...
			e.printStackTrace();
		}

		server.clientDisconected(this);
	}

	private void sendFileList() throws IOException
	{
		File[] listOfFiles = server.getListOfFiles();

		for (File file : listOfFiles)
			outputStream.write((file.getName() + "\n").getBytes());

		outputStream.write('\r');
	}

	private void sendFile() throws IOException
	{
		System.out.println("Sending a file");

		String fileName = "";

		int character;

		while ((character = inputStream.read()) != Commands.END)
			fileName += (char) character;

		File file = new File(server.getShareDirectoryLocation() + fileName);

		System.out.println("Sending " + fileName);

		if (file.exists() && file.isFile())
		{
			outputStream.write(Commands.FILE_EXISTS);

			InputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));

			long length = file.length();

			System.out.println("Length = " + length);

			outputStream.write(new Long(length).toString().getBytes());

			outputStream.write(Commands.END);

			byte[] buffer = new byte[1024];

			int numberOfBytesRead;

			while ((numberOfBytesRead = fileInputStream.read(buffer)) > 0)
				outputStream.write(buffer, 0, numberOfBytesRead);

			fileInputStream.close();

			System.out.println("Done");
		}
	}

	public void stop()
	{
		run = false;
	}
}
