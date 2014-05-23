package com.cooksys.bootcamp.building;

public class EmpireStateBuilding extends Building {

	public EmpireStateBuilding(String name, int numRooms, int numFloors,
			double cost) {
		this.name = name;
		this.numRooms = numRooms;
		this.numFloors = numFloors;
		this.cost = cost;
	}

	public void built() {
		System.out.println(this.toString() + " built!");

	}

	@Override
	public String toString() {
		return "Empire State Building";
	}
}
