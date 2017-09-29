package com.ex.day3;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * JUnit provides static methods in the
 * org.junit.Assert class to test for
 * certain conditions
 */

public class RecursionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Before class");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("After class");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("Before method");
	}
	
	@After
	public void tearDown() throws Exception {
		System.out.println("After method");
	}

	@Test
	public void test() {
		Recursion tester = new Recursion();
		int expected = 24;
		int actual = tester.factorial(4);
		assertEquals(expected, actual);
	}

}