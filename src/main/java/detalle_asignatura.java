

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Servlet implementation class lista_asignaturas
 */
public class detalle_asignatura extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String nota;
	private String urlCE = "http://localhost:9090/CentroEducativo";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public detalle_asignatura() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion = request.getSession();
		String asig = request.getParameter("nameAsignatura");
		
		if(sesion.getAttribute("key") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String key = sesion.getAttribute("key").toString();
		
		//Comprobar que el alumno está matriculado en esa asignatura ========================================================
		String asignaturasAlumno = fetchGet(request, "/alumnos/"+sesion.getAttribute("dni")+"/asignaturas");
		
		JSONArray asignaturasJSON = new JSONArray(asignaturasAlumno);
		String resultado = "<p>";
		boolean estaMatriculado = false;
		for(int i = 0; i< asignaturasJSON.length(); i++) {
			String acronimo_asig = asignaturasJSON.getJSONObject(i).getString("asignatura");
			resultado += "Asig: "+asig + "  JSON_Asignatura: "+acronimo_asig +"\n";
			if (asig.equals(acronimo_asig)) {
				estaMatriculado = true;
			}
		}
		resultado += "</p>";
		if (estaMatriculado == false) {
			response.sendRedirect(request.getContextPath() + "/not_found_error.html");
			return;
		}
		
		// ==================================================================================================================
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = response.getWriter();
		
		
		//Recoger que el acronimo de la asignatura que  se ha clicado
		
		//Obtener toda la información de la asigntura
		String resAsig = fetchGet(request, "/asignaturas/" + asig);
		
		//Se pasa a JSON el string para acceder a sus atributos por separado
		JSONObject jsonAsig = new JSONObject(resAsig);
		   
		
		String asigsAlu = fetchGet(request, "/alumnos/" + sesion.getAttribute("dni")+ "/asignaturas");
		JSONArray arrayAsigs = new JSONArray(asigsAlu);

        JSONObject foundObject = null;
        
        for (int i = 0; i < arrayAsigs.length(); i++) {
            JSONObject jsonObject = arrayAsigs.getJSONObject(i);
            if (jsonObject.getString("asignatura").equals(asig)) {
               foundObject = jsonObject;
                break;
            }
       }
       if(foundObject.getString("nota") != "") {
    	    nota = foundObject.getString("nota");
       } 
       else {
    	   nota = "Sin calificar";
       }
		
       String preHTML5 = "<!doctype html>\r\n"
   			+ "<html lang=\"en\" data-bs-theme=\"auto\">\r\n"
   			+ "  <head><script src=\"./assets/js/color-modes.js\"></script>\r\n"
   			+ "\r\n"
   			+ "    <meta charset=\"utf-8\">\r\n"
   			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
   			+ "    <meta name=\"description\" content=\"\">\r\n"
   			+ "    <meta name=\"author\" content=\"Mark Otto, Jacob Thornton, and Bootstrap contributors\">\r\n"
   			+ "    <meta name=\"generator\" content=\"Hugo 0.122.0\">\r\n"
   			+ "    <title>Información: "+asig+"</title>\r\n"
   			+ "\r\n"
   			+ "    <link rel=\"canonical\" href=\"https://getbootstrap.com/docs/5.3/examples/jumbotron/\">\r\n"
   			+ "    <link rel=\"canonical\" href=\"https://getbootstrap.com/docs/5.3/examples/grid/\">\r\n"
   			+ "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/@docsearch/css@3\">\r\n"
   			+ "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/@docsearch/css@3\">\r\n"
   			+ "\r\n"
   			+ "<link href=\"./assets/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n"
   			+ "\r\n"
   			+ "    <style>\r\n"
   			+ "      .bd-placeholder-img {\r\n"
   			+ "        font-size: 1.125rem;\r\n"
   			+ "        text-anchor: middle;\r\n"
   			+ "        -webkit-user-select: none;\r\n"
   			+ "        -moz-user-select: none;\r\n"
   			+ "        user-select: none;\r\n"
   			+ "      }\r\n"
   			+ "\r\n"
   			+ "      @media (min-width: 768px) {\r\n"
   			+ "        .bd-placeholder-img-lg {\r\n"
   			+ "          font-size: 3.5rem;\r\n"
   			+ "        }\r\n"
   			+ "      }\r\n"
   			+ "\r\n"
   			+ "      .b-example-divider {\r\n"
   			+ "        width: 100%;\r\n"
   			+ "        height: 3rem;\r\n"
   			+ "        background-color: rgba(26, 12, 12, 0.1);\r\n"
   			+ "        border: solid rgba(0, 0, 0, .15);\r\n"
   			+ "        border-width: 1px 0;\r\n"
   			+ "        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);\r\n"
   			+ "      }\r\n"
   			+ "\r\n"
   			+ "      .b-example-vr {\r\n"
   			+ "        flex-shrink: 0;\r\n"
   			+ "        width: 1.5rem;\r\n"
   			+ "        height: 100vh;\r\n"
   			+ "      }\r\n"
   			+ "\r\n"
   			+ "      .bi {\r\n"
   			+ "        vertical-align: -.125em;\r\n"
   			+ "        fill: currentColor;\r\n"
   			+ "      }\r\n"
   			+ "\r\n"
   			+ "      .nav-scroller {\r\n"
   			+ "        position: relative;\r\n"
   			+ "        z-index: 2;\r\n"
   			+ "        height: 2.75rem;\r\n"
   			+ "        overflow-y: hidden;\r\n"
   			+ "      }\r\n"
   			+ "\r\n"
   			+ "      .nav-scroller .nav {\r\n"
   			+ "        display: flex;\r\n"
   			+ "        flex-wrap: nowrap;\r\n"
   			+ "        padding-bottom: 1rem;\r\n"
   			+ "        margin-top: -1px;\r\n"
   			+ "        overflow-x: auto;\r\n"
   			+ "        text-align: center;\r\n"
   			+ "        white-space: nowrap;\r\n"
   			+ "        -webkit-overflow-scrolling: touch;\r\n"
   			+ "      }\r\n"
   			+ "\r\n"
   			+ "      .btn-bd-primary {\r\n"
   			+ "        --bd-violet-bg: #712cf9;\r\n"
   			+ "        --bd-violet-rgb: 112.520718, 44.062154, 249.437846;\r\n"
   			+ "\r\n"
   			+ "        --bs-btn-font-weight: 600;\r\n"
   			+ "        --bs-btn-color: var(--bs-white);\r\n"
   			+ "        --bs-btn-bg: var(--bd-violet-bg);\r\n"
   			+ "        --bs-btn-border-color: var(--bd-violet-bg);\r\n"
   			+ "        --bs-btn-hover-color: var(--bs-white);\r\n"
   			+ "        --bs-btn-hover-bg: #6528e0;\r\n"
   			+ "        --bs-btn-hover-border-color: #6528e0;\r\n"
   			+ "        --bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);\r\n"
   			+ "        --bs-btn-active-color: var(--bs-btn-hover-color);\r\n"
   			+ "        --bs-btn-active-bg: #5a23c8;\r\n"
   			+ "        --bs-btn-active-border-color: #5a23c8;\r\n"
   			+ "      }\r\n"
   			+ "\r\n"
   			+ "      .bd-mode-toggle {\r\n"
   			+ "        z-index: 1500;\r\n"
   			+ "      }\r\n"
   			+ "\r\n"
   			+ "      .bd-mode-toggle .dropdown-menu .active .bi {\r\n"
   			+ "        display: block !important;\r\n"
   			+ "      }\r\n"
   			+ "    </style>\r\n"
   			+ "    \r\n"
   			+ "    \r\n"
   			+ "    <link href=\"./consultaNotas/grid.css\" rel=\"stylesheet\">\r\n"
   			+ "\r\n"
   			+ "    \r\n"
   			+ "  </head>";
       	out.println(preHTML5);
		out.println(" <body>\r\n"	
				+ "    <svg xmlns=\"http://www.w3.org/2000/svg\" class=\"d-none\">\r\n"
				+ "      <symbol id=\"check2\" viewBox=\"0 0 16 16\">\r\n"
				+ "        <path d=\"M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z\"/>\r\n"
				+ "      </symbol>\r\n"
				+ "      <symbol id=\"circle-half\" viewBox=\"0 0 16 16\">\r\n"
				+ "        <path d=\"M8 15A7 7 0 1 0 8 1v14zm0 1A8 8 0 1 1 8 0a8 8 0 0 1 0 16z\"/>\r\n"
				+ "      </symbol>\r\n"
				+ "      <symbol id=\"moon-stars-fill\" viewBox=\"0 0 16 16\">\r\n"
				+ "        <path d=\"M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z\"/>\r\n"
				+ "        <path d=\"M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z\"/>\r\n"
				+ "      </symbol>\r\n"
				+ "      <symbol id=\"sun-fill\" viewBox=\"0 0 16 16\">\r\n"
				+ "        <path d=\"M8 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z\"/>\r\n"
				+ "      </symbol>\r\n"
				+ "    </svg>\r\n"
				+ "\r\n"
				+ "    <div class=\"dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle\">\r\n"
				+ "      <button class=\"btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center\"\r\n"
				+ "              id=\"bd-theme\"\r\n"
				+ "              type=\"button\"\r\n"
				+ "              aria-expanded=\"false\"\r\n"
				+ "              data-bs-toggle=\"dropdown\"\r\n"
				+ "              aria-label=\"Toggle theme (auto)\">\r\n"
				+ "        <svg class=\"bi my-1 theme-icon-active\" width=\"1em\" height=\"1em\"><use href=\"#circle-half\"></use></svg>\r\n"
				+ "        <span class=\"visually-hidden\" id=\"bd-theme-text\">Toggle theme</span>\r\n"
				+ "      </button>\r\n"
				+ "      <ul class=\"dropdown-menu dropdown-menu-end shadow\" aria-labelledby=\"bd-theme-text\">\r\n"
				+ "        <li>\r\n"
				+ "          <button type=\"button\" class=\"dropdown-item d-flex align-items-center\" data-bs-theme-value=\"light\" aria-pressed=\"false\">\r\n"
				+ "            <svg class=\"bi me-2 opacity-50\" width=\"1em\" height=\"1em\"><use href=\"#sun-fill\"></use></svg>\r\n"
				+ "            Light\r\n"
				+ "            <svg class=\"bi ms-auto d-none\" width=\"1em\" height=\"1em\"><use href=\"#check2\"></use></svg>\r\n"
				+ "          </button>\r\n"
				+ "        </li>\r\n"
				+ "        <li>\r\n"
				+ "          <button type=\"button\" class=\"dropdown-item d-flex align-items-center\" data-bs-theme-value=\"dark\" aria-pressed=\"false\">\r\n"
				+ "            <svg class=\"bi me-2 opacity-50\" width=\"1em\" height=\"1em\"><use href=\"#moon-stars-fill\"></use></svg>\r\n"
				+ "            Dark\r\n"
				+ "            <svg class=\"bi ms-auto d-none\" width=\"1em\" height=\"1em\"><use href=\"#check2\"></use></svg>\r\n"
				+ "          </button>\r\n"
				+ "        </li>\r\n"
				+ "        <li>\r\n"
				+ "          <button type=\"button\" class=\"dropdown-item d-flex align-items-center active\" data-bs-theme-value=\"auto\" aria-pressed=\"true\">\r\n"
				+ "            <svg class=\"bi me-2 opacity-50\" width=\"1em\" height=\"1em\"><use href=\"#circle-half\"></use></svg>\r\n"
				+ "            Auto\r\n"
				+ "            <svg class=\"bi ms-auto d-none\" width=\"1em\" height=\"1em\"><use href=\"#check2\"></use></svg>\r\n"
				+ "          </button>\r\n"
				+ "        </li>\r\n"
				+ "      </ul>\r\n"
				+ "    </div>\r\n"
				+ "\r\n"
				+ "    \r\n"
				+ "<main>\r\n"
				+ "  <div class=\"container py-4\">\r\n"
				+ "    <header class=\"pb-3 mb-4 border-bottom\">\r\n"
				+ "      <a href='./alumno' class=\"d-flex align-items-center text-body-emphasis text-decoration-none\">\r\n"
				+ "        <span class=\"fs-4\">DEW ~ 2023/2024</span>\r\n"
				+ "      </a>\r\n"
				+ "    </header>\r\n"
				+ "\r\n"
				+ "    <div class=\"p-5 mb-4 bg-body-tertiary rounded-3\">\r\n"
				+ "      <div class=\"container-fluid py-5\">\r\n"
				+ "        <h1 class=\"display-5 fw-bold\">"+jsonAsig.getString("nombre")+"</h1>\r\n"
				+ "      </div>\r\n"
				+ "    </div>\r\n"
				+ "    \r\n"
				+ "\r\n"
				+ "    <h2 class=\"mt-4\">Aquí puedes consultar tus evaluaciones</h2>\r\n"
				+ "    <hr>  \r\n"
				+ "    <div class=\"row mb-3 text-center\">\r\n"
				+ "      <div class=\"col-md-4 themed-grid-col\">"+asig+"</div>\r\n"
				+ "      <div class=\"col-md-4 themed-grid-col\">Calificación:</div>\r\n"
				+ "      <div class=\"col-md-4 themed-grid-col\">"+nota+"</div>\r\n"
				+ "    </div>\r\n"
				+ "\r\n"
				+ "    <h2 class=\"mt-4\">Información sobre la asignatura</h2>\r\n"
				
				+ "    <ul>\r\n"
				+ "      <li>Curso:  " +jsonAsig.getInt("curso")+ "</li>"
				+ "      <li>Cuatrimestre:  " +jsonAsig.getString("cuatrimestre")+ "</li>"
				+ "      <li>Créditos: "  +jsonAsig.getDouble("creditos")+ "</li>\r\n"
				+ "    </ul>\r\n"
				+ "\r\n"
				+ "    <footer class=\"pt-3 mt-4 text-body-secondary border-top\">\r\n"
				+ "      Trabajo en grupo realizado para la asignatura de Desarrollo Web. Curso 2023/2024\r\n"
				+ "    </footer>\r\n"
				+ "  </div>\r\n"
				+ "</main>\r\n"
				+ "<script src=\"./assets/dist/js/bootstrap.bundle.min.js\"></script>\r\n"
				+ "\r\n"
				+ "    </body>\r\n"
				+ "</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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

}
