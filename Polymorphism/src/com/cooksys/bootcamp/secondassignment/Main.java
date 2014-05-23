package com.cooksys.bootcamp.secondassignment;

public class Main{
	public static void main(String[] args) {
		Atomizer a = new Stillare(3, "Gold");
		Atomizer b = new Zenith(3, "Copper", 2);
		Atomizer c = new Lotus(5, "Stainless Steel", "Gold");
		a.out();
		b.out();
		c.out();
	}
}
