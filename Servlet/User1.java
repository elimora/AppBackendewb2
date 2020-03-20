

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class User1
 */
@WebServlet("/User1")
@MultipartConfig()
public class User1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//con esto chequeo si existe una secion activa o no
		if(request.getSession(false) != null) {
			//con esto tomo el id de la seccion que ya tengo la seguridad que esta activa 
			//y puedo devolver esos datos al front
			 HttpSession miSession= request.getSession(false);//coloco false p q estoy pidiendo
			 //datos de una nueva seccion y no quiero eso solo los datos de seccion actual
			 String miId=(String) miSession.getAttribute("id");
			 
			 response.getWriter().append("Datos de usuario con seccion activa"+miId);
			 
			return;
		}else {
			 response.getWriter().append("No hay ningun usuario Inicializado");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		String miPassword=request.getParameter("password");
		
		if(request.getSession(false) == null) {//si no hay seccion iniciada 
			response.getWriter().append("Se debe iniciar secion para esta opcion");
			return;
		}
		System.out.println(miPassword+"hola");
		if(miPassword.equals("")) {
			response.getWriter().append(" Alerta: se debe incluir el password!!");
			return;
		}
		//tomar la contracenia y hasarla ya que voy a guardarla en BD
		String encriptado=Encriptador.HassPassword(miPassword);
		
		//hacer la consulta a DB
		Connection miConexion=AdminConex.devolverConexion();
		try {
			
			 HttpSession miSession= request.getSession(false);//coloco false p q estoy pidiendo
			 //datos de una nueva seccion y no quiero eso solo los datos de seccion actual
			 String miId=(String) miSession.getAttribute("id");
			System.out.println(miId  + " update");
			PreparedStatement miStatement= miConexion.prepareStatement("UPDATE Usuario set user_password='"+miPassword +"'"+" where user_cedula='"+miId+"'");
			
			miStatement.execute();//executeQuery es solo para los select debo hacer el normal execute
			
			response.getWriter().append("Modificado con exito");
		}catch(Exception e) {
			e.printStackTrace();
			response.getWriter().append(" error al modificar el Usuario");
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getSession(false) == null) {//si no hay seccion iniciada 
			response.getWriter().append("Debe iniciar sesion para borrar su usuario");
			return;
		}
		try {
			HttpSession miSession = request.getSession(false);
		Connection miConexion=AdminConex.devolverConexion();
	PreparedStatement miStatement = miConexion.prepareStatement("DELETE FROM Usuario WHERE user_cedula = ?");
	miStatement.setString(1, (String) miSession.getAttribute("id"));	
	System.out.println(miSession.getAttribute("id") + " delete");
	miStatement.execute();
	miSession.invalidate();
	response.getWriter().append("Usuario eliminado con exito");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.getWriter().append("Hubo un error al eliminar el usuario");
		}
		
	}

}
