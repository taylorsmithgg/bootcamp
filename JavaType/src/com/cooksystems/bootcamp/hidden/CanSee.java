package com.cooksystems.bootcamp.hidden;

public class CanSee {
	Scope peekaboo = new Scope();
	public CanSee(){
		testVisible();
	}
	
	public void testVisible() {
		System.out.println(peekaboo.i);
		System.out.println(peekaboo.j);
//		System.out.println(peekaboo.k); not visible (private)
		System.out.println(peekaboo.l);
	}
	
	public static void main(String[] args){
		new CanSee();
	}
}
