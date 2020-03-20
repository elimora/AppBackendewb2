

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
@MultipartConfig()
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection miConexion=AdminConex.devolverConexion();
		try {
			String id=request.getParameter("id");
			String password =request.getParameter("password");
			
			String encriptado=Encriptador.HassPassword(password);			
			
			
			PreparedStatement miStatement= miConexion.prepareStatement("INSERT INTO Usuario VALUES (?, ?)");
			
			miStatement.setString(1,id);
			miStatement.setString(2,encriptado);
			
			miStatement.execute();//aca tendre la contrasenia del usuario desde la base de datos
			
			 response.getWriter().append("Usuario Agregado con Exito"); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.setStatus(404);
			response.getWriter().append("Error. Usuario ya existe");
			e.printStackTrace();
		}
		
		
		
		
	}
	}


