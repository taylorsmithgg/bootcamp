package com.taylorsmith.cloud;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.taylorsmith.XML.ClientConfig;
import com.taylorsmith.XML.ConnectionHistory;
import com.taylorsmith.XML.DownloadHistory;
import com.taylorsmith.XML.ServerConfig;
import com.taylorsmith.client.Client;
import com.taylorsmith.server.Server;

public class Handler implements Runnable {
	Client client;

	public String localhost = "localhost";
	public static String Tyler = "10.0.0.9";
	byte[] buffer = new byte[1024];
	Server server;
	Socket socket;
	InputStream serverIn;
	OutputStream serverOut;
	BufferedInputStream bufferedIn;
	BufferedOutputStream bufferedOut;

	boolean running = true;

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
			serverOut.write(Server.DEVELOPER.getBytes());
			serverOut.write(Commands.END);

			while (running) {
				try {
					int commandByte = serverIn.read();
					System.out.println("Command: " + (char) commandByte);

					if (commandByte == -1) {
						break;
					}

					switch (commandByte) {
					case (Commands.SEND_FILE):
						sendFile();
						break;
					case (Commands.LIST_FILES):
						sendFileList();
					case (Commands.END + Commands.END):
						File file = new File("history.xml");
						Marshaller marshaller;
						try {
							JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionHistory.class);
							marshaller = jaxbContext.createMarshaller();
							marshaller.marshal(new ConnectionHistory(client.getDownloadHistory()), file);
							marshaller.marshal(new ConnectionHistory(client.getDownloadHistory()), System.out);
							
							marshaller.marshal(new ClientConfig(), System.out);
							marshaller.marshal(new DownloadHistory(), System.out);
							marshaller.marshal(new ServerConfig(), System.out);
							
						} catch (JAXBException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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

	public void sendFileList() throws IOException {
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

	// Receives bytes to be interpreted as file name until END is reached
	// File is cast by concatenating the Server's shared directory to the
	// fileName
	public void sendFile() throws IOException {
		System.out.println("Sending file...");

		String fileName = "";
		int character;

		while ((character = serverIn.read()) != Commands.END) {
			fileName += (char) character;
		}

		File file = new File(Server.SERVER_FILEPATH + fileName);

		if (file.exists()) {
			serverOut.write(Commands.FILE_EXISTS);

			InputStream fileInput = new BufferedInputStream(
					new FileInputStream(file));

			long fileSize = file.length();

			serverOut.write(new Long(fileSize).toString().getBytes());
			serverOut.write(Commands.END);

			byte[] buffer = new byte[1024 * 100];
			int bRead;

			while ((bRead = fileInput.read(buffer)) > 0) {
				serverOut.write(buffer, 0, bRead);
			}

			System.out.println("\tSize = " + fileSize);
			System.out.println("\tFile sent.");

			fileInput.close();

		} else if (!file.exists()) {
			serverOut.write(Commands.FILE_DOES_NOT_EXIST);
		}
	}

}
