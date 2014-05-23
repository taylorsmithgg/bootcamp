package com.cooksys.tutorials.basics.carExample.cars;

import com.cooksys.tutorials.basics.carExample.Car;
import com.cooksys.tutorials.basics.carExample.Load;

public class SportUtilityVehicle extends Car
{
	public SportUtilityVehicle(int year, String make, String model)
	{
		super(year, make, model);
	}

	@Override
	public boolean canCarry(Load load)
	{
		return load.getWeight() <= 200;
	}

	@Override
	public String toString()
	{
		return "Sport Utility Vehicle";
	}
}
