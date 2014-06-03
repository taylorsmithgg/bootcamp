package com.cooksystems.bootcamp.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.cooksystems.bootcamp.cloud.Commands;
import com.cooksystems.bootcamp.cloud.Handler;

public class Client {
	public static final int PORT = 2665;
	public static final String FILEPATH = System.getProperty("user.home")
			.concat("\\Documents");
	byte[] buffer;
	String host;
	Socket socket;
	Handler handler;
	boolean running = true;

	public Client(String host) throws IOException {
		this.host = host;
		connect();
	}

	public void connect() throws IOException {
		socket = new Socket(host, PORT);
		// Print Developer Name
		int character;
		while ((character = socket.getInputStream().read()) != Commands.END) {
			System.out.print((char) character);
		}
		System.out.println();

		socket.getOutputStream().write(Commands.SEND_FILE);
	}

	public void downloadFile(InputStream bufferedInput) {
		try {
			System.out.println("Reading file!");
			buffer = new byte[(int) 2.14958e6];
			// Read File Name
			int fileNameByte;
			String fileName = "";
			while ((fileNameByte = bufferedInput.read()) > 0) {
				fileName += (char) fileNameByte;
			}
			System.out.println(fileName);

			FileOutputStream fileOutput = new FileOutputStream(fileName);

			int currentByte;
			while ((currentByte = bufferedInput.read(buffer)) > 0) {
				fileOutput.write(buffer, 0, currentByte);
				System.out.println(currentByte);
				fileOutput.flush();
			}

			fileOutput.close();
		} catch (IOException e) {
			System.out.println("Failed to read file");
			e.printStackTrace();
		}
	}

	public void 
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Stream closed");
		}
	}
}
