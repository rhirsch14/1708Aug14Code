package Q13;

public class PrintChallenge {

	public static void main(String[] args) {
		
		
		for(int i = 1; i <= 4; i++){
			for(int j = 1; j <= i; j++){
				
				//print 0 if i and j are equal, 1 otherwise
				if(i <= 2){
					if(i == j){
						System.out.print(0);
					}
					else{
						System.out.print(1);
					}
				}
				//print 0 if i and j are both even or both odd, 1 otherwise
				else{
					if(isEven(i) && isEven(j)){
						System.out.print(1);
					}
					else if(!isEven(i) && !isEven(j)){
						System.out.print(1);
					}
					else{
						System.out.print(0);
					}
				}
			}
			System.out.println(""); //new line
		}


	}
	static boolean isEven(int x){
		//true if x%2 is 0, false otherwise
		return x%2 == 0 ? true : false; 
	}
}
