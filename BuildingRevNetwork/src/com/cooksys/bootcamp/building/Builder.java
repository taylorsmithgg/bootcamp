package com.cooksys.bootcamp.building;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Builder {
	double budget;
	File file;
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	List<Building> history;
	static final int PORT = 2665;

	public Builder(double budget) {
		this.budget = budget;
		history = new ArrayList<Building>();

		try {
			System.out.println("Binding...");
			ServerSocket serverSocket = new ServerSocket(PORT);
			
			System.out.println("\tBound");
			System.out.println("Waiting for client...");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				OutputStream outputStream = clientSocket.getOutputStream();
				System.out.println("\tClient connected");

				int character;
				
				this.budget = clientSocket.getInputStream().read();
				
				while ((character = clientSocket.getInputStream().read()) != -1) {
					//Thread.sleep(5000);
					System.out.print((char) character);
				}

				System.out.println("Exiting...");

				serverSocket.close();
			}
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static void closeResource(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
				System.out.println("Resource closed: "
						+ closeable.getClass().getName());
				System.out.println("Done!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printHistory() {
		int i = 0;
		System.out.println("Records:");
		for (Building b : history) {
			i += 1;
			System.out.println(i + " " + b.toString());
		}
	}

	public boolean buildBuilding(Building b) {
		if (budget >= b.cost) {
			history.add(b);
			b.built();
			budget -= b.getCost();
			System.out.println("Budget: " + budget);
			return true;
		} else {
			Thread.currentThread().interrupt();
			return false;
		}
	}

	public void testWrapper() {
		XMLFormatter xmlList = new XMLFormatter();
		xmlList.setBuildingList(history);
		File file = new File("History.xml");

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(XMLFormatter.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(xmlList, file);
			jaxbMarshaller.marshal(xmlList, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void testUnwrapper() {
		try {
			file = new File(".", "History.xml");
			JAXBContext jaxbContext = JAXBContext
					.newInstance(XMLFormatter.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			XMLFormatter xmlList = (XMLFormatter) jaxbUnmarshaller
					.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Builder builder = new Builder(500);
	}// end main

}// end class
