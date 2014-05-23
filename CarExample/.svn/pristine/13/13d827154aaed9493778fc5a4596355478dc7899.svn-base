package com.cooksys.tutorials.basics.carExample;

import java.util.ArrayList;
import java.util.Random;

import com.cooksys.tutorials.basics.carExample.cars.Compact;
import com.cooksys.tutorials.basics.carExample.cars.PickupTruck;
import com.cooksys.tutorials.basics.carExample.cars.SportUtilityVehicle;

public class CarDealer
{
	private ArrayList<Car>	cars	= new ArrayList<>();

	public CarDealer()
	{
		cars.add(new Compact(2000, "Honda", "Accord"));
		cars.add(new SportUtilityVehicle(2000, "Honda", "Accord"));
		cars.add(new PickupTruck(2000, "Honda", "Accord"));
	}

	public Car getSmallestCarThatCanCarry(Load load)
	{
		for (Car car : cars)
			if (car.canCarry(load))
				return car;

		return null;
	}

	public static void main(String[] args)
	{
		CarDealer carDealer = new CarDealer();

		Random random = new Random();

		for (int i = 0; i < 10; i++)
		{
			Load load = new Load(random.nextInt(400));

			Car car = carDealer.getSmallestCarThatCanCarry(load);

			System.out.println(load + ", " + car);
		}
	}
}
