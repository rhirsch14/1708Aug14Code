package Q19;

import java.util.ArrayList;
import java.util.List;

public class ListOps {
	
	public static void fillList(List<Integer> list, int max) {
		for (int i = 0; i < max; i++) {
			list.add(Integer.valueOf(i + 1));
		}
	}
	
	public static int addEvens(List<Integer> list) {
		Integer ret = 0;
		for (Integer i : list) {
			if (i % 2 == 0) {
				ret += i;
			}
		}
		return ret;
	}
	
	public static int addOdds(List<Integer> list) {
		Integer ret = 0;
		for (Integer i : list) {
			if (i % 2 != 0) {
				ret += i;
			}
		}
		return ret;
	}
	
	public static List<Integer> removePrimes(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			if (isPrime(list.get(i))) {
				list.remove(i--);
			}
		}
		return list;
	}
	
	private static boolean isPrime(Integer i) {
		if (i == 1) return false;
		for (int j = 2; j*j <= i; j++) {
			if (i % j == 0) return false;
		}
		if (i == 3) System.out.println("3: good");
		return true;
	}
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		fillList(list, 10);
		
		System.out.println("Sum of evens = " + addEvens(list));
		System.out.println("Sum of odds = " + addOdds(list));
		System.out.println("Remove primes:");
		list = removePrimes(list);
		for (Integer i : list) {
			System.out.print(i + ", ");
		}
	}

}