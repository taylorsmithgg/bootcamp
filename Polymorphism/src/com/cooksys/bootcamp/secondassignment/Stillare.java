package com.cooksys.bootcamp.secondassignment;

public class Stillare extends Atomizer{
	public Stillare(int posts, String material){
		this.name = "Stillare";
		this.posts = posts;
		this.material = material;
	}
	
	public void out(){
		System.out.println("Name: " + this.name + " Material: " + this.material + " Posts: " + this.posts);
	}
}
