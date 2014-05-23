package com.cooksys.tutorials.basics.carExample.cars;

import com.cooksys.tutorials.basics.carExample.Car;
import com.cooksys.tutorials.basics.carExample.Load;

public class PickupTruck extends Car
{
	public PickupTruck(int year, String make, String model)
	{
		super(year, make, model);
	}

	@Override
	public boolean canCarry(Load load)
	{
		return load.getWeight() <= 400;
	}

	@Override
	public String toString()
	{
		return "Pickup Truck";
	}
}
