
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class servlet_listar_archivo_persistencia
 */
public class servlet_listar_archivo_persistencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet_listar_archivo_persistencia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
            File file = new File("./tomcat/webapps/Trabajo-NOL/logs/logs1.txt");
            Scanner input = new Scanner(file);
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
			out.println("<html><head><title>Info</title></head><body>");
			out.println("<h3>Fichero Logs1.txt</h3>");
			
            while (input.hasNextLine()) {
                String line = input.nextLine();
                out.println("<p>"+ line +"<p>");
            }
            input.close();
            
            out.println("<h3>Fichero Logs2.txt</h3>");
            
            file = new File("./tomcat/webapps/Trabajo-NOL/logs/logs2.txt");
            input = new Scanner(file);;
			
            while (input.hasNextLine()) {
                String line = input.nextLine();
                out.println("<p>"+ line +"<p>");
            }
            input.close();
            
            
            out.println("</body></html>");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

}
