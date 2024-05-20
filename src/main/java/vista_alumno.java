

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class vista_alumno
 */
public class vista_alumno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String preHTML5 = "<!DOCTYPE html>\n<html lang=\"es-es\">\n" +
			"<head>\n<meta charset=\"utf-8\" />\n" +
			"<title>Notas Online - Alumno</title>\n</head>\n<body>\n" +
			"<h1>VISTA ALUMNO</h1>\n";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public vista_alumno() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = response.getWriter();
		out.println(preHTML5);
		
		HttpSession sesion = request.getSession();
		
		out.println ("<p> sesion.getAttribute(\"dni\"): "+sesion.getAttribute("dni")+"</p>");
		out.println ("<p> sesion.getAttribute(\"key\"): "+sesion.getAttribute("key")+"</p>");
		out.println ("<p> sesion.getAttribute(\"cookies\"): "+sesion.getAttribute("cookies")+"</p>");
		out.println ("<p> sesion.getAttribute(\"password\"): "+sesion.getAttribute("password")+"</p>");
		out.println ("<p> request.getRemoteUser(): "+request.getRemoteUser()+"</p>");
		out.println ("<p> request.isUserInRole(\"mirol\"): "+request.isUserInRole("mirol")+"</p>");
		out.println ("<p> request.getUserPrincipal(): "+request.getUserPrincipal()+"</p>");
		
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
