package com.taylorsmith.main;

import org.junit.Test;

public class BLogic {
	
	@Test
	public int raiseSalary(int i,double percent){
		System.out.println((int) percent);
		return (i*(int)percent);
	}
	
	@Test
	public static String givePromotion(String s){
		if(s.equals("Supervisor")){
			return "CEO";
		}
		return "";
	}
}
