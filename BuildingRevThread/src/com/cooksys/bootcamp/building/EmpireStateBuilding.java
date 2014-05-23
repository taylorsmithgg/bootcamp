package com.cooksys.bootcamp.building;

public class EmpireStateBuilding extends Building {

	public EmpireStateBuilding(String name, int numRooms, int numFloors,
			double cost, long buildTime) {
		super();
		setName(name);
		setNumRooms(numRooms);
		setNumFloors(numFloors);
		setCost(cost);
		this.buildTime = buildTime;
	}

	public EmpireStateBuilding() {

	}

	public void built() {
		System.out.println(this + " built!");

	}

	@Override
	public String toString() {
		return "Empire State Building";
	}
}
