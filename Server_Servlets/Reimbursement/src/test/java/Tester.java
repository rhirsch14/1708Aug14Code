import com.reimburse.service.Service;

public class Tester {
	
	// FIXME use logging instead of syso
	public static void main(String[] args) {
		System.out.println("Begin Tester main method");
		Service serv = new Service();
		
		// Log in a manager
		serv.login("user name", "pass word");
		
		System.out.println(serv.getAllEmployees());
		
		serv.logout();
		System.out.println("End Tester main method");
	}

}
