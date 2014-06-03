package com.taylorsmith.main;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Assert;

public class OtherLogic {
	@Test
	public void printSomething(){
		System.out.println("something");
	}
	
	@Test
	public String sendString(){
		return "string";
	}
	
	@Test
	public int sendInt(){
		assertEquals(5,5);
		return 5;
	}
}
