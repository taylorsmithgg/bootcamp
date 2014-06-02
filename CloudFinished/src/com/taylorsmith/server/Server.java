package com.taylorsmith.server;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.taylorsmith.XML.ClientConfig;
import com.taylorsmith.XML.ServerConfig;
import com.taylorsmith.cloud.Handler;

public class Server implements Runnable {

	public String developer;
	public String serverPath;
	public int port;
	public ArrayList<String> hostList = new ArrayList<String>();

	public static final String CHRISTOPHER = "192.168.20.111";
	public static final String CHRIS = "192.168.20.106";
	public static final String MIKE = "192.168.20.105";
	public static final String DODD = "192.168.20.108";
	public static final String TAYLOR = "192.168.20.110";
	public static final String TYLER = "192.168.20.109";
	public static final String ANDREW = "192.168.20.102";

	public ArrayList<String> history = new ArrayList<String>();

	ServerSocket serverSocket;
	Socket clientSocket;

	private String[] extensions = { ".jpg", ".png", ".gif", ".tif" };
	public File file;

	// Constructor starts a new thread and sets it to Daemon for efficiency

	public Server() {
		hostList.add(CHRIS);
		hostList.add(CHRISTOPHER);
		hostList.add(MIKE);
		hostList.add(DODD);
		hostList.add(TAYLOR);
		hostList.add(TYLER);
		hostList.add(ANDREW);
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	// Filter Files by extension using apache-commons SuffixFilFilter
	public File[] getServerFileList() {
		return file.listFiles((FileFilter) new SuffixFileFilter(extensions,
				IOCase.SENSITIVE));
	}

	/***
	 * Listens for incoming connections and establishes link appropriately. Adds
	 * client address to history ArrayList for logging. Initializes the Handler
	 * class to start accepting commands.
	 */

	@Override
	public void run() {
		try {
			loadConfig();

			this.file = new File(serverPath);

			if (!file.exists()) {
				file.mkdir();
			}

			System.out.println("Binding...");

			serverSocket = new ServerSocket(port);

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

		} catch (IOException | JAXBException e) {
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

	/***
	 * Loads configuration for the program from XML file
	 * @throws JAXBException
	 */
	private void loadConfig() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ServerConfig.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		File f = new File(".\\config\\server.xml");
		ServerConfig serverConfig = (ServerConfig) unmarshaller.unmarshal(f);

		this.port = serverConfig.getPort();
		this.serverPath = serverConfig.getShareDirectory();
		this.developer = serverConfig.getDeveloper();
	}
}