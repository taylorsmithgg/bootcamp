package com.cooksystems.bootcamp.math;

import java.util.ArrayList;

public class Math extends Other implements Operation {
	long x = 10;
	int y = 10;
	ArrayList<Integer> numList = new ArrayList<Integer>();

	public Math() {
		forLoop();
		System.out.println(mult(x, y));
		System.out.println(total(numList));
	}

	public static void main(String[] args) {
		new Math();
	}

	public void forLoop() {
		for (int i = 1754; i >= -348; i-=3) {
			numList.add(i);
			System.out.println(i);
		}
	}

	@Override
	public Integer mult(int x, int y) {
		return x * y;
	}

	public long mult(long x, long y) {
		return (x * y);
	}

	public Integer total(ArrayList<Integer> e) {
		Integer result = 0;
		for (Integer i : e) {
			result += i;
		}
		return result;
	}
}