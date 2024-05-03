

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
			
			//ServletContext context = getServletContext();
			//String BDFileName = context.getInitParameter("fichero-persistencia");
			//PrintWriter outDB = new PrintWriter("logs.txt");
			//FileWriter file = new FileWriter("logs.txt"); 
			PrintWriter out = response.getWriter();
			String rutaArchivo = "./tomcat/webapps/Trabajo-NOL/logs/logs1.txt";
			 FileWriter fileWriter = new FileWriter(rutaArchivo,true);
			 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			 String directorioActual = System.getProperty("user.dir");
			 System.out.println("Directorio actual: " + directorioActual);
			 
			 out.println("<html><head><title>Log 0</title></head><body>");
			 out.println("<span>" + result + "</span>");
			 out.println("</body></html>");
			
		    // String t=request.getParameter("");
			response.setContentType("text/html");
			/*
			
			out.println("<html><head><title>Info</title></head><body><table>");
			out.println("<tr><td colspan=2><b>Info Path</b></td><tr>");
			out.println("<tr><td>Request URL</td><td>"+request.getRequestURL()+"</td><tr>");
			out.println("<tr><td>Context Path</td><td>"+request.getContextPath()+"</td><tr>");out.println("<tr><td>Servlet Path</td><td>"+request.getServletPath()+"</td><tr>");out.println("<tr><td>Path Info</td><td>"+request.getPathInfo()+"</td><tr>");
			out.println("<tr><td>Query String</td><td>"+request.getQueryString()+"</td><tr>");out.println("<tr><td colspan=2><b>Protocolo HTTP</b></td><tr>");
			out.println("<tr><td>Metodo</td><td>"+request.getMethod()+"</td><tr>");
			out.println("<tr><td>Remote Addr</td><td>"+request.getRemoteAddr()+"</td><tr>");
			out.println("<tr><td>Remote Host</td><td>"+request.getRemoteHost()+"</td><tr>");
			out.println("<tr><td>Remote Port</td><td>"+request.getRemotePort()+"</td><tr>");
			out.println("<tr><td>Current Time</td><td>"+ LocalDateTime.now()+"</td><tr>");
			out.println("<tr><td>Archivo persistencia</td><td>"+ BDFileName +"</td><tr>");
			out.println("<tr><td>directorio actual</td><td>"+ directorioActual +"</td><tr>");
			*/
			//outDB.println(LocalDateTime.now());
			 bufferedWriter.write(result);
			 bufferedWriter.close();
			 
			
			
			//outDB.close();
			
		}catch(IOException e){
			
			
		}
		
	}

}
