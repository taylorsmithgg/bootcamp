package com.taylorsmith.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.taylorsmith.XML.ClientConfig;
import com.taylorsmith.XML.ConnectionHistory;
import com.taylorsmith.XML.DownloadHistory;
import com.taylorsmith.XML.ServerConfig;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.taylorsmith.cloud.Commands;

public class Client {

	private final ArrayList<String> clientHistory = new ArrayList<>();
	private final ArrayList<String> downloadHistory = new ArrayList<>();

	private static final String LOCAL_FILEPATH = ".\\local\\";
	private final File localPath = new File(LOCAL_FILEPATH);
	private final String host;
    private InputStream clientIn = null;
	private OutputStream clientOut = null;
	private Socket socket = null;
	private final String[] extensions = { ".jpg", ".png", ".gif", ".tif" };

	// Constructor -- sets the host and calls connect
	public Client(String host) {
		this.host = host;
		connect();
	}

	/**
	 * Opens connection to the server, creates local folder, reads developer
	 * name on connect
	 */

    void /**/connect() {
		makeDir(localPath);

		try {
			System.out.println("Attempting to connect to server...");
            int port = 2663;
            socket = new Socket(host, port);
			if (socket.isConnected()) {
				System.out.println("\tConnected to " + socket.getLocalAddress()
						+ ":" + socket.getPort() + "\n");

				clientHistory.add(socket.getInetAddress().toString());
				clientIn = socket.getInputStream();
				clientOut = socket.getOutputStream();

				int character;
				String developer = "";

				// Read the developer name from the server.
				while ((character = clientIn.read()) != Commands.END) {
					developer += (char) character;
				}

				System.out.println(developer);
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}


	// Disconnects the Client's current socket (closes connection)
	public void disconnect() {
		System.out.println("Closing connection...");

		// Logging actions to be completed before closing the connection. Writes
		// out to XML files.

		log();

		System.out.println("\tClosed.");
	}

	/***
	 * Uses JAXB to marshal to XML the connection history, the downloaded file
	 * history, & the configuration files.
	 * 
	 */
    void log() {
		Marshaller marshaller;

		try {
			JAXBContext jaxbConnection = JAXBContext
					.newInstance(ConnectionHistory.class);
			JAXBContext jaxbClientCfg = JAXBContext
					.newInstance(ClientConfig.class);
			JAXBContext jaxbDownload = JAXBContext
					.newInstance(DownloadHistory.class);
			JAXBContext jaxbServerCfg = JAXBContext
					.newInstance(ServerConfig.class);

			// Check if directories exist, if not, then create directory

			File historyDir = new File(".\\history\\");
			if (!historyDir.exists()) {
				historyDir.mkdir();
			}

			File configDir = new File(".\\config\\");
			if (!configDir.exists()) {
				configDir.mkdir();
			}

			/***
			 * client.xml & server.xml should be overwritten in order to keep
			 * them current
			 */

			File clientCfg = new File(".\\config\\client.xml");
			marshaller = jaxbClientCfg.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(new ClientConfig(), clientCfg);

			File serverCfg = new File(".\\config\\server.xml");
			marshaller = jaxbServerCfg.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(new ServerConfig(), serverCfg);

			// both history files should append new data in order to prevent
			// overwriting

			// Connection history

			File connectHistory = new File(".\\history\\history.xml");
			marshaller = jaxbConnection.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			if (!connectHistory.exists()) {
				marshaller.marshal(new ConnectionHistory(clientHistory),
						connectHistory);
			} else {
				FileWriter fileWriter = new FileWriter(connectHistory, true);
				marshaller.marshal(new ConnectionHistory(clientHistory),
						fileWriter);
				fileWriter.close();
			}

			// Download history

			File download = new File(".\\history\\download.xml");
			marshaller = jaxbDownload.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			if (!download.exists()) {
				marshaller.marshal(new DownloadHistory(downloadHistory),
						download);
			} else {
				FileWriter fileWriter = new FileWriter(download, true);
				marshaller.marshal(new DownloadHistory(downloadHistory),
						fileWriter);
				fileWriter.close();
			}
			this.socket.close();
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Checks filePath and creates it if it doesn't exist
	 * 
	 * @param filePath
	 */
	private void makeDir(File filePath) {
		if (!filePath.exists()) {
			System.out.println(filePath.getAbsolutePath() + " created.");
			filePath.mkdir();
		}
	}
	
	/***
	 * Receives the file while communicating with the server through commands
	 * 
	 * @param fileName
	 * @throws IOException
	 * @throws FileNotFoundException
	 */

	public void downloadFile(String fileName) throws IOException {
		clientOut.write(Commands.SEND_FILE);

		clientOut.write((fileName).getBytes());
		clientOut.write(Commands.END);

		int commandByte = clientIn.read();

		System.out.println((char) commandByte);

		// Check to see if file exists, if not throw exception
		if (commandByte == Commands.FILE_EXISTS) {

			String fileLength = "";
			int character;

			// Read file size as String
			while ((character = clientIn.read()) != Commands.END) {
				fileLength += (char) character;
			}

			// Parse string received for long value
			long fileSize = Long.parseLong(fileLength);

			File file = new File(LOCAL_FILEPATH + fileName);

			// Print results for user/developer's benefit			
			System.out.println("\nDownloading " + fileName);
			System.out.println("\tFile size: " + fileSize);

			OutputStream fileOut = new BufferedOutputStream(
					new FileOutputStream(file));

			// Initialize argument variables for BufferedReader
			byte[] buffer = new byte[1024 * 100];
			int currentByte;
			int size = 0;

			// Read buffered input according to buffer
			while (size < fileSize) {
				currentByte = clientIn.read(buffer, 0, buffer.length);
				size += currentByte;
				fileOut.write(buffer, 0, currentByte);
			}

			// Print success message
			System.out.println("\t" + fileName + " downloaded.");

			// Add fileName to downloadHistory to be logged			
			downloadHistory.add(fileName);

			// Flush file stream and close
			fileOut.flush();
			fileOut.close();

		} else if (commandByte == Commands.FILE_DOES_NOT_EXIST) {
			throw new FileNotFoundException();
		} else {
			System.out.println("Unknown command.");
		}
	}

	/***
	 * Retrieves the Server's shared files. Writes LIST_FILES Command and reads
	 * file names to the client
	 * 
	 * @return
	 * @throws IOException
	 */

	public ArrayList<String> getServerFiles() throws IOException {
		ArrayList<String> fileNames = new ArrayList<>();

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

	/***
	 * Retrieves the files stored on the local machine, filtered by
	 * apache-commons SuffixFileFilter to only accept file extensions that are
	 * in the array extensions
	 * 
	 * @return
	 */

	public ArrayList<String> getLocalFiles() {
		File[] localFiles = localPath
				.listFiles((FileFilter) new SuffixFileFilter(extensions,
						IOCase.SENSITIVE));
		ArrayList<String> fileNames = new ArrayList<>();
		for (File files : localFiles) {
			fileNames.add(files.getName());
		}

		return fileNames;
	}

	/***
	 * Retrieve the downloadHistory ArrayList<String> for refresh purposes in
	 * the GUI
	 * 
	 * @return
	 */
	public ArrayList<String> getDownloadHistory() {
        return downloadHistory;
    }
}
