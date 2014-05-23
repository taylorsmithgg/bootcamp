package com.cooksystems.bootcamp.cars;

import com.cooksystems.bootcamp.cardealer.Car;
import com.cooksystems.bootcamp.cardealer.Load;

public class PickupTruck extends Car {

	public PickupTruck(int year, String type, String make, String model,
			int capacity) {
		super(year, type, make, model, capacity);
	}

	@Override
	public String toString() {
		return this.type + " " + this.year + " " + this.make + " " + this.model;

	}

	@Override
	public boolean canCarry(Load load) {
		if (load.getWeight() < capacity) {
			return true;
		} else {
			return false;
		}
	}

}
