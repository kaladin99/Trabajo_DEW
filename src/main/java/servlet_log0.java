



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class servlet_log0 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	String preTituloHTML5 = "<!DOCTYPE html>\n<html>\n<head>\n<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />";

    public servlet_log0() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String directorioActual = System.getProperty("user.dir");
		//File directorio = new File(directorioActual);
		//File[] archivos = directorio.listFiles();
		
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		
		String result = request.getRemoteHost() + " - " +LocalDateTime.now() + " - " + request.getMethod() + " " + request.getRequestURL();
		
		
		out.println("<html><head><title>Log 0</title></head><body>");
		out.println("<span>" + result + "</span>");
		out.println("</body></html>");
		
	    // String t=request.getParameter("");
		
		//out.println("<html><head><title>Info</title></head><body><table>");
		//out.println("<tr><td colspan=2><b>Info Path</b></td><tr>");
		//out.println("<tr><td>Request URL</td><td>"+request.getRequestURL()+"</td><tr>");
		//out.println("<tr><td>Context Path</td><td>"+request.getContextPath()+"</td><tr>");out.println("<tr><td>Servlet Path</td><td>"+request.getServletPath()+"</td><tr>");out.println("<tr><td>Path Info</td><td>"+request.getPathInfo()+"</td><tr>");
		//out.println("<tr><td>Query String</td><td>"+request.getQueryString()+"</td><tr>");out.println("<tr><td colspan=2><b>Protocolo HTTP</b></td><tr>");
		//out.println("<tr><td>Metodo</td><td>"+request.getMethod()+"</td><tr>");
		//out.println("<tr><td>Remote Addr</td><td>"+request.getRemoteAddr()+"</td><tr>");
		//out.println("<tr><td>Remote Host</td><td>"+request.getRemoteHost()+"</td><tr>");
		//out.println("<tr><td>Remote Port</td><td>"+request.getRemotePort()+"</td><tr>");
		//out.println("<tr><td>Current Time</td><td>"+ LocalDateTime.now()+"</td><tr>");
		//out.println("<tr><td>directorio actual</td><td>"+ directorioActual +"</td><tr>");
		
		//if(archivos !=null) {
		//	for (File archivo : archivos) {
		//		out.println("<tr><td></td><td>"+ archivo +"</td><tr>");
		//		
		//	}
		//}
			
		
	}

}
