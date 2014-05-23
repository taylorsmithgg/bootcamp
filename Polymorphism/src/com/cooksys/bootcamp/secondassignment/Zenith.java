package com.cooksys.bootcamp.secondassignment;

public class Zenith extends Atomizer{
	public Zenith(int posts, String material, int airflow){
		this.name = "Zenith";
		this.posts = posts;
		this.material = material;
		this.airflow = airflow;
	}
	
	public void out(){
		System.out.println("Name: " + this.name + " Material: " + this.material + " " + this.posts + " Airflow rating: " + this.airflow);
	}
}
