package tests;

import java.math.BigDecimal;

import com.bank.dao.DBDAO;
import com.bank.pojo.Account;
import com.bank.pojo.User;

public class Test {
	public static void main(String[] args) {
		DBDAO dao = new DBDAO();
//		User user = new User("Steven","Leighton","Serka","pass");
//		user.setID(dao.addUser(user));
//		User user = dao.getUser("Serka", "pass");
//		int accountID = dao.addAccount(user.getId(), 2);
//		Account savings = new Account (accountID, BigDecimal.valueOf(0),user.getId(),2);
		Account savings = dao.getAccount(3);
		
		System.out.println(savings.toString());
	}
}