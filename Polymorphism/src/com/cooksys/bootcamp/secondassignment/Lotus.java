package com.cooksys.bootcamp.secondassignment;

public class Lotus extends Atomizer{
	public Lotus(int posts, String material, String color){
	this.name = "Lotus";
	this.posts = posts;
	this.material = material;
	this.color = color;
	}
	
	public void out(){
		System.out.println("Name: " + this.name + " Material: " + this.material + " Posts: " + this.posts + " Color: " + this.color);
	}
}
