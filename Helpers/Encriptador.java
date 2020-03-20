import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encriptador {
	public static String HassPassword(String initialPass) {
		
		try {
			//Esta  clase(MessageDigest) proporciona a las aplicaciones la funcionalidad 
			//de un algoritmo resumen para los mensajes
			
			//Aca se devuelve un objeto tipo MessageDigest que implemeta
			//el algoritmo resumen especificado
			MessageDigest myDigest= MessageDigest.getInstance("MD5");
			
			//.update: Metodo que actualiza el Digest usando el arreglo de Bytes especificados
			//en el parametro
			myDigest.update(initialPass.getBytes());
			
			//.digest: Completa el cálculo de hash realizando operaciones finales como el relleno.
			//El resumen se restablece después de realizar esta llamada.
			byte[] mybytes=myDigest.digest();
			
			//StringBuilder Crea una secuencia de caracteres mutable
			StringBuilder myStringBuilder=new StringBuilder();
			
			for(int i=0;i<mybytes.length;i++) {
				myStringBuilder.append(Integer.toString((mybytes[i] & 0xff)+0x100, 16).substring(1));
			}
			
			return myStringBuilder.toString();
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}		
		return initialPass;
		
	}
}
