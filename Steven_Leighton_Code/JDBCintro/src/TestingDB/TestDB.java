package TestingDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDB {
	static String URL = "jdbc:oracle:thin:chinook@//localhost:1521/xe";
	
	public static void main(String[] args) {
		
		try( Connection con=DriverManager.getConnection(  
						URL,"chinook","p4ssw0rd")){ 
			
			//step1 load the driver class  
			Class.forName("oracle.jdbc.driver.OracleDriver");  

			//step2 create  the connection object, done in the try resources

			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select count(artistid) from ARTIST");  
			
			//use info from resultset
			while(rs.next())  
				System.out.println(rs.getInt(1));  

			
			//step5 close the connection object, done in try w/ resources 

		}catch(Exception e){ System.out.println(e);}  

	}  
}