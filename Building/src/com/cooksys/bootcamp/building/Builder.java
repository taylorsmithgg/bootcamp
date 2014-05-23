package com.cooksys.bootcamp.building;

import java.util.List;
import java.util.ArrayList;

public class Builder {
	double budget;
	List<Building> history = new ArrayList<Building>();
	Building empire = new EmpireStateBuilding("Empire State Building", 10000,
			100, 100.0);
	Building cooksys = new CookSystemsBuilding("Cook Systems Building", 100,
			10, 10.0);

	public Builder(double budget) {
		this.budget = budget;
		buildBuilding(empire);
		printHistory();
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

		do {
			if (b instanceof CookSystemsBuilding || b == null) {
				b = empire;
			} else {
				b = cooksys;
			}
			if (budget >= b.cost) {
				history.add(b);
				b.built();
				budget -= b.getCost();
				System.out.println("Budget: " + budget);
			} else {
				System.out.println("BANKRUPT!\n");
				return false;
			}
			;
		} while (buildBuilding(b));
		return false;
	}

	public static void main(String[] args) {
		Builder builder = new Builder(500);
	}
}// end class
