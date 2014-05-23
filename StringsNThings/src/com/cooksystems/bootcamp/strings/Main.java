package com.cooksystems.bootcamp.strings;

import java.util.StringTokenizer;

public class Main {

	public Main() {
		
		double b = 100.2;
		int a = 2;
		long c = 2002;
		
		Converter convert = new Converter(a,b,c);
		
		String one = toString(a);
		String two = toString(b);
		String three = toString(c);
		
		

		String radar = "radar";
		String tree = "tree";

		String sentence = "The quick brown fox jumps over the sleepy brown dog";

		System.out.println(checkPalindrome(tree));
		System.out.println(checkPalindrome(radar));
		System.out.println(checkPalindrome(three));

		reverseTokens(sentence);
	}

	public String reverseString(String s) {
		return new StringBuilder(s).reverse().toString();
	}

	public String reverseTokens(String s) {
		StringTokenizer tokenizer = new StringTokenizer(s);
		String result = "";

		while (tokenizer.hasMoreTokens()) {
			result = tokenizer.nextToken() + " " + result;
		}
		System.out.println(result);
		return result;
	}

	public boolean checkPalindrome(String s) {
		String compare = reverseString(s);
		if (compare.equals(s)) {
			return true;
		} else {
			return false;
		}
	}

	public String toString(int i) {
		return String.valueOf(i);
	}

	public String toString(long l) {
		return String.valueOf(l);
	}

	public String toString(double d) {
		return String.valueOf(d);
	}

	public static void main(String[] args) {
		new Main();
	}
}
