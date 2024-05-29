

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class evaluar_alumno_pruebas
 */
public class evaluar_alumno_pruebas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public evaluar_alumno_pruebas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String preHTML5 = "<!doctype html>\r\n"
				+ "<html>"
				+ "<head><title>Evaluar</title>"
				+ "<script src=\"https://code.jquery.com/jquery-3.7.1.min.js\"></script>"
				+ "<script src=\"./js/evaluar_alumnos.js\"></script>"
				+ "</head>"
				+ "<body>"
				+ "<h1>Pagina del profesor para evaluar alumnos</h1>"
				+ "<p>A falta de implementar para el hito 3</p>"
				+ "<p id=\"gatos\"></p>"
				+ "<p id=\"alumnos\"></p>"
				+ "</body>"
				+ "</html>";
				
			
		PrintWriter out = response.getWriter();
		out.println(preHTML5);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
