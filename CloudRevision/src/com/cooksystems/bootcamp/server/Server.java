package com.cooksystems.bootcamp.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.cooksystems.bootcamp.cloud.Handler;

public class Server implements Runnable {
	public static final int PORT = 2665;
	public static final String DEVELOPER = "Taylor Smith";
	public ServerSocket serverSocket;
	public Socket clientSocket;
	public BufferedInputStream bufferedInput;
	public BufferedOutputStream bufferedOutput;
	public Handler handler;

	public Server() {
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	public void close() {
		try {
			serverSocket.close();
			System.out.println("Server closed on " + Thread.currentThread().getName());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while(true){
			System.out.println("Binding");
			serverSocket = new ServerSocket(PORT);
			System.out.println("\t Bound");

			System.out.println("Listening...");
			clientSocket = serverSocket.accept();
			System.out.println(clientSocket.getInetAddress() + " connected!");
			
			handler = new Handler(this, clientSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}