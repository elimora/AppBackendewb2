

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UpLoad
 */
@WebServlet("/UpLoad")
@MultipartConfig()

//@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
//maxFileSize=1024*1024*10,      // 10MB
//maxRequestSize=1024*1024*50)   // 50MB
public class UpLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "videos";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpLoad() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Pruebo de funcionamiento doGet : Exitos");
		response.getWriter().append("Served at:  ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		// gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
         System.out.println("Se definio la ruta SavePath");
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
         System.out.println("La carpera para guardar los archivos fue creada y usada");
        for (Part miPart : request.getParts()) {
            VideoHelper.upLoadVideo(miPart,savePath);
           
        }
       
        request.setAttribute("message", "Upload has been done successfully!");
        response.getWriter().write("  El archivo se subio correctamente");
    
	}

}
