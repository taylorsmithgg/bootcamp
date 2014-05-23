package com.cooksys.bootcamp.building;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	public void save(String s) {
		file = new File(s);
		FileWriter writer = null;
		Date timestamp = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"MM.dd.yy 'at' hh:mm:ss a zzz" + LINE_SEPARATOR);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new FileWriter(file, true);
			writer.write(dateformat.format(timestamp));
			for (Building b : history) {
				writer.write(b + LINE_SEPARATOR);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeResource(writer);
		}
	}

	public void load() {
		FileReader reader = null;
		try {
			reader = new FileReader(file);

			int character;

			while ((character = reader.read()) != -1) {
				System.out.print((char) character);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeResource(reader);
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
		int i = 0;
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
 		builder.save("History.txt");
 		builder.load();
	}// end main
}// end class
