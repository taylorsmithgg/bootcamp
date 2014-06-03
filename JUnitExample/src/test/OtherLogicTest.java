package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Assert;

public class OtherLogicTest {
	@Test
	public void printSomething(){
		System.out.println("something");
	}
	
	@Test
	public void sendString(){
		assertEquals("", null);
	}
	
	@Test
	public void sendInt(){
		assertEquals(5,5);
	}
}
