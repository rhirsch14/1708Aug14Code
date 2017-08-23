package com.ex.day3;

public class ThreadIntro {
	
	public static void main(String[] args) {
		
	
	ExtendsThread et = new ExtendsThread();
	ImplementsRunnable  ir = new ImplementsRunnable();
	
	et.start();
	Thread something = new Thread(ir);
	something.start();
	
	Runnable anonRun = new Runnable(){

		@Override
		public void run() {
		// TODO Auto-generated method stub
			System.out.println("In Anon");
			for(int i = 0; i <10; i++){
				System.out.println(i + "In Anon");
			}
		}
		};
		Thread anonThread = new Thread(anonRun);
		anonThread.start();
	
	//((Thread) anonRun).start();
	
	Runnable lambda = () -> {
		System.out.println("lambda");
		for(int i = 0; i <10; i++){
			System.out.println(i + "lambda");
	}
	};
	Thread l = new Thread(lambda);
	l.start();
}
}
// extends Thread
class ExtendsThread extends Thread{
	
	public void run(){
		
		System.out.println("In ExtendsThread");
		for(int i = 0; i <10; i++){
			System.out.println(i + "In ExtendsThread");
		}
	}
	}
	class ImplementsRunnable implements Runnable{

		@Override
		public void run() {
			System.out.println("In ImplementsThread");
			for(int i = 0; i <10; i++){
				System.out.println(i + "In ImplementsThread");
			
		}
		
		
	}
	
}