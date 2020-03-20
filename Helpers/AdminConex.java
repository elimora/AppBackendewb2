import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class AdminConex {
	
	public static Connection devolverConexion() {
		try {
			
			Class.forName("org.postgresql.Driver");
			
			Connection miConexion =DriverManager.getConnection("jdbc:postgresql://localhost:5432/AppDB","postgres","elimora");
			
			return 	miConexion; 		
			
		}catch(Exception e) {
			
			e.printStackTrace();//indicara una pista donde ocurrio el error
		return null; 
	}
  }
}
