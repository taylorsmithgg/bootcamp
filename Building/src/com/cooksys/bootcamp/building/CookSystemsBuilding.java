package com.cooksys.bootcamp.building;

public class CookSystemsBuilding extends Building {

	public CookSystemsBuilding(String name, int numRooms, int numFloors,
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
		return "Cook Systems Building";
	}

}
