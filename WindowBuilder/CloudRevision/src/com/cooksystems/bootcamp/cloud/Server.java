3package com.cooksystems.bootcamp.cloud;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.SwingWorker;

public class Server{
	public static final int PORT = 2663;
	public static final String DEVELOPER = "Taylor Smith";
	ServerSocket serverSocket;
	Socket clientSocket;
	Handler handler = new Handler();
	BufferedInputStream bufferedInput;
	BufferedOutputStream bufferedOutput;

	public Server(){
		try {
			System.out.println("Binding");
			serverSocket = new ServerSocket(PORT);
			System.out.println("\t Bound");
			
			System.out.println("Listening...");
			clientSocket = serverSocket.accept();
			System.out.println(clientSocket.getInetAddress() + " connected!");
			// Initialize Streams
			InputStream serverInput = clientSocket.getInputStream();
			OutputStream serverOutput = clientSocket.getOutputStream();
			BufferedInputStream bufferedInput = new BufferedInputStream(
					serverInput);
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(
					serverOutput);
			
			// Write Developer Name
			serverOutput.write(DEVELOPER.getBytes());
			serverOutput.write(Commands.END);
			serverOutput.close();
			
			while (true) {

				// Check for command being sent
				System.out.println(bufferedInput.read(handler.buffer));
					switch (bufferedInput.read(handler.buffer)) {
					case (Commands.FILE_EXISTS):
						handler.readFile(bufferedInput);
					case (Commands.SEND_FILE):
						File f = new File(handler.FILEPATH + "\\githubsetup.exe");
						handler.sendFile(f, bufferedOutput);
					case (Commands.LIST_FILES):
						handler.listFiles();
					default:
						System.out.println("Invalid request.");
				}

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			serverSocket.close();
			System.out.println("Server closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
