package com.cooksystems.bootcamp.cloud;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingWorker;

import org.apache.commons.io.IOUtils;

public class Client extends SwingWorker<Void, Void> {
	public static final int PORT = 2663;
	public static final String FILEPATH = System.getProperty("user.home")
			.concat("\\Documents");
	Handler handler = new Handler();
	byte[] buffer = new byte[1024 * 100];
	String host;
	Socket clientSocket;

	public Client() {
		try {
			clientSocket = new Socket(handler.localhost, PORT);

			// Initialize Streams
			InputStream clientInput = clientSocket.getInputStream();
			OutputStream clientOutput = clientSocket.getOutputStream();
			BufferedInputStream bufferedInput = new BufferedInputStream(
					clientInput);
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(
					clientOutput);

			// Print Developer Name
			int character;
			while ((character = clientInput.read()) != Commands.END) {
				System.out.print((char) character);
			}
			System.out.println(bufferedInput.read(buffer));
			System.out.println();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	}

	@Override
	protected Void doInBackground() throws Exception {


	public void close() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Stream closed");
		}
	}
	
	public static void main(String[] args) {
		new Client();
	}
}
