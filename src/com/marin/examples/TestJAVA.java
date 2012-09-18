
package com.marin.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestJAVA {

	public TestJAVA()
		throws InterruptedException {

	}

	@Before
	public void setUp()
		throws Exception {

	}

	@Test
	/*
	 * public void testMyStrings() { String str = "today is monday"; char[]
	 * chArray = new char[str.length()]; for (int i = 0; i < str.length(); i++)
	 * { chArray[i] = str.charAt(i); } Arrays.sort(chArray); for (int i = 0; i <
	 * str.length(); i++) { // System.out.println(chArray[i]); } }
	 */
	public void middleTwo() {

		String str = "Practice";
		String newStr = null;
		int len = str.length();
		if (len > 2) {

			newStr = str.substring(len / 2 - 1, len / 2 + 1);
			System.out.println(newStr);
			// return newStr;

		}

	}

	public void testRevString() {

		String str = "Marin is a wonderful place to work";
		char[] chArray = new char[str.length()];
		String outStr = "";
		for (int i = str.length() - 1; i > 0; i--) {
			chArray[i] = str.charAt(i);
			outStr += chArray[i];
		}

		System.out.println(outStr);
	}

	@After
	public void tearDown()
		throws Exception {

		// logout();

	}
}
