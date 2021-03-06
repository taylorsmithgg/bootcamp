package com.cooksystems.bootcamp.types;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.cooksystems.bootcamp.hidden.Scope;

public class Default extends Abstract {
	HashMap<Integer, String> data = new HashMap<Integer, String>();
	Set<Default> dataset = new HashSet<Default>();
	Queue<String> line = new LinkedList<String>();

	int i = 1; //32 bit signed integer (-2^31 to 2^31-1)
	boolean t = true; //true or false (1-bit)
	short x = 0; //16 bit signed integer (-32768 to 32767)
	long y = 1024; //64 bit signed integer (-2^63 to 2^63-1)
	float z = 256;  //32 bit signed floating point value
	double xy = 10.25; //64 bit floating point value 
	char c = 'a'; //1 byte unsigned integer value (0 to 255)
	byte b = 0x00034; //1 byte signed integer value (-128 to 127)
	
	Scope peekaboo = new Scope();

	public Default() {
		runTime();
	}

	public void runTime() {
		tryCatch();
		booleanTest(t);
		dataset.add(this);
		System.out.println("Set: " + dataset);
		line.add(this.toString());
		for(String s : line){
			System.out.println("Queue: " + s);
		}
//		System.out.println(peekaboo.i); not visible (protected)
		System.out.println(peekaboo.j); //(public)
//		System.out.println(peekaboo.k); not visible (private)
//		System.out.println(peekaboo.l); not visible (default)
	}
	
	public void booleanTest(boolean test){
		if(test == true){
			System.out.println("True");
		}else{
			System.out.println("False");
		}
	}

	public void tryCatch() {
		try {
			i = i / 0;
		} catch (ArithmeticException e) {
			data.put(i, e.getMessage());
		}
	}

	public static void main(String[] args) {
		new Default();
	}
}
