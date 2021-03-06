package com.ex.day3;

import com.ex.day3.NestedClasses.MemberClass;

public class NestedClasses {
	/*
	 * Java allows us to write classes within other
	 * classes, these are called nested classes
	 * 
	 * Benefits:
	 * - logical grouping of classes only used 
	 * in one place
	 * - increases encapsulation 
	 * - more readable and maintainable code
	 * 
	 * Types:
	 * - static - declared as a static member
	 * of another class
	 */
	int a = 0;
	static int b = 0;
	
	static class StaticClass{
		int b = 10;
		void message(){
			System.out.println("hi i am in my static inner class");
			System.out.println(b);
		}
	}
	
	class MemberClass{
		int a;
		int b = 100;
		void message(){
			System.out.println("hi i am in my member class");
			System.out.println(a + b);
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("inside main method");
		class LocalClass{
			void message(){
				System.out.println("inside local class");
				System.out.println(b);
			}
		}
		
		Runnable run = new Runnable(){

			@Override
			public void run() {
					System.out.println("running things");
			}
			
		};
		
		MyInterface anonClass = new MyInterface(){

			@Override
			public void doThings() {
				System.out.println("doing things");
			}

			@Override
			public void doOtherThings() {
				System.out.println("doing other things");
			}
			
		};
		
		
		// inside of main 
		
		StaticClass stat = new StaticClass();
		stat.message();
		
		
		
		
		
		
		
	}
	
	
	
	

	
	
}
/*
 * MyInterface is a regular interface that we use above
 * with the anonymous nested class. We created an instance
 * of the interface by giving implementation to its abstract
 * methods, but without creating an actual class, so
 * the one instance of MyInterface will have its own
 * specific implementation
 */
interface MyInterface{ 
	
	void doThings();
	void doOtherThings();
}



class Other{
	int a;
	String message;
	
	
	
	
	NestedClasses nested = new NestedClasses();
	MemberClass mc = nested.new MemberClass();
	
//	mc.message;
	
	
}
