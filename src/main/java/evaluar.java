

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class evaluar
 */
public class evaluar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public evaluar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String asig = request.getParameter("nameAsignatura");
		
		String preHTML5 = "<!doctype html>\r\n"
				+ "<html>"
				+ "<head><title>Evaluar</title>"
				+ "<script src=\"https://code.jquery.com/jquery-3.7.1.min.js\"></script>"
				+ "<script src=\"./js/evaluar_alumnos.js\"></script>"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/evaluar_ajax.css\">"
				+ "</head>"
				+ "<body>"
				+ "<h1>Pagina del profesor para evaluar alumnos</h1>"
				+ "<p></p>"
				+ "<p id=\"alumnos\"></p>"
				+ "<div class=\"tarjeta\">"
				+ "<img src=\"https://robohash.org/abb\" alt=\"foto del alumno\"/>"
				+ "<p id=\"alumno_nombre\"></p>"
				+ "<div class=\"arrows_container\">"
				+ "<button id=\"btn_left\">LEFT</button>"
				+ "<span id=\"nota\"></span>"
				+ "<button id=\"btn_right\">RIGHT</button>"
				+ "</div>"
				+ "</div>"
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
