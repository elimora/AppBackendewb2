import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.Part;

public class VideoHelper {
	
	//con este metodo agarro un archivo tipo part que es como vendran los
	//archivos de donde los este enviado(front, postman u otras parte)
	public static void upLoadVideo(Part miPart, String savePath) throws IOException {
		
		String fileName = extractFileName(miPart);
		//con esto paso el directorio deonde lo voy a guardar
		fileName=new File(fileName).getName();
		
		System.out.println("la ruta de del archivo guardado es: "+savePath);
		
		System.out.println("El nombre de archivo es "+fileName);// lon acabo de poner 
		miPart.write(savePath + File.separator + fileName);
		
		//Con este codigo enviare la base de datos AppDB informacion para 
		//llenar la tabla videos
		Connection miConexion=AdminConex.devolverConexion();
		try {
			
			String video_path=savePath;
			String user_title=fileName;
		
			
			PreparedStatement miStatement= miConexion.prepareStatement("INSERT INTO video VALUES (?,?)");
			
			miStatement.setString(1,video_path);
			miStatement.setString(2,user_title);
			
			miStatement.execute();//aca tendre la contrasenia del usuario desde la base de datos
			
			 System.out.println("tabla video modificada con exito"); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}//hasta aca el metodo de envio

	}
	
	//metodo que me devuelve el nombre del archuivo
	private static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
