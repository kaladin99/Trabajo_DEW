/*
	Este código no tiene muchas diferencias respecto al log0.java,
	por ello comentaremos únicamente aquellos aspectos diferentes que puedan causar dudas.
 	La principal diferencia es que además de lo que hemos hecho antes, en este caso
  	añadiremos las peticiones almacenadas en result en un documento
*/



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class servlet_log1 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	String preTituloHTML5 = "<!DOCTYPE html>\n<html>\n<head>\n<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />";

    public servlet_log1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Enumeration<String> names;
		
		try {
			
			 
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String result = request.getRemoteHost() + " - " +LocalDateTime.now() + " - " + request.getMethod() + " " + request.getRequestURL() + " Informacion formulario -->";
		
		
		 //obtenemos el directorio actual del usuario y lo mostramos por terminal
		 String directorioActual = System.getProperty("user.dir");
		 System.out.println("Directorio actual: " + directorioActual);
		 
		 // Añadimos los parametros al result
		 names = request.getParameterNames();
		String parametersResult = "";
		 while (names.hasMoreElements()) {
				String name = names.nextElement();
				parametersResult += " " + name + ": " + request.getParameter(name);
		 }
		
		 result += parametersResult + "\n";
		 
		 out.println("<!DOCTYPE html><html><head><title>Log 0</title> content=\"text/html\"; charset=\"utf-8\" </head><body>");
		 out.println("<span>" + result + "</span>");
		 out.println("</body></html>");
			
		//definimos la ruta del archivo en la que almacenaremos el "result", y la escribimos a mano
			String rutaArchivo = "/home/user/log1.txt";
			//definimos un filewriter para poder esccribir sobre el archivo indicado en la ruta 
			//y lo ponemos a true para que escriba sobre lo que ya hay y no sobreescriba
			FileWriter fileWriter = new FileWriter(rutaArchivo,true);
			//el bufferedWritter es un envoltorio de filewritter que mejora en eficiencia
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			 
			fileWriter.write(result);
			fileWriter.close();
			 //escribimos lo que hay en la variable result y lo almacenamos en un archivo
			 bufferedWriter.write(result);
			 //cerramos el buffer de escritura
			 bufferedWriter.close();
			
		}catch(IOException e){
			
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String result = request.getRemoteHost() + " - " +LocalDateTime.now() + " - " + request.getMethod() + " " + request.getRequestURL() + " Informacion formulario -->";
		Enumeration<String> names;
		
		try {
			
			 
		PrintWriter out = response.getWriter();
		//definimos la ruta del archivo en la que almacenaremos el "result", y la escribimos a mano
		String rutaArchivo = "./tomcat/webapps/Trabajo-NOL/logs/logs1.txt";
		//definimos un filewriter para poder esccribir sobre el archivo indicado en la ruta 
		//y lo ponemos a true para que escriba sobre lo que ya hay y no sobreescriba
		FileWriter fileWriter = new FileWriter(rutaArchivo,true);
		//El bufferedWritter es un envoltorio de filewritter que mejora en eficiencia
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		 //obtenemos el directorio actual del usuario y lo mostramos por terminal
		 String directorioActual = System.getProperty("user.dir");
		 System.out.println("Directorio actual: " + directorioActual);
		 
		 
		 names = request.getParameterNames();
		String parametersResult = "";
		 while (names.hasMoreElements()) {
				String name = names.nextElement();
				parametersResult += " " + name + ": " + request.getParameter(name);
		 }
		
		 result += parametersResult;
		 
		 out.println("<!DOCTYPE html><html><head><title>Log 0</title> content=\"text/html\"; charset=\"utf-8\" </head><body>");
		 out.println("<span>" + result + "</span>");
		 out.println("</body></html>");
			
		   
			 response.setContentType("text/html");

			 //escribimos lo que hay en la variable result y lo almacenamos en un archivo
			 bufferedWriter.write(result);
			 //cerramos el buffer de escritura
			 bufferedWriter.close();
			
		}catch(IOException e){
			
			
		}
		
	}

}
