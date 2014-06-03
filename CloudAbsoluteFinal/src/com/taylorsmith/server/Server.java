package com.taylorsmith.server;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.taylorsmith.cloud.Handler;

public class Server implements Runnable {

	public static final String DEVELOPER = "Taylor Smith";
	public static final String SERVER_FILEPATH = ".\\shared\\";
	public static final int PORT = 2663;

	ServerSocket serverSocket;
	Socket clientSocket;

	public ArrayList<String> history = new ArrayList<String>();

	private String[] extensions = { ".jpg", ".png", ".gif", ".tif" };
	public File file;

	public Server() {
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	// Filter Files by extension using apache-commons SuffixFilFilter
	public File[] getServerFileList() {
		return file.listFiles((FileFilter) new SuffixFileFilter(extensions,
				IOCase.SENSITIVE));
	}

	// Listens for incoming connections and establishes link appropriately
	// Adds client address to history ArrayList for logging
	//
	@Override
	public void run() {

		this.file = new File(SERVER_FILEPATH);

		if (!file.exists()) {
			file.mkdir();
		}

		try {
			System.out.println("Binding...");

			serverSocket = new ServerSocket(PORT);

			System.out.println("Server bound to port: "
					+ serverSocket.getLocalPort() + "\n");

			while (true) {
				System.out.println("Listening...");

				clientSocket = serverSocket.accept();

				System.out
						.println("\tClient connected from "
								+ clientSocket.getInetAddress()
										.getHostAddress() + "\n");

				history.add(clientSocket.getInetAddress().getHostAddress());

				new Handler(this, clientSocket);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				System.out.println("Server closed unexpectedly");
				e.printStackTrace();
			}
		}

	}
}