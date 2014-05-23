package com.cooksys.bootcamp.building;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({ CookSystemsBuilding.class, EmpireStateBuilding.class })
public abstract class Building implements Operation {
	int numRooms;
	int numFloors;
	double cost;
	String name;
	long buildTime;

	public Building() {

	}

	public long getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(long buildTime) {
		this.buildTime = buildTime;
	}

	public abstract void built();

	public static Building parseBuilding(String s) {
		if (s.equals("Empire State Building")) {
			return new EmpireStateBuilding("Empire State Building", 10000, 100,
					100.0, 5000);
		} else if (s.equals("Cook Systems Building")) {
			return new CookSystemsBuilding("Cook Systems Building", 100, 10,
					10.0, 1000);
		} else {
			return null;
		}
	}

	public int getNumRooms() {
		return numRooms;
	}

	@XmlElement
	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	public int getNumFloors() {
		return numFloors;
	}

	@XmlElement
	public void setNumFloors(int numFloors) {
		this.numFloors = numFloors;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	protected double getCost() {
		return cost;
	}

	@XmlElement
	public void setCost(double cost) {
		this.cost = cost;
	}
}
