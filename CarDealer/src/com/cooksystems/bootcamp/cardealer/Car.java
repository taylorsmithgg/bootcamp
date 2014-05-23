package com.cooksystems.bootcamp.cardealer;

public abstract class Car {
	protected int year;
	protected String type;
	protected String model;
	protected String make;
	protected int capacity;

	public Car(int year, String type, String make, String model, int capacity) {
		this.year = year;
		this.type = type;
		this.make = make;
		this.model = model;
		this.capacity = capacity;
	}

	public abstract String toString();

	public abstract boolean canCarry(Load load);
}
