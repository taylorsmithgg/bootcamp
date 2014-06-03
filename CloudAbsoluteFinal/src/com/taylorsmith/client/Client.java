package com.taylorsmith.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.taylorsmith.cloud.Commands;

public class Client {

	public static ArrayList<String> downloadHistory = new ArrayList<String>();
	public static final String LOCAL_FILEPATH = ".\\local\\";
	public static final String DOWNLOAD_FILEPATH = ".\\download\\";
	File filePath = new File(LOCAL_FILEPATH);
	private String developer;
	private String host;
	private int port = 2663;
	private InputStream clientIn = null;
	private OutputStream clientOut = null;
	Socket socket = null;
	private String[] extensions = { ".jpg", ".png", ".gif", ".tif" };

	// Constructor -- sets the host and connects the Client to Server
	public Client(String host) {
		this.host = host;
		connect();
	}

	public void connect() {
		System.out.println(filePath.getAbsolutePath());
		if (!filePath.exists()) {
			System.out.println(filePath.getAbsolutePath() + " created.");
			filePath.mkdir();
		}

		try {
			System.out.println("Attempting to connect to server...");
			socket = new Socket(host, port);
			if (socket.isConnected()) {
				System.out.println("\tConnected to " + socket.getLocalAddress()
						+ ":" + socket.getPort() + "\n");

				clientIn = socket.getInputStream();
				clientOut = socket.getOutputStream();

				int character;

				// Read the developer name from the server.
				while ((character = clientIn.read()) != Commands.END) {
					developer += (char) character;
				}

				System.out.println(developer);

				clientOut.write(Commands.END + Commands.END);
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void disconnect() throws IOException {
		System.out.println("Closing connection...");
		this.socket.close();
		System.out.println("\tClosed.");
	}

	public ArrayList<String> getServerFiles() throws IOException {
		ArrayList<String> fileNames = new ArrayList<String>();

		// Tell the server to send the list of files.
		clientOut.write(Commands.LIST_FILES);

		int character;

		// Nested while loop to iterate each file name by character and combine
		// them into full file names to be returned as an Array List
		while ((character = clientIn.read()) != Commands.END_OF_LIST) {
			String fileName = "" + (char) character;

			// Reads the current line
			while ((character = clientIn.read()) != Commands.END) {
				fileName += (char) character;
			}
			fileNames.add(fileName);
		}

		return fileNames;
	}

	public ArrayList<String> getLocalFiles() {
		File[] localFiles = filePath
				.listFiles((FileFilter) new SuffixFileFilter(extensions,
						IOCase.SENSITIVE));
		ArrayList<String> fileNames = new ArrayList<String>();
		for (File files : localFiles) {
			fileNames.add(files.getName());
		}

		return fileNames;
	}

	public ArrayList<String> getDownloadHistory() {
		return downloadHistory;
	}	
	
	public void downloadFile(String fileName) throws IOException,
			FileNotFoundException {
		clientOut.write(Commands.SEND_FILE);

		clientOut.write((fileName).getBytes());
		clientOut.write(Commands.END);

		int commandByte = clientIn.read();

		System.out.println((char) commandByte);

		if (commandByte == Commands.FILE_EXISTS) {

			String fileLength = "";
			int character;

			while ((character = clientIn.read()) != Commands.END) {
				fileLength += (char) character;
			}

			long fileSize;

			fileSize = Long.parseLong(fileLength);

			File file = new File(DOWNLOAD_FILEPATH + fileName);

			System.out.println("\nDownloading " + fileName);
			System.out.println("\tFile size: " + fileSize);

			OutputStream output = new BufferedOutputStream(
					new FileOutputStream(file));

			byte[] buffer = new byte[1024 * 100];
			int bRead = 0;
			int size = 0;

			while (size < fileSize) {
				bRead = clientIn.read(buffer, 0, buffer.length);
				size += bRead;
				output.write(buffer, 0, bRead);

			}

			System.out.println("\t" + fileName + " downloaded.");

			downloadHistory.add(fileName);

			output.flush();
			output.close();

		} else if (commandByte == Commands.FILE_DOES_NOT_EXIST) {
			throw new FileNotFoundException();
		} else {
			System.out.println("Unknown command.");
		}

	}

}
