

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogOut
 */
@WebServlet("/LogOut")
public class LogOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogOut() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //esto es para decierle al servidor que ya la seccion existeia o que el usuario estaba logeado
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) == null) {//si no hay seccion iniciada 
			response.getWriter().append("Se debe iniciar secion para esta opcion");
			return;
		}
		System.out.println(request.getSession(false));

		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		//con el false inpide que tome una nueva seccion y toma la que ya esta existe
        System.out.println(request.getSession(false).getAttribute("id"));
		request.getSession(false).invalidate();
		response.getWriter().append("Deslogeado con exito").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
