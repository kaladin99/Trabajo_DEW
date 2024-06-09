

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
 * Servlet implementation class profesor
 */
public class detalle_asignatura_profesor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private String urlCE = "http://localhost:9090/CentroEducativo";
	private static final String preHTML5 = "<!DOCTYPE html>\r\n"
			+ "<html lang=\"en\" data-bs-theme=\"auto\">\r\n"
			+ "  <head><script src=\"./assets/js/color-modes.js\"></script>\r\n"
			+ "\r\n"
			+ "    <meta charset=\"utf-8\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
			+ "    <meta name=\"description\" content=\"\">\r\n"
			+ "    <meta name=\"author\" content=\"Mark Otto, Jacob Thornton, and Bootstrap contributors\">\r\n"
			+ "    <meta name=\"generator\" content=\"Hugo 0.122.0\">\r\n"
			+ "    <title>Lista Asignaturas</title>\r\n"
			+ "\r\n"
			+ "    <link rel=\"canonical\" href=\"https://getbootstrap.com/docs/5.3/examples/jumbotron/\">\r\n"
			+ "\r\n"
			+ "    \r\n"
			+ "\r\n"
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
			+ "        background-color: rgba(0, 0, 0, .1);\r\n"
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
			+ "\r\n"
			+ "    \r\n"
			+ "  </head>\r\n"
			+ "  <body>\r\n"
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
			+ "      <a href='./profesor' class=\"d-flex align-items-center text-body-emphasis text-decoration-none\">\r\n"
			+ "        <span class=\"fs-4\">DEW ~ 2023/2024</span>\r\n"
			+ "      </a>\r\n"
			+ "    </header>\r\n";
    public detalle_asignatura_profesor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    			HttpSession sesion = request.getSession();
    			String dniProf = (String) sesion.getAttribute("dni");
    			boolean imparteAsignatura = false;
    			//si no tiene sesion abierta te manda al home
    			if(sesion.getAttribute("key") == null) {
    				response.sendRedirect(request.getContextPath());
    				return;
    			}
    			//si no es un profesor te manda al home
    			String prof = fetchGet(request, "/profesores/"+sesion.getAttribute("dni"));
    			if (prof == "") {
    				response.sendRedirect(request.getContextPath());
    				return;
    			}
    			
    			//Si no es un profesor de la asignatura te manda a /profesor
    			
    			//se obtiene el acronimo de la asignatura
    			String asignaturaAcronimo = request.getParameter("acronimo_asig");
    			
    			//se obtienen los profesor que dan una asignatura
    			String profDeAsig = fetchGet(request, "/asignaturas/"+asignaturaAcronimo+"/profesores");
    			JSONArray profDeAsigJSON = new JSONArray(profDeAsig);
    			
    			//se recorre el json en para ver si el profesor de dniProf imparte docencia en la asignatura asignaturaAcronimo
    			for(int i = 0; i < profDeAsigJSON.length(); i++) {
    				String dniAux = profDeAsigJSON.getJSONObject(i).getString("dni");
    				if(dniAux.equals(dniProf)) {
    					imparteAsignatura = true;
    					break;
    				}
    			}
    			
    			if (!imparteAsignatura) {
    				response.sendRedirect(request.getContextPath() + "/profesor");
    				return;
    			}
    			//String key = sesion.getAttribute("key").toString();
    			
    			response.setContentType("text/html");
    			response.setCharacterEncoding("UTF-8"); 
    			PrintWriter out = response.getWriter();
    			out.println(preHTML5);
    			
    			
    				
    			
    			String alumnosAsignatura = fetchGet(request, "/asignaturas/"+asignaturaAcronimo+"/alumnos");
    			
    	        //JSONObject dni = new JSONObject(alumnos);
    			String error = "";
    			String res = "";
    			
    			try {
    				JSONArray alumnosAsignaturaJSON = new JSONArray(alumnosAsignatura);
    				error = "1";
        	        for(int i = 0; i <= alumnosAsignaturaJSON.length(); i++) {
        	        	String dni = alumnosAsignaturaJSON.getJSONObject(i).getString("alumno");
        	        	error = "2";
        	        	String alumno = fetchGet(request, "/alumnos/"+dni);
        	        	error = "3";
        	        	//JSONArray alumnoDniJSONA = new JSONArray(alumno);
        	        	JSONObject alumnoDniJSON = new JSONObject(alumno);
        	        	error = "4";
        	        	String nombre =  alumnoDniJSON.getString("nombre");
        	        	error = "5";
        	        	String apellido = alumnoDniJSON.getString("apellidos");
        	        	error = "6";
        	        	double nota = Double.parseDouble(alumnosAsignaturaJSON.getJSONObject(i).getString("nota").equals("") ?
        	        			"0" : alumnosAsignaturaJSON.getJSONObject(i).getString("nota"));
        	        	error = "7";
        	        	String nombreApellido = nombre+" "+apellido;
        	        	error = "8";
        	        	String linea = "<li><a href='./detalle_alumno?dni="+dni+"&asig="+asignaturaAcronimo+"'>"+nombreApellido+"</a> "+ "Nota: "+nota+"</li>\r\n";
        	        	error = "9";
        	        	res += linea;
        	        	error = "10";
        	        }
    				
    			}catch (Exception e) {
    				//error = "final";
    				//res = error;
    			}
    	        
    	        	
    	        
    	        
    	       
    	        
    			//String asignaturasProfesor = fetchGet(request, "/profesores/"+sesion.getAttribute("dni")+"/asignaturas");
    			//JSONArray asignaturasJSON = new JSONArray(asignaturasProfesor);
    			/*
    			JSONObject asignatura0 = asignaturasJSON.getJSONObject(0);
    			JSONObject asignatura1 = asignaturasJSON.getJSONObject(1);
    			JSONObject asignatura2 = asignaturasJSON.getJSONObject(2);
    			*/
    			
    				//double media = notaMedia(request, "DEW");
    				//out.println("<h1> Error: "+media+ "</h1>");
    				//out.println("<a href='prueba_nota_media?asig=DEW'> prueba</a>");
    			
    			
    			
    			//System.out.println(media);
    	        /*
    			String res = "";
    			for(int i = 0; i< asignaturasJSON.length(); i++) {
    				String acronimo_asig = asignaturasJSON.getJSONObject(i).getString("acronimo");
    				String nombre_asig = asignaturasJSON.getJSONObject(i).getString("nombre");
    				double nota = notaAlumno(request,acronimo_asig,dni);
    				//double media = notaMedia(request,acronimo_asig);
    				
    				String linea = "<li><a href='./detalle_asignatura?nameAsignatura="+nombre_asig+"'>"+nombre_asig+"</a> "+ "Nota: "+nota+"</li>\r\n";
    				//String linea = "<li>"+acronimo_asig+"</li>\r\n"
    				res += linea;
    			}
    			*/
    			
    			//
    			
    			
    			/*
    			String asig0 = asignatura0.getString("nombre");
    			String asig1 = asignatura1.getString("nombre");
    			String asig2 = asignatura2.getString("nombre");
    			*/
    			

    	        
    			out.println("    <div class=\"p-5 mb-4 bg-body-tertiary rounded-3\" style=\"background-image: url('wallpaper.png'); background-size: cover;\">\r\n"
    				        + "      <div class=\"container-fluid py-5\">\r\n"	
    				        + "        <h1 class=\"display-5 fw-bold\" style=\"color: white;\"!important >Notas OnLine. Alumnos de la asignatura "+asignaturaAcronimo+"</h1>\r\n"
    				        + "        <p class=\"col-md-8 fs-4\" style=\"color: white;\"!important>En esta página se muestra la lista de alumnos matriculados.</br>Al pulsar en uno podrás acceder a la vista detallada del alumno seleccionado.</p>\r\n"
    				        + "   <h2 class=\"display-5 fw-bold\" style=\"color: white;\"!important >La nota media de "+asignaturaAcronimo+" es: "+request.getParameter("media")+"</h2>\r\n "
    				        
    				        + "</div>\r\n"
    				        + "    </div>\r\n"
    				        + "    \r\n"
    				        + "\r\n"
    				        + ""
    				        + "    <div class=\"row align-items-md-stretch\">\r\n"
    				        + "      <div class=\"col-md-6\">\r\n"
    				        + "        <div class=\"h-100 p-5 text-bg-dark rounded-3\">\r\n"
    				        +"          <a href='evaluar?asig="+asignaturaAcronimo +"'class='btn btn-primary w-100 py-2' >Evaluar a todos</a>"
    				        + "          <ul>\r\n"
    				        + 					res  
    				        + "        </ul>\r\n"
    				        + "        </div>\r\n"
    				        + "      </div>\r\n"
    				        + "      <div class=\"col-md-6\">\r\n"
    				        + "        <div class=\"h-100 p-5 bg-body-tertiary border rounded-3\">\r\n"
    				        + "\r\n"
    				        + "          <h3>Grupo 3ti12_g1</h3>\r\n"
    				        + "          <ol>\r\n"
    				        + "              <li>Isabel Vallés Bertomeu</li>\r\n"
    				        + "              <li>Elisa Fernández Losada</li>\r\n"
    				        + "              <li>Joan Alcaina Aznar</li>\r\n"
    				        + "              <li>Jorge Estarlich Moreno </li>\r\n"
    				        + "              <li>Javier Felipe Martínez Díaz</li>\r\n"
    				        + "              <li>Joel Revert Vila</li>\r\n"
    				        + "              <li>Pau Haro Acín</li>        \r\n"
    				        + "          </ol>\r\n"
    				        + "          \r\n"
    				        + "        </div>\r\n"
    				        + "      </div>\r\n"
    				        + "    </div>\r\n"
    				        + "\r\n"
    				        + "    <footer class=\"pt-3 mt-4 text-body-secondary border-top\">\r\n"
    				        + "      Trabajo en grupo realizado para la asignatura de Desarrollo Web. Curso 2023/2024\r\n"
    				        + "    </footer>\r\n"
    				        + "  </div>\r\n"
    				        + "</main>\r\n"
    				        + "<script src=\"./assets/dist/js/bootstrap.bundle.min.js\"></script>\r\n"
    				        + "\r\n"
    				        + "    </body>\r\n"
    				        + "</html>" );
    			

    		}

    		/**
    		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    		 */
    		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    			// TODO Auto-generated method stub
    			doGet(request, response);
    		}
    		// calcular la nota media se debe pasar el acronimo de la asignatura
    		
    		private double notaAlumno(HttpServletRequest request, String asignatura, String dni ){
    			double error = 0;
    			
    			// agafe el alumnes i els recorrec
    			String asignaturasAlumnos = fetchGet(request, "/alumnos/"+dni+"/asignaturas");
    			JSONArray asignaturaAlumnosJSON= new JSONArray(asignaturasAlumnos);
    			
    			try {
    				
        			
        			for( int i = 0; i<asignaturaAlumnosJSON.length(); i++){
        				// de cada alumne recorrec les assignatures que te 
        				//String dnialum = alumnosJSON.getJSONObject(i).getString("dni");
        				
        				//String alumnosasig = fetchGet(request, "/alumnos/"+ dnialum + "/asignaturas");
        				
        				//JSONArray asigalumJSON= new JSONArray(alumnosasig);
        				
        				String asignaturaAlumno = asignaturaAlumnosJSON.getJSONObject(i).getString("asignatura");
        				
        				
        					
        					
        					// si la assignatura es la que esta en la capçalera i no es null la afegisc a nota
        					
        					
        					if(asignaturaAlumno.equals(asignatura)) {
        						
        						String notaAsigAlum =  asignaturaAlumnosJSON.getJSONObject(i).getString("nota");
        						double intNotaAsigAlum = Double.parseDouble(notaAsigAlum);
        						return intNotaAsigAlum;
        					}
        						
        					
        				
        				
        			}
        				
        			return 0;
    			}catch(Exception e) {
    				error= -1;
    				return error;
    			}
    				
    		}
    		
    		
    		private void ponerNotasRandom(HttpServletRequest request) {
    			int nota = 0;
    			int numNotas = 0;
    			String alumnos = fetchGet(request, "/alumnos");
    			JSONArray alumnosJSON= new JSONArray(alumnos);
    			for( int i = 0; i<alumnosJSON.length(); i++){
    				String dnialum = alumnosJSON.getJSONObject(i).getString("dni");
    				String alumnosasig = fetchGet(request, "/alumnos"+ dnialum + "asignaturas");
    				JSONArray asigalumJSON= new JSONArray(alumnosasig);
    				for (int j = 0; j < asigalumJSON.length();j++) {
    					String asignaturaAlumno = alumnosJSON.getJSONObject(j).getString("asignatura");
    					double randomDouble = Math.random(); 
    			        // Escala el número aleatorio a un rango entre 0 y 10
    			        int randomNumber = (int) (randomDouble * 11);
    			        
    			        
    				}
    				
    			}
    			
    			
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
