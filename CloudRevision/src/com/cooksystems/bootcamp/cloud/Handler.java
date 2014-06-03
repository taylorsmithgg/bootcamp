package com.cooksystems.bootcamp.cloud;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.cooksystems.bootcamp.client.Client;
import com.cooksystems.bootcamp.server.Server;

public class Handler implements Runnable {
	Client client;

	public String localhost = "localhost";
	public String Tyler = "10.1.1.238";
	public static final String FILEPATH = System.getProperty("user.home")
			.concat("\\shared");
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
					System.out.println("commandByte: " + (char) commandByte);

					if (commandByte == -1) {
						break;
					}

					switch (commandByte) {
					case (Commands.FILE_EXISTS):
						System.out.println("File exists");
						break;
					case (Commands.SEND_FILE):
						File f = new File(FILEPATH + "\\githubsetup.exe");
						sendFile(f);
						break;
					case (Commands.LIST_FILES):
						listFiles();
					default:
						System.out.println("Invalid request.");
						break;
					}
				} finally {
					System.out.println("Request Complete!");
				}
			}

			System.out.println("Connection closed");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendFile(File f) {
		System.out.println("Writing file");
		try {
				bufferedOut = new BufferedOutputStream(serverOut);
				byte[] buffer = new byte[1024 * 100];
				FileInputStream fileInputStream = new FileInputStream(f);
				buffer = new byte[(int) f.length()];
				bufferedOut.write(f.getName().getBytes());

				int currentByte;
				
				while ((currentByte = fileInputStream.read()) > 0) {
					bufferedOut.write(buffer, 0, currentByte);
					System.out.println(currentByte);
					bufferedOut.flush();
				}

				System.out.println(f + " successfully written.");
				fileInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
	}

	public void listFiles() {

			try {
				File filePath = new File(FILEPATH);
				File[] fileList = filePath.listFiles();
				for(File f : fileList){
				serverOut.write(f.getName().getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	public String[] listServers() {
		String[] result = { localhost, Tyler };
		return result;
	}

	public Server getServer() {
		return server;
	}
}
