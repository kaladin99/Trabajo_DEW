

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String result = request.getRemoteHost() + " - " +LocalDateTime.now() + " - " + request.getMethod() + " " + request.getRequestURL() + " \n";
		
		try {
			
			 
			PrintWriter out = response.getWriter();
			String rutaArchivo = "./tomcat/webapps/Trabajo-NOL/logs/logs1.txt";
			FileWriter fileWriter = new FileWriter(rutaArchivo,true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			 String directorioActual = System.getProperty("user.dir");
			 System.out.println("Directorio actual: " + directorioActual);
			 
			 out.println("<html><head><title>Log 0</title></head><body>");
			 out.println("<span>" + result + "</span>");
			 out.println("</body></html>");
			
		   
			 response.setContentType("text/html");
			
			 bufferedWriter.write(result);
			 bufferedWriter.close();
			
		}catch(IOException e){
			
			
		}
		
	}

}
