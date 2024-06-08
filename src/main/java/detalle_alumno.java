

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class detalle_alumno
 */
public class detalle_alumno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://localhost:9090/CentroEducativo";
	private String HTML= "<!doctype html>\n"
			+ "<html lang=\"en\" data-bs-theme=\"auto\">\n"
			+ "  <head><script src=\"./assets/js/color-modes.js\"></script>\n"
			+ "\n"
			+ "    <meta charset=\"utf-8\">\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
			+ "    <meta name=\"description\" content=\"\">\n"
			+ "    <meta name=\"author\" content=\"Mark Otto, Jacob Thornton, and Bootstrap contributors\">\n"
			+ "    <meta name=\"generator\" content=\"Hugo 0.122.0\">\n"
			+ "    <title>Detalles Alumno</title>\n"
			+ "\n"
			+ "    <link rel=\"canonical\" href=\"https://getbootstrap.com/docs/5.3/examples/jumbotron/\">\n"
			+ "\n"
			+ "    \n"
			+ "\n"
			+ "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/@docsearch/css@3\">\n"
			+ "\n"
			+ "<link href=\"./assets/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n"
			+ "\n"
			+ "    <style>\n"
			+ "      .bd-placeholder-img {\n"
			+ "        font-size: 1.125rem;\n"
			+ "        text-anchor: middle;\n"
			+ "        -webkit-user-select: none;\n"
			+ "        -moz-user-select: none;\n"
			+ "        user-select: none;\n"
			+ "      }\n"
			+ "\n"
			+ "      @media (min-width: 768px) {\n"
			+ "        .bd-placeholder-img-lg {\n"
			+ "          font-size: 3.5rem;\n"
			+ "        }\n"
			+ "      }\n"
			+ "\n"
			+ "      .b-example-divider {\n"
			+ "        width: 100%;\n"
			+ "        height: 3rem;\n"
			+ "        background-color: rgba(0, 0, 0, .1);\n"
			+ "        border: solid rgba(0, 0, 0, .15);\n"
			+ "        border-width: 1px 0;\n"
			+ "        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);\n"
			+ "      }\n"
			+ "\n"
			+ "      .b-example-vr {\n"
			+ "        flex-shrink: 0;\n"
			+ "        width: 1.5rem;\n"
			+ "        height: 100vh;\n"
			+ "      }\n"
			+ "\n"
			+ "      .bi {\n"
			+ "        vertical-align: -.125em;\n"
			+ "        fill: currentColor;\n"
			+ "      }\n"
			+ "\n"
			+ "      .nav-scroller {\n"
			+ "        position: relative;\n"
			+ "        z-index: 2;\n"
			+ "        height: 2.75rem;\n"
			+ "        overflow-y: hidden;\n"
			+ "      }\n"
			+ "\n"
			+ "      .nav-scroller .nav {\n"
			+ "        display: flex;\n"
			+ "        flex-wrap: nowrap;\n"
			+ "        padding-bottom: 1rem;\n"
			+ "        margin-top: -1px;\n"
			+ "        overflow-x: auto;\n"
			+ "        text-align: center;\n"
			+ "        white-space: nowrap;\n"
			+ "        -webkit-overflow-scrolling: touch;\n"
			+ "      }\n"
			+ "\n"
			+ "      .btn-bd-primary {\n"
			+ "        --bd-violet-bg: #712cf9;\n"
			+ "        --bd-violet-rgb: 112.520718, 44.062154, 249.437846;\n"
			+ "\n"
			+ "        --bs-btn-font-weight: 600;\n"
			+ "        --bs-btn-color: var(--bs-white);\n"
			+ "        --bs-btn-bg: var(--bd-violet-bg);\n"
			+ "        --bs-btn-border-color: var(--bd-violet-bg);\n"
			+ "        --bs-btn-hover-color: var(--bs-white);\n"
			+ "        --bs-btn-hover-bg: #6528e0;\n"
			+ "        --bs-btn-hover-border-color: #6528e0;\n"
			+ "        --bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);\n"
			+ "        --bs-btn-active-color: var(--bs-btn-hover-color);\n"
			+ "        --bs-btn-active-bg: #5a23c8;\n"
			+ "        --bs-btn-active-border-color: #5a23c8;\n"
			+ "      }\n"
			+ "\n"
			+ "      .bd-mode-toggle {\n"
			+ "        z-index: 1500;\n"
			+ "      }\n"
			+ "\n"
			+ "      .bd-mode-toggle .dropdown-menu .active .bi {\n"
			+ "        display: block !important;\n"
			+ "      }\n"
			+ "    </style>\n"
			+ "\n"
			+ "    \n"
			+ "  </head>";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public detalle_alumno() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		String dniProf = (String) sesion.getAttribute("dni");
		String dniAlum = request.getParameter("dni") ;
		boolean imparteAsignatura = false;
		
		if(sesion.getAttribute("key") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String prof = fetchGet(request, "/profesores/"+sesion.getAttribute("dni"));
		
		if (prof == "") {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		//Si no es un profesor de una asignatura a la que este matriculado el alumno te manda a /profesor
		
		//se obtiene las asignaturas a las que esta matriculado el alumno
		String asignaturaAlumno = fetchGet(request, "/alumnos/"+request.getParameter("dni")+"/asignaturas");
		JSONArray asignaturaAlumnoJSON = new JSONArray(asignaturaAlumno);
		
		//se obtienen las asignaturas que imparte el profesor
		String asigDeProf = fetchGet(request, "/profesores/"+dniProf+"/asignaturas");
		JSONArray asigDeProfJSON = new JSONArray(asigDeProf);
		
		//se recorre el json en para ver si el profesor de dniProf imparte docencia en la asignatura asignaturaAcronimo
		for(int i = 0; i < asigDeProfJSON.length(); i++) {
			String acronimoProfAux = asigDeProfJSON.getJSONObject(i).getString("acronimo");
			for(int j = 0; j < asignaturaAlumnoJSON.length(); j++) {
				String acronimoAlumAux = asignaturaAlumnoJSON.getJSONObject(j).getString("asignatura");
				if(acronimoProfAux.equals(acronimoAlumAux)) {
					imparteAsignatura = true;
					break;
				}
			}
		}
		
		if (!imparteAsignatura) {
			response.sendRedirect(request.getContextPath() + "/profesor");
			return;
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = response.getWriter();
		out.println(HTML);
		//
		
	      String alumno = fetchGet(request, "/alumnos/"+request.getParameter("dni"));
		  JSONObject nombreAlu = new JSONObject(alumno);        
          String nombre = nombreAlu.getString("nombre");
          String apellidos = nombreAlu.getString("apellidos");
          
		  JSONArray asignaturasJSON = new JSONArray(asignaturaAlumno);
		  File file = new File(getServletContext().getRealPath("/assets/fotos/" + request.getParameter("dni") + ".pngb64"));
          byte[] fileContent = Files.readAllBytes(file.toPath());
          String fileContentString = new String(fileContent, StandardCharsets.UTF_8);
		String res="";
		double notaAsig = 0;
		double nota = 0;
		  for(int i = 0; i< asignaturasJSON.length(); i++) {
			 String acronimo_asig = asignaturasJSON.getJSONObject(i).getString("asignatura");
			 String notas_JSON = asignaturasJSON.getJSONObject(i).getString("nota");
			 if (acronimo_asig.equals(request.getParameter("asig"))){
				 notaAsig = Double.parseDouble(notas_JSON);
			 }
			 nota += notas_JSON.equals("") ? 0 : Double.parseDouble(notas_JSON);			 
			 res+= acronimo_asig + " ";
		}
		double media = nota / asignaturasJSON.length();
		out.println("<body>\n"
				+ "    <svg xmlns=\"http://www.w3.org/2000/svg\" class=\"d-none\">\n"
				+ "      <symbol id=\"check2\" viewBox=\"0 0 16 16\">\n"
				+ "        <path d=\"M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z\"/>\n"
				+ "      </symbol>\n"
				+ "      <symbol id=\"circle-half\" viewBox=\"0 0 16 16\">\n"
				+ "        <path d=\"M8 15A7 7 0 1 0 8 1v14zm0 1A8 8 0 1 1 8 0a8 8 0 0 1 0 16z\"/>\n"
				+ "      </symbol>\n"
				+ "      <symbol id=\"moon-stars-fill\" viewBox=\"0 0 16 16\">\n"
				+ "        <path d=\"M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z\"/>\n"
				+ "        <path d=\"M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z\"/>\n"
				+ "      </symbol>\n"
				+ "      <symbol id=\"sun-fill\" viewBox=\"0 0 16 16\">\n"
				+ "        <path d=\"M8 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z\"/>\n"
				+ "      </symbol>\n"
				+ "    </svg>\n"
				+ "\n"
				+ "    <div class=\"dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle\">\n"
				+ "      <button class=\"btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center\"\n"
				+ "              id=\"bd-theme\"\n"
				+ "              type=\"button\"\n"
				+ "              aria-expanded=\"false\"\n"
				+ "              data-bs-toggle=\"dropdown\"\n"
				+ "              aria-label=\"Toggle theme (auto)\">\n"
				+ "        <svg class=\"bi my-1 theme-icon-active\" width=\"1em\" height=\"1em\"><use href=\"#circle-half\"></use></svg>\n"
				+ "        <span class=\"visually-hidden\" id=\"bd-theme-text\">Toggle theme</span>\n"
				+ "      </button>\n"
				+ "      <ul class=\"dropdown-menu dropdown-menu-end shadow\" aria-labelledby=\"bd-theme-text\">\n"
				+ "        <li>\n"
				+ "          <button type=\"button\" class=\"dropdown-item d-flex align-items-center\" data-bs-theme-value=\"light\" aria-pressed=\"false\">\n"
				+ "            <svg class=\"bi me-2 opacity-50\" width=\"1em\" height=\"1em\"><use href=\"#sun-fill\"></use></svg>\n"
				+ "            Light\n"
				+ "            <svg class=\"bi ms-auto d-none\" width=\"1em\" height=\"1em\"><use href=\"#check2\"></use></svg>\n"
				+ "          </button>\n"
				+ "        </li>\n"
				+ "        <li>\n"
				+ "          <button type=\"button\" class=\"dropdown-item d-flex align-items-center\" data-bs-theme-value=\"dark\" aria-pressed=\"false\">\n"
				+ "            <svg class=\"bi me-2 opacity-50\" width=\"1em\" height=\"1em\"><use href=\"#moon-stars-fill\"></use></svg>\n"
				+ "            Dark\n"
				+ "            <svg class=\"bi ms-auto d-none\" width=\"1em\" height=\"1em\"><use href=\"#check2\"></use></svg>\n"
				+ "          </button>\n"
				+ "        </li>\n"
				+ "        <li>\n"
				+ "          <button type=\"button\" class=\"dropdown-item d-flex align-items-center active\" data-bs-theme-value=\"auto\" aria-pressed=\"true\">\n"
				+ "            <svg class=\"bi me-2 opacity-50\" width=\"1em\" height=\"1em\"><use href=\"#circle-half\"></use></svg>\n"
				+ "            Auto\n"
				+ "            <svg class=\"bi ms-auto d-none\" width=\"1em\" height=\"1em\"><use href=\"#check2\"></use></svg>\n"
				+ "          </button>\n"
				+ "        </li>\n"
				+ "      </ul>\n"
				+ "    </div>\n"
				+ "\n"
				+ "    \n"
				+ "<main>\n"
				+ "  <div class=\"container py-4\">\n"
				+ "    <header class=\"pb-3 mb-4 border-bottom\">\n"
				+ "      <a href='./profesor' class=\"d-flex align-items-center text-body-emphasis text-decoration-none\">\n"
				+ "        <span class=\"fs-4\">DEW ~ 2023/2024</span>\n"
				+ "      </a>\n"
				+ "    </header>\n"
				+ "\n"
				+ "    <div class=\"p-5 mb-4 bg-body-tertiary rounded-3\">\n"
				+ "      <div class=\"container-fluid py-5\">\n"
				+ "        <h1 class=\"display-5 fw-bold\"> "+ apellidos + ", " + nombre + " (" + request.getParameter("dni") + ") </br> Nota media: " + String.format("%.2f", media) +"</h1>\n"
			
				
				+ "      </div>\n"
				+ "    </div>\n"
				+ "\n"
				+ "    <div class=\"row align-items-md-stretch\">\n"
				+ "      <div class=\"col-md-6\">\n"
				+ "        <div class=\"h-100 p-5 text-bg-dark rounded-3\">\n"
				+ "			<img src=\"data:image/png;base64, "+fileContentString+ "\" alt=\"Ejemplo de imagen\" height=\"400\" width=\"400\">"
				+" <form action='cambiar_nota_servlet' >"
				+ "<input type='hidden'  name='dni' value='"+request.getParameter("dni")+"'>"
				+ "<input type='hidden' name='asig' value='"+request.getParameter("asig")+"'>"
				+ "<input type='text' name='nota' value='"+notaAsig+"'>"
				+ "<input type='submit' value='Cambiar Nota'>"
				+ "</form>'"
				+ "        </div>\n"
				+ "      </div>\n"
				+ "      <div class=\"col-md-6\">\n"
				+ "        <div class=\"h-100 p-5 bg-body-tertiary border rounded-3\">\n"
				+ "          <h2>Matriculad@ en: "+res+" " +" </h2>\n"
				+ "          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Amet luctus venenatis lectus magna. Mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus. In fermentum posuere urna nec tincidunt praesent semper feugiat. Tincidunt praesent semper feugiat nibh sed. Tellus integer feugiat scelerisque varius morbi enim. Dolor magna eget est lorem ipsum dolor sit. Sed augue lacus viverra vitae congue eu. Libero nunc consequat interdum varius sit. Mi tempus imperdiet nulla malesuada pellentesque. Consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo. Ac orci phasellus egestas tellus. Quis imperdiet massa tincidunt nunc pulvinar sapien et ligula. Eget felis eget nunc lobortis mattis. Neque laoreet suspendisse interdum consectetur. Enim sed faucibus turpis in eu mi bibendum. Pharetra magna ac placerat vestibulum lectus mauris ultrices eros in.</p>\n"
				+ "        </div>\n"
				+ "      </div>\n"
				+ "    </div>\n"
				+ "\n"
				+ "    <footer class=\"pt-3 mt-4 text-body-secondary border-top\">\n"
				+ "      Trabajo en grupo realizado para la asignatura de Desarrollo Web. Curso 2023/2024"
				+ "    </footer>\n"
				+ "  </div>\n"
				+ "</main>\n"
				+ "<script src=\"./assets/dist/js/bootstrap.bundle.min.js\"></script>\n"
				+ "\n"
				+ "    </body>\n"
				+ "</html>");
        
		
	}
	private String fetchGet(HttpServletRequest request, String url) {
		HttpSession sesion = request.getSession();
		String key = sesion.getAttribute("key").toString();
		
		try {
			URL obj = new URL(urlCE+url+"?key="+key);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
		    con.setDoInput(true);
		    String cookie = (String) sesion.getAttribute("cookies");
		    con.setRequestProperty("Cookie", cookie.split(";", 2)[0]);
			
			int responseCode = con.getResponseCode();
			//System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer res = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					res.append(inputLine);
				}
				in.close();
				return res.toString();
				//out.println("<p> res.toString(): "+res.toString()+"</p>");
				// print result
				//System.out.println(res.toString());
			} else {
				//out.println("<p> LA PETICION GET NO HA FUNCIONADO</p>");
				System.out.println("GET request did not work.");
				return "";
			}
		} catch (Exception e) {
			//out.println("<p> exception " +e +"</p>");ç
			System.out.println("GET request did not work." +e);
			return "";
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	

}
