package com.ex.strings;

public class StringBuilderAndBuffer {

	public static void main(String[] args) {
		String str = "hello";
		StringBuffer buff = new StringBuffer("hello");
		StringBuilder build = new StringBuilder("hello");
		
//		System.out.println(str.equals(buff)); //have to do buff.toString()
//		System.out.println(str.equals(build)); //have to do build.toString()
//		System.out.println(buff.equals(build));//have to toString() on both
		
		str.concat(" world"); //doesnt change str
		buff.append(" world");//does change
		build.append(" world");//does change
		System.out.println(str + " " + buff + " " + build);
	
		
	}
}
