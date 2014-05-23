package com.cooksys.bootcamp.secondassignment;

public abstract class Atomizer implements Operation{
	protected int posts;
	protected int airflow;
	protected String material;
	protected String color;
	protected String name;
	
	public abstract void out();
}
