package Q3;

import java.util.Scanner;

public class Reverse {

	public static void main(String[] args) {
	
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter string to reverse: ");
		String str = scan.nextLine();
		
		System.out.println(reverse(str));

	}
	public static String reverse(String str){
		char[] arr = str.toCharArray();
		char[] reverseArr = new char[arr.length];
		for(int i = 0; i < arr.length; i++){
			reverseArr[i] = arr[arr.length-i-1]; //take correct element from back of arr
		}
		
		return String.valueOf(reverseArr);
	}
}
