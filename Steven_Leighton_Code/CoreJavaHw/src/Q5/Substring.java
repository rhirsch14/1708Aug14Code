package Q5;

public class Substring {

	public static void main(String[] args) {
		System.out.println("12345 : substring(3) = " + substring("12345", 3));
		System.out.println("this is cool huh : substring(10) = " + 
								substring("this is cool huh", 10));

	}

	static String substring(String str, int idx){
		char[] arr = str.toCharArray();  //get character array
		char[] newArr = new char[idx+1]; //add one for inclusiveness
		
		//add substring to new array
		for(int i = 0; i <= idx; i++){
			newArr[i] = arr[i];
		}
		
		//return String representation of char[]
		return String.valueOf(newArr);
	}
}
