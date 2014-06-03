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

public class Builder {
	double budget;
	File file;
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	List<Building> history;

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
			b.built();
			budget -= b.getCost();
			System.out.println("Budget: " + budget);
		} else {
			System.out.println("BANKRUPT!\n");
			return false;
		}
		return true;
	}

	public void testWrapper(){
		XMLFormatter xmlList = new XMLFormatter();
		xmlList.setBuildingList(history);
		File file = new File("History.xml");
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(XMLFormatter.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			jaxbMarshaller.marshal(xmlList, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public void testUnwrapper(){
		try {
			file = new File(".","History.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(XMLFormatter.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			XMLFormatter xmlList = (XMLFormatter) jaxbUnmarshaller.unmarshal(file);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Builder builder = new Builder(500);
		String[] blueprint = { "Cook Systems Building",
				"Empire State Building", "Cook Systems Building",
				"Cook Systems Building", "foo" };

		for (String s : blueprint) {
			Building b = Building.parseBuilding(s);
			if (b != null) {
				if (!builder.buildBuilding(b)) {
					System.out.println("Out of money!");
				}
			} else {
				System.out.println("Error " + s + " is not a building");
			}
		}
		
 		builder.printHistory();
 		builder.testWrapper();
 		builder.testUnwrapper();
	}// end main
}// end class
