package Q16;

public class NumChars {

	public static void main(String[] args) {
		if(args.length > 0){
			System.out.println(args[0]);
			System.out.println("first arg string has " + numChars(args[0]) + " chars");
		}
		else{
			System.out.println("No input");
		}
	}
	static int numChars(String k){
		return k.length();
	}

}