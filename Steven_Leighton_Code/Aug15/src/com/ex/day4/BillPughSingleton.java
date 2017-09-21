package com.ex.day4;

public class BillPughSingleton {

	//hides constructor
	private BillPughSingleton(){}
	
	//inner nested class to save memory and keep thread safe
	private static class SingletonHelper{
		private static final BillPughSingleton INSTANCE = new BillPughSingleton();
	}
	
	//global access calls helper
	public static BillPughSingleton getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	
}