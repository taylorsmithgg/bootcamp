package com.cooksys.tutorials.basics.carExample.cars;

import com.cooksys.tutorials.basics.carExample.Car;
import com.cooksys.tutorials.basics.carExample.Load;

public class Compact extends Car
{
	public Compact(int year, String make, String model)
	{
		super(year, make, model);
	}

	@Override
	public boolean canCarry(Load load)
	{
		return load.getWeight() <= 100;
	}

	@Override
	public String toString()
	{
		return "Compact Car";
	}
}
