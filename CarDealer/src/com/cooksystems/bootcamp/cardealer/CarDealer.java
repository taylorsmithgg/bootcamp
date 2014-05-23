package com.cooksystems.bootcamp.cardealer;

import java.util.ArrayList;
import java.util.List;

import com.cooksystems.bootcamp.cars.Compact;
import com.cooksystems.bootcamp.cars.PickupTruck;
import com.cooksystems.bootcamp.cars.SportUtilityVehicle;

public class CarDealer implements Operation {
	static List<Car> inventory = new ArrayList<Car>();

	public CarDealer(Load load) {
		Car current = getSmallestCarThatCanCarry(load);
		if (current != null) {
			System.out.println("Vehicle: " + current
					+ " Load Size: " + load);
		} else {
			System.out.println("Vehicle: Unavailable" + " Load Size: " + load);
		}
	}

	public Car getSmallestCarThatCanCarry(Load load) {
		for (Car x : inventory) {
			if (x.canCarry(load)) {
				Car result = x;
				inventory.remove(x);
				return result;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		inventory.add(new Compact(2000, "Compact", "Honda", "Civic", 100));
		inventory.add(new SportUtilityVehicle(2004, "Sport Utility Vehicle",
				"Chevrolet", "Tahoe", 200));
		inventory.add(new PickupTruck(2009, "Pickup Truck", "Ford", "F-250",
				400));
		inventory.add(new Compact(2000, "Compact", "Toyota", "Corolla", 100));
		inventory.add(new SportUtilityVehicle(2001, "Sport Utility Vehicle",
				"Licoln", "Navigator", 200));
		inventory.add(new PickupTruck(2003, "Pickup Truck", "Dodge",
				"Ram 2500", 400));
		for (int i = 0; i < 10; i++) {
			int rand = (int) ((Math.random() * 400) + 1);
			Load load = new Load(rand);
			new CarDealer(load);
		}
	}// end main
}// end class
