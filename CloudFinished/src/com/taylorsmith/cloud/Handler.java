package com.taylorsmith.cloud;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.taylorsmith.server.Server;

public class Handler implements Runnable {

    private final Server server;
	private final Socket socket;
	private InputStream serverIn;
	private OutputStream serverOut;

    // Constructor sets server and socket for handler and intializes thread for
	// multi-threaded connections. Thread is set to Daemon for efficiency.

	public Handler(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	// "Main" loop of the program, handles input and output streams in
	// designated threads for multiple connections.
	// Maintains the stream to the server by establishing a while loop that
	// reads continuously from the server for commands and executes
	// corresponding actions

	@Override
	public void run() {
		try {
			serverIn = socket.getInputStream();
			serverOut = socket.getOutputStream();

			// Write Developer Name
			serverOut.write(server.developer.getBytes());
			serverOut.write(Commands.END);

            boolean running = true;
            while (true) {
				try {
					int commandByte = serverIn.read();
					System.out.println("Command: " + (char) commandByte);

					if (commandByte == -1) {
						break;
					}

					// Accept command to determine next action
					switch (commandByte) {
					case (Commands.SEND_FILE):
						sendFile();
						break;
					case (Commands.LIST_FILES):
						sendFileList();
						break;
					default:
						System.out.println("Invalid request.");
						break;
					}
				} finally {
					System.out.println("Request processed.");
				}
			}

			System.out.println("Connection closed");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Initializes a File array that calls getServerFileList(), then iterates
	// over the File array to get the names of all the files and write them to
	// the client to display to the user.

	void sendFileList() throws IOException {
		System.out.println("Sending file list...");

		File[] files = server.getServerFileList();

		// Write list of files in the directory to the client.
		for (File file : files) {
			serverOut.write((file.getName() + "\n").getBytes());
		}

		// End of list
		serverOut.write('\r');

		System.out.println("\tFile list sent.\n");
	}

	// Action called when server is asked to send a file.
	// Receives bytes to be interpreted as file name until END is reached.
	// File is cast by concatenating the Server's shared directory to the
	// fileName. Checks if file exists and returns corresponding command (Y).
	// File is written to OutputStream to be read by Client

	void sendFile() throws IOException {
		System.out.println("Sending file...");

		String fileName = "";
		int character;

		// Reads FileInput until reaches END to concatenate String fileName
		while ((character = serverIn.read()) != Commands.END) {
			fileName += (char) character;
		}

		// Use fileName to locate file on Server
		File file = new File(server.serverPath + fileName);

		if (file.exists()) {
			serverOut.write(Commands.FILE_EXISTS);

			// Initialize FileInputStream for the file
			InputStream fileInput = new BufferedInputStream(
					new FileInputStream(file));

			long fileSize = file.length();

			// Write fileSize to the Client
			serverOut.write(Long.toString(fileSize).getBytes());
			serverOut.write(Commands.END);

			// Initialize argument variables before sending file
			byte[] buffer = new byte[1024 * 100];
			int currentByte;

			// Send file, buffered
			while ((currentByte = fileInput.read(buffer)) > 0) {
				serverOut.write(buffer, 0, currentByte);
			}

			// Success message
			System.out.println("\tSize = " + fileSize);
			System.out.println("\tFile sent.");

			fileInput.close();

		// If file does not exist, command is sent to client
		} else if (!file.exists()) {
			serverOut.write(Commands.FILE_DOES_NOT_EXIST);
		}
	}

}
