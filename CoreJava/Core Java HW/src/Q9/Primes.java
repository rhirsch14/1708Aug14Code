package Q9;

import java.util.ArrayList;
import java.util.List;

public class Primes {
	
	public static List<Integer> getPrimes(List<Integer> nums) {
		List<Integer> ret = new ArrayList<>();
		
		for (Integer n : nums) {
			if (n == 1) continue;
			ret.add(n);
			for (int i = 2; i <= Math.sqrt(n); i++) {
				if (n % i == 0) {
					ret.remove(n);
					break;
				}
			}
		}
		
		return ret;
	}

	public static void main(String[] args) {
		
		List<Integer> nums = new ArrayList<>();
		for (int i = 0; i < 30; i++) nums.add(i+1);

		nums = getPrimes(nums);
		for (Integer n : nums) {
			System.out.print(n + ", ");
		}		
	}
}