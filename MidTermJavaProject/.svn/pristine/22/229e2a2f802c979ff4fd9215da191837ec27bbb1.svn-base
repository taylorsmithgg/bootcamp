package com.bondstone.finalJavaProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bondstone.finalJavaProject.client.FileShareClient;
import com.bondstone.finalJavaProject.client.NoConnectionException;
import com.bondstone.finalJavaProject.listeners.ConnectionListener;
import com.bondstone.finalJavaProject.server.FileShareServer;

public class FileShare
{
	private FileShareClient					client;
	private FileShareServer					server;
	private int								port;
	private final String					XML_FILE_NAME		= "Preferences.dat";
	private String							SHARE_DIRECTORY_LOCATION;
	private String							DOWNLOAD_DIRECTORY_LOCATION;
	private String							USER_NAME;
	private ArrayList<String>				ipAddresses			= new ArrayList<>();
	private boolean							test				= false;
	private ArrayList<ConnectionListener>	connectionListeners	= new ArrayList<>();

	public FileShare() throws SAXException, IOException, ParserConfigurationException
	{
		loadDataFromXMLFile();

		client = new FileShareClient(port, DOWNLOAD_DIRECTORY_LOCATION);
		server = new FileShareServer(port, SHARE_DIRECTORY_LOCATION, USER_NAME);
	}

	private void loadDataFromXMLFile() throws SAXException, IOException, ParserConfigurationException
	{
		File dataFile = new File(XML_FILE_NAME);

		if (dataFile.exists() && !test)
		{
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(XML_FILE_NAME));
			document.getDocumentElement().normalize();

			NodeList nodes = document.getElementsByTagName("FileShare");

			port = Integer.parseInt(nodes.item(0).getAttributes().getNamedItem("Port").getNodeValue());
			SHARE_DIRECTORY_LOCATION = nodes.item(0).getAttributes().getNamedItem("ShareFolder").getNodeValue();
			DOWNLOAD_DIRECTORY_LOCATION = nodes.item(0).getAttributes().getNamedItem("DownloadFolder").getNodeValue();
			USER_NAME = nodes.item(0).getAttributes().getNamedItem("UserName").getNodeValue();

			nodes = document.getDocumentElement().getElementsByTagName("IPAddresses");

			nodes = ((Element) nodes.item(0)).getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++)
			{
				Element node = (Element) nodes.item(i);

				ipAddresses.add(node.getAttributes().getNamedItem("ipAddress").getNodeValue());
			}
		}
		else
		{
			port = 2663;
			SHARE_DIRECTORY_LOCATION = "share\\";
			DOWNLOAD_DIRECTORY_LOCATION = "downloads\\";
			USER_NAME = "Madison Fortenberry";

			ipAddresses.add("192.168.20.140");
			ipAddresses.add("192.168.20.128");
			ipAddresses.add("192.168.20.145");
			ipAddresses.add("192.168.20.134");
			ipAddresses.add("192.168.20.138");
			ipAddresses.add("192.168.20.121");
			ipAddresses.add("192.168.20.112");
			ipAddresses.add("192.168.20.137");
			ipAddresses.add("192.168.20.116");
			ipAddresses.add("192.168.20.147");
			ipAddresses.add("localhost");
		}
	}

	public void exit() throws TransformerConfigurationException, SAXException, IOException, ParserConfigurationException, TransformerException,
			TransformerFactoryConfigurationError
	{
		server.stop();

		saveDataToXMLFile();
	}

	private void saveDataToXMLFile() throws SAXException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException,
			TransformerFactoryConfigurationError
	{
		File xmlFile = new File(XML_FILE_NAME);

		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		Element fileShareNode = document.createElement("FileShare");
		fileShareNode.setAttribute("Port", "" + port);
		fileShareNode.setAttribute("ShareFolder", "" + SHARE_DIRECTORY_LOCATION);
		fileShareNode.setAttribute("DownloadFolder", "" + DOWNLOAD_DIRECTORY_LOCATION);
		fileShareNode.setAttribute("UserName", "" + USER_NAME);

		Element ipAddressesNode = document.createElement("IPAddresses");

		for (String ipAddress : ipAddresses)
		{
			Element ipElement = document.createElement("IPAddress");
			ipElement.setAttribute("ipAddress", ipAddress);
			ipAddressesNode.appendChild(ipElement);
		}

		fileShareNode.appendChild(ipAddressesNode);

		document.appendChild(fileShareNode);

		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(xmlFile);
		TransformerFactory.newInstance().newTransformer().transform(source, result);
	}

	public ArrayList<String> getIPAddresses()
	{
		return ipAddresses;
	}

	public String connect(String ipAddress)
	{
		try
		{
			String name = client.connect(ipAddress);

			if (name != null)
				synchronized (connectionListeners)
				{
					for (ConnectionListener listener : connectionListeners)
						listener.connected();
				}

			return name;
		}
		catch (IOException e)
		{
			e.printStackTrace();

			return null;
		}
	}

	public ArrayList<String> getRemoteFiles() throws NoConnectionException
	{
		try
		{
			ArrayList<String> files = client.getListOfFiles();

			return files;
		}
		catch (IOException e)
		{
			e.printStackTrace();

			return null;
		}
	}

	public void addConectionListener(ConnectionListener listener)
	{
		synchronized (connectionListeners)
		{
			connectionListeners.add(listener);
		}
	}

	public void downloadFile(String fileName) throws FileNotFoundException, NoConnectionException, IOException, UnknownCommandException
	{
		client.downloadFile(fileName);
	}

	public int getPort()
	{
		return port;
	}

	public String getSHARE_DIRECTORY_LOCATION()
	{
		return SHARE_DIRECTORY_LOCATION;
	}

	public String getDOWNLOAD_DIRECTORY_LOCATION()
	{
		return DOWNLOAD_DIRECTORY_LOCATION;
	}
}
