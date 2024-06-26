

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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class evaluar
 */
public class evaluar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://localhost:9090/CentroEducativo";

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
    	HttpSession sesion = request.getSession();
		String asig = request.getParameter("asig");

		if (asig == null) {
			response.sendRedirect(request.getContextPath() + "/not_found_error.html");
			return;
		}
		
    	boolean isProfesorValido = true;
    	
    	if(sesion.getAttribute("key") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
    	
    	String profesores = fetchGet(request, "/profesores");
		JSONArray profesoresJSON= new JSONArray(profesores);
		JSONArray asignaturasProfesorJSON = new JSONArray();
		for( int i = 0; i<profesoresJSON.length(); i++){
			String profesor_dni = profesoresJSON.getJSONObject(i).getString("dni");
			if (profesor_dni.equals(sesion.getAttribute("dni"))) {
				String asignaturasProfesor = fetchGet(request, "/profesores/" + profesor_dni + "/asignaturas");
				asignaturasProfesorJSON = new JSONArray(asignaturasProfesor);
				for( int x = 0; x<asignaturasProfesorJSON.length(); x++){
					String acronimo_asignatura = "";
					acronimo_asignatura = asignaturasProfesorJSON.getJSONObject(x).getString("acronimo");
			
					if (acronimo_asignatura.equals(asig)) {
						isProfesorValido = true;
						break;
					}
				}
				
				if (isProfesorValido) {
		            break;
		        }
				
			}
		}
		
		if (isProfesorValido == false) {
			response.sendRedirect(request.getContextPath() + "/not_found_error.html");
			return;
		}

		String preHTML5 = "<!doctype html>\n" +
		        "<html lang=\"en\" data-bs-theme=\"auto\">\n" +
		        "  <head><script src=\"./assets/js/color-modes.js\"></script>\n" +
		        "\n" +
		        "    <meta charset=\"utf-8\">\n" +
		        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
		        "    <meta name=\"description\" content=\"\">\n" +
		        "    <meta name=\"author\" content=\"Mark Otto, Jacob Thornton, and Bootstrap contributors\">\n" +
		        "    <meta name=\"generator\" content=\"Hugo 0.122.0\">\n" +
		        "    <title>Evaluar alumnos</title>\n" +
		        "\n" +
		        "    <link rel=\"canonical\" href=\"https://getbootstrap.com/docs/5.3/examples/pricing/\">\n" +
		        "\n" +
		        "    \n" +
		        "\n" +
		        "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/@docsearch/css@3\">\n" +
		        "\n" +
		        "<link href=\"./assets/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
		        "\n" +
		        "    <style>\n" +
		        "      button#btn_right svg {\n" +
		        "           width: 75%;\n" +
		        "      		height: 100%;\n" +
		        "      }\n" +
		        "      button#btn_left svg {\n" +
		        "           width: 75%;\n" +
		        "      		height: 100%;\n" +
		        "      }\n" +
		        "      button#btn_right {\n" +
		        "           padding: 0;\n" +
		        "      }\n" +
		        "      button#btn_left {\n" +
		        "           padding: 0;\n" +
		        "      }\n" +
		        "      .bd-placeholder-img {\n" +
		        "        font-size: 1.125rem;\n" +
		        "        text-anchor: middle;\n" +
		        "        -webkit-user-select: none;\n" +
		        "        -moz-user-select: none;\n" +
		        "        user-select: none;\n" +
		        "      }\n" +
		        "\n" +
		        "      @media (min-width: 768px) {\n" +
		        "        .bd-placeholder-img-lg {\n" +
		        "          font-size: 3.5rem;\n" +
		        "        }\n" +
		        "      }\n" +
		        "\n" +
		        "      .b-example-divider {\n" +
		        "        width: 100%;\n" +
		        "        height: 3rem;\n" +
		        "        background-color: rgba(0, 0, 0, .1);\n" +
		        "        border: solid rgba(0, 0, 0, .15);\n" +
		        "        border-width: 1px 0;\n" +
		        "        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);\n" +
		        "      }\n" +
		        "\n" +
		        "      .b-example-vr {\n" +
		        "        flex-shrink: 0;\n" +
		        "        width: 1.5rem;\n" +
		        "        height: 100vh;\n" +
		        "      }\n" +
		        "\n" +
		        "      .bi {\n" +
		        "        vertical-align: -.125em;\n" +
		        "        fill: currentColor;\n" +
		        "      }\n" +
		        "\n" +
		        "      .nav-scroller {\n" +
		        "        position: relative;\n" +
		        "        z-index: 2;\n" +
		        "        height: 2.75rem;\n" +
		        "        overflow-y: hidden;\n" +
		        "      }\n" +
		        "\n" +
		        "      .nav-scroller .nav {\n" +
		        "        display: flex;\n" +
		        "        flex-wrap: nowrap;\n" +
		        "        padding-bottom: 1rem;\n" +
		        "        margin-top: -1px;\n" +
		        "        overflow-x: auto;\n" +
		        "        text-align: center;\n" +
		        "        white-space: nowrap;\n" +
		        "        -webkit-overflow-scrolling: touch;\n" +
		        "      }\n" +
		        "\n" +
		        "      .btn-bd-primary {\n" +
		        "        --bd-violet-bg: #712cf9;\n" +
		        "        --bd-violet-rgb: 112.520718, 44.062154, 249.437846;\n" +
		        "\n" +
		        "        --bs-btn-font-weight: 600;\n" +
		        "        --bs-btn-color: var(--bs-white);\n" +
		        "        --bs-btn-bg: var(--bd-violet-bg);\n" +
		        "        --bs-btn-border-color: var(--bd-violet-bg);\n" +
		        "        --bs-btn-hover-color: var(--bs-white);\n" +
		        "        --bs-btn-hover-bg: #6528e0;\n" +
		        "        --bs-btn-hover-border-color: #6528e0;\n" +
		        "        --bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);\n" +
		        "        --bs-btn-active-color: var(--bs-btn-hover-color);\n" +
		        "        --bs-btn-active-bg: #5a23c8;\n" +
		        "        --bs-btn-active-border-color: #5a23c8;\n" +
		        "      }\n" +
		        "\n" +
		        "      .bd-mode-toggle {\n" +
		        "        z-index: 1500;\n" +
		        "      }\n" +
		        "\n" +
		        "      .bd-mode-toggle .dropdown-menu .active .bi {\n" +
		        "        display: block !important;\n" +
		        "      }\n" +
		        "\n" +
		        "      .tarjeta button {\n" +
		        "        width: 60px !important;\n" +
		        "        height: 40px !important;\n" +
		        "      }\n" +
		        "    </style>\n" +
		        "    \n" +
		        "    <title>Evaluar</title>\n" +
		        "    <script src=\"https://code.jquery.com/jquery-3.7.1.min.js\"></script>\n" +
		        "    <script src=\"./js/evaluar_alumnos.js\"></script>\n" +
		        "    <!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./css/evaluar_ajax.css\">-->\n" +
		        "\n" +
		        "    \n" +
		        "  </head>\n" +
		        "  <body>\n" +
		        "    <div id=\"toast-container\" class=\"position-fixed top-0 end-0 p-3\" style=\"z-index: 1100;\"></div>\r\n"+
		        "    <svg xmlns=\"http://www.w3.org/2000/svg\" class=\"d-none\">\n" +
		        "      <symbol id=\"check2\" viewBox=\"0 0 16 16\">\n" +
		        "        <path d=\"M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z\"/>\n" +
		        "      </symbol>\n" +
		        "      <symbol id=\"circle-half\" viewBox=\"0 0 16 16\">\n" +
		        "        <path d=\"M8 15A7 7 0 1 0 8 1v14zm0 1A8 8 0 1 1 8 0a8 8 0 0 1 0 16z\"/>\n" +
		        "      </symbol>\n" +
		        "      <symbol id=\"moon-stars-fill\" viewBox=\"0 0 16 16\">\n" +
		        "        <path d=\"M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z\"/>\n" +
		        "        <path d=\"M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z\"/>\n" +
		        "      </symbol>\n" +
		        "      <symbol id=\"sun-fill\" viewBox=\"0 0 16 16\">\n" +
		        "        <path d=\"M8 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z\"/>\n" +
		        "      </symbol>\n" +
		        "    </svg>\n" +
		        "\n" +
		        "    <div class=\"dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle\">\n" +
		        "      <button class=\"btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center\"\n" +
		        "              id=\"bd-theme\"\n" +
		        "              type=\"button\"\n" +
		        "              aria-expanded=\"false\"\n" +
		        "              data-bs-toggle=\"dropdown\"\n" +
		        "              aria-label=\"Toggle theme (auto)\">\n" +
		        "        <svg class=\"bi my-1 theme-icon-active\" width=\"1em\" height=\"1em\"><use href=\"#circle-half\"></use></svg>\n" +
		        "        <span class=\"visually-hidden\" id=\"bd-theme-text\">Toggle theme</span>\n" +
		        "      </button>\n" +
		        "      <ul class=\"dropdown-menu dropdown-menu-end shadow\" aria-labelledby=\"bd-theme-text\">\n" +
		        "        <li>\n" +
		        "          <button type=\"button\" class=\"dropdown-item d-flex align-items-center\" data-bs-theme-value=\"light\" aria-pressed=\"false\">\n" +
		        "            <svg class=\"bi me-2 opacity-50\" width=\"1em\" height=\"1em\"><use href=\"#sun-fill\"></use></svg>\n" +
		        "            Light\n" +
		        "            <svg class=\"bi ms-auto d-none\" width=\"1em\" height=\"1em\"><use href=\"#check2\"></use></svg>\n" +
		        "          </button>\n" +
		        "        </li>\n" +
		        "        <li>\n" +
		        "          <button type=\"button\" class=\"dropdown-item d-flex align-items-center\" data-bs-theme-value=\"dark\" aria-pressed=\"false\">\n" +
		        "            <svg class=\"bi me-2 opacity-50\" width=\"1em\" height=\"1em\"><use href=\"#moon-stars-fill\"></use></svg>\n" +
		        "            Dark\n" +
		        "            <svg class=\"bi ms-auto d-none\" width=\"1em\" height=\"1em\"><use href=\"#check2\"></use></svg>\n" +
		        "          </button>\n" +
		        "        </li>\n" +
		        "        <li>\n" +
		        "          <button type=\"button\" class=\"dropdown-item d-flex align-items-center active\" data-bs-theme-value=\"auto\" aria-pressed=\"true\">\n" +
		        "            <svg class=\"bi me-2 opacity-50\" width=\"1em\" height=\"1em\"><use href=\"#circle-half\"></use></svg>\n" +
		        "            Auto\n" +
		        "            <svg class=\"bi ms-auto d-none\" width=\"1em\" height=\"1em\"><use href=\"#check2\"></use></svg>\n" +
		        "          </button>\n" +
		        "        </li>\n" +
		        "      </ul>\n" +
		        "    </div>\n" +
		        "\n" +
		        "    <svg xmlns=\"http://www.w3.org/2000/svg\" class=\"d-none\">\n" +
		        "  <symbol id=\"check\" viewBox=\"0 0 16 16\">\n" +
		        "    <title>Check</title>\n" +
		        "    <path d=\"M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z\"/>\n" +
		        "  </symbol>\n" +
		        "</svg>\n" +
		        "\n" +
		        "<div class=\"container py-3\">\n" +
		        "  <header>\n" +
		        "    <div class=\"d-flex flex-column flex-md-row align-items-center pb-3 mb-4 border-bottom\">\n" +
		        "      <a href=\"/\" class=\"d-flex align-items-center link-body-emphasis text-decoration-none\">\n" +
		        "        \n" +
		        "        <span class=\"fs-4\">DEW ~ 2023/2024</span>\n" +
		        "      </a>\n" +
		        "\n" +
		        "\n" +
		        "    </div>\n" +
		        "\n" +
		        "    <div class=\"pricing-header p-3 pb-md-4 mx-auto text-center\">\n" +
		        "      <h1 class=\"display-4 fw-normal text-body-emphasis\">Evaluar alumnos</h1>\n" +
		        "      <p id=\"alumnos\"></p> \n" +
		        "    </div>\n" +
		        "  </header>\n" +
		        "\n" +
		        "  <main>\n" +
		        "    <div class=\"row row-cols-1 row-cols-md-3 mb-3 text-center\">\n" +
		        "\n" +
		        "      <div class=\"col\">\n" +
		        "      </div>\n" +
		        "\n" +
		        "      <div class=\"col\">\n" +
		        "        <div class=\"card mb-4 rounded-3 shadow-sm\">\n" +
		        "          <div class=\"card-header py-3\">\n" +
		        "            <h4 id=\"alumno_nombre\" class=\"my-0 fw-normal\">NOMBRE DEL ALUMNO</h4>\n" +
		        "          </div>\n" +
		        "          <div class=\"card-body\">\n" +
		        "            <div class=\"tarjeta\">\n" +
		        "              <img id=\"foto_alumno_base64\" src=\"\" alt=\"foto del alumno\"/>\n" +
		        "              <!--<p id=\"alumno_nombre\"></p>-->\n" +
		        "              <div class=\"arrows_container\" style=\"margin-top: 20px;\">\n" +
		        "                  <button id=\"btn_left\" type=\"button\" class=\"w-100 btn btn-lg btn-primary\">"+
		        "						<svg width=\"100\" height=\"50\" viewBox=\"0 0 100 50\" xmlns=\"http://www.w3.org/2000/svg\">"+
		        "							<polygon points=\"40,10 10,25 40,40\" style=\"fill: black;\" />"+
		        "							<line x1=\"10\" y1=\"25\" x2=\"90\" y2=\"25\" style=\"stroke: black; stroke-width: 2;\" />"+
		        "						</svg>"+
		        "                  </button>\n" +
		        "                  <input type=\"number\" id=\"nota\" min=\"0\" max=\"10\" step=\".01\"/>\n" +
		        "                  <button id=\"btn_right\" type=\"button\" class=\"w-100 btn btn-lg btn-primary\">"+
		        "						<svg width=\"100\" height=\"50\" viewBox=\"0 0 100 50\" xmlns=\"http://www.w3.org/2000/svg\">"+
			    "    						<polygon points=\"60,10 90,25 60,40\" style=\"fill: black;\" />"+
			    "    						<line x1=\"10\" y1=\"25\" x2=\"90\" y2=\"25\" style=\"stroke: black; stroke-width: 2;\" />"+
			    "   					</svg>"+
		        "				   </button>\n" +
		        "              </div>\n" +
		        "            </div>\n" +
		        "            <input type=\"hidden\" id=\"asignatura\" value=\"asig\"/>\n" +
		        "            \n" +
		        "          </div>\n" +
		        "        </div>\n" +
		        "      </div>\n" +
		        "\n" +
		        "\n" +
		        "\n" +
		        "      <div class=\"col\">        \n" +
		        "      </div>\n" +
		        "\n" +
		        "      \n" +
		        "\n" +
		        "    </div>\n" +
		        "    \n" +
		        "\n" +
		        "  </main>\n" +
		        "\n" +
		        "  <footer class=\"pt-3 mt-4 text-body-secondary border-top\">\n" +
		        "    Trabajo en grupo realizado para la asignatura de Desarrollo Web. Curso 2023/2024\n" +
		        "  </footer>\n" +"</div>\n" +
		        "<script src=\"./assets/dist/js/bootstrap.bundle.min.js\"></script>\n" +
		        "\n" +
		        "    </body>\n" +
		        "</html>\n";



			
				
			
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
