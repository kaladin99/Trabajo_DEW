

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


public class servlet_log2 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	String preTituloHTML5 = "<!DOCTYPE html>\n<html>\n<head>\n<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />";

    public servlet_log2() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String result = request.getRemoteHost() + " - " +LocalDateTime.now() + " - " + request.getMethod() + " " + request.getRequestURL() +"\n";
		
		try {
			ServletContext context = getServletContext();
			String BDFileName = context.getInitParameter("fichero-persistencia");
			
			PrintWriter out = response.getWriter();
			
			 FileWriter fileWriter = new FileWriter(BDFileName,true);
			 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			 out.println("<html><head><title>Log 0</title></head><body>");
			 out.println("<span>" + result + "</span>");
			 out.println("</body></html>");
			
			 bufferedWriter.write(result);
			 bufferedWriter.close();
			
		}catch(IOException e){
			
			
		}
		
	}

}
