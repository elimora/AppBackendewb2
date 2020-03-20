

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
@MultipartConfig()//para indicar al servlet que busque los parametros en el cuerpo de request
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("request");
		response.getWriter().write("loco");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id=request.getParameter("id");
		String miPassword=request.getParameter("password");
		
		if(request.getSession(false) != null) {
			response.getWriter().append("Sesion ya iniciada ");
			return;
		}
		
		if(id.equals("")||miPassword.equals("")) {
			response.getWriter().append("Los campos deben estar llenos");
			return;
		}
		
		//esto tambien lo podria hacer con un midleword, con esto le digo a la 
		//respuesta que el lugar de donde vino la respuesta es seguro
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		
		Connection miConexion=AdminConex.devolverConexion();
		try {
			
			
			PreparedStatement miStatement= miConexion.prepareStatement("SELECT user_password from Usuario where user_cedula='"+id +"'");
			
			ResultSet miResultset=miStatement.executeQuery();//aca tendre la contrasenia del usuario desde la base de datos
			
			String password = null; 
			while(miResultset.next()) {
				//System.out.println(miResultset.getString(1));
				
				password=miResultset.getString(1);
			}
			
			//enviada por el usuario postman y debe coincidir con lo que
			//esta entre parentesis del getParameter
			System.out.println(miPassword);
			System.out.println(id);
			System.out.println(password);
			System.out.println("SELECT user_password from Usuario where user_cedula='"+id +"'");
			System.out.println("UPDATE Usuario set user_password='"+miPassword +"'"+" where user_cedula=' "+id+" ' ");
			System.out.println("Delete from Usuario where user_cedula=' "+id+" ' ");
			System.out.println("Esto es una prueba del servidor");
			
			
			String encriptado=Encriptador.HassPassword(miPassword);	
			
			
			 if(password.equals(encriptado)) {
				 
				 //creando la secion(para que quede registrado el usuario)
				 
				 HttpSession miSession= request.getSession();
				 miSession.setAttribute("id",id);
				 
				 
				 
				 response.getWriter().append("Clave Correcta");//lo que se enviara el front
				 //System.out.println("clave correcta");
				
				 
			 }else {
				
				 response.setStatus(401);//buena practica poner codigo de error para epecificidad del mismo
				 response.getWriter().append("Clave Invalida");//lo que se enviara el front
				 
				 //System.out.println("Clave incorrecta");
			 }
			 
			 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.getWriter().append("Usuario no Encontrado");
			e.printStackTrace();
		}
		
		
		
		
	}

}
