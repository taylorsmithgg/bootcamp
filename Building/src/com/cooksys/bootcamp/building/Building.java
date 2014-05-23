package com.cooksys.bootcamp.building;

public abstract class Building implements Operation {
	int numRooms;
	int numFloors;
	double cost;
	String name;

	public abstract void built();

	public static Building parseBuilding(String s) {
		if (s.equals("Empire State Building")) {
			return new CookSystemsBuilding("Cook Systems Building",
					100, 10, 10.0);
		} else if (s.equals("Cook Systems Building")) {
			return new EmpireStateBuilding("Empire State Building",
					10000, 100, 100.0);
		} else {
			return null;
		}
	}

	protected double getCost() {
		return cost;
	}
}
