package Q19;

import java.util.ArrayList;
import java.util.List;

public class ArrayListStuff {

	public static void main(String[] args) {
		//create list
		List<Integer> list = new ArrayList<Integer>();
		//add nums through 10
		for(int i= 1; i<=10; i++){
			list.add(i);
		}
		//print
		printList(list);
		//add all even and print (also add up odds here)
		int evenSum = 0;
		int oddSum = 0;
		for (Integer integer : list) {
			if(integer%2==0) evenSum+= integer;
			else oddSum += integer;
		}
		System.out.println("Even sum is = " +evenSum);
		System.out.println("Odd sum is = " +oddSum);
		
		//find all primes and put in list
		List<Integer> primesFound = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i=i+1) {
			if(isPrime(list.get(i))) primesFound.add(list.get(i));
		}
		//remove all primes
		list.removeAll(primesFound);
		//print final list
		printList(list);
	}

	static void printList(List<Integer> list){
		//iterate over list, print each
		System.out.print("List: ");
		for (Integer integer : list) {
			System.out.print(integer + " ");
		}
		System.out.println("");
	}
	
	//is prime function
	static boolean isPrime(int n){
		if(n <= 1) return false;
		if(n <= 3) return true;
		for(int i = 2; i <= Math.sqrt((double) n); i++){
			if(n%i == 0) return false;
		}
		return true;
	}
}