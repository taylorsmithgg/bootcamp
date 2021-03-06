package com.cooksys.bootcamp.building;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Builder implements Runnable {
	double budget;
	File file;
	volatile boolean running = true;
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	List<Building> history;
	static Builder builder = new Builder(500);
	static String[] blueprint = { "Cook Systems Building",
			"Empire State Building", "foo" };
	Building b;

	public Builder(double budget) {
		this.budget = budget;
		history = new ArrayList<Building>();
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
			try {
				Thread.sleep(b.buildTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			b.built();
			budget -= b.getCost();
			System.out.println("Budget: " + budget);
			return true;
		} else {
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

	@Override
	public void run() {
		while (running) {
			synchronized (builder) {
				for (String s : blueprint) {
					b = Building.parseBuilding(s);
					if (b != null) {
						running = builder.buildBuilding(b);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Thread thread1 = new Thread(builder);
		Thread thread2 = new Thread(builder);
		Thread thread3 = new Thread(builder);
		Thread thread4 = new Thread(builder);
		Thread thread5 = new Thread(builder);

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();

		try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
			thread5.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		builder.printHistory();
		// builder.testWrapper();
		// builder.testUnwrapper();
	}// end main

}// end class
