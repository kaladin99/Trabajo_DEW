import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class certificado
 */
public class certificado extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://localhost:9090/CentroEducativo";
	private String preHTML5 = "<!DOCTYPE html>\r\n"
		    + "<html lang=\"es\">\r\n"
		    + "<head>\r\n"
		    + "    <meta charset=\"UTF-8\">\r\n"
		    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
		    + "    <title>Certificado Imprimible</title>\r\n"
		    + "    <link rel=\"stylesheet\" href=\"css/estilo_certificado.css\" type=\"text/css\">\r\n"
		    + "</head>\r\n";
    private String nota;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public certificado() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		String dni = (String) sesion.getAttribute("dni");

		//se comprueba que el usuario tiene sesion abierta
		if(sesion.getAttribute("key") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}

		//se comprueba que el usuario es un alumno
		String alumno2 = fetchGet(request, "/alumnos/"+dni);
		if (alumno2 == "") {
			response.sendRedirect(request.getContextPath());
			return;
		}


		String asignaturaAlumno = fetchGet(request, "/alumnos/"+dni+"/asignaturas");
		JSONArray asignaturasJSON = new JSONArray(asignaturaAlumno);
		if (asignaturasJSON.length() == 0 ) {
			response.sendRedirect(request.getContextPath());
			return;
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = response.getWriter();
		out.println(preHTML5);

		//Obtención del nombre y apellidos del alumno
		String alumno = fetchGet(request, "/alumnos/"+dni);
        JSONObject nombreAlu = new JSONObject(alumno);        
        String nombre = nombreAlu.getString("nombre");
        String apellidos = nombreAlu.getString("apellidos");
        String nombreApellido = nombre+" "+apellidos;


		System.out.println(asignaturaAlumno);

		String res = "";
		for(int i = 0; i< asignaturasJSON.length(); i++) {

		   	String acronimo_asig = asignaturasJSON.getJSONObject(i).getString("asignatura");
		   	String infoAsig = fetchGet(request, "/asignaturas/"+acronimo_asig);
		   	JSONObject jsonInfoAsig = new JSONObject(infoAsig);
			String nombre_asig = jsonInfoAsig.getString("nombre");
			double cred_asig =jsonInfoAsig.getDouble("creditos");
			int curso_asig =jsonInfoAsig.getInt("curso");


	        JSONObject foundObject = null;

	        for (int j = 0; i < asignaturasJSON.length(); j++) {
	            JSONObject jsonObject = asignaturasJSON.getJSONObject(j);
	            if (jsonObject.getString("asignatura").equals(acronimo_asig)) {
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
			String linea =  "<tr>\r\n" + "<td>"+acronimo_asig+"</td>\r\n" + "<td>"+nombre_asig+"</td>\r\n" + "<td>"+cred_asig+"</td>\r\n" + "<td>"+curso_asig+"</td>\r\n"+
					"<td>"+nota+"</td>\r\n"+ "</tr>\r\n";
			res += linea;
		}

		LocalDate fechaActual = LocalDate.now();
        int dia = fechaActual.getDayOfMonth();
        int numeroMes = fechaActual.getMonthValue();
        int año = fechaActual.getYear();

        // Convertir el número del mes a Month
        java.time.Month mes = java.time.Month.of(numeroMes);

        // Obtener el nombre del mes en español
        String nombreMes = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

        out.println( "<body>\r\n"
    		    + "    <div class=\"container\">\r\n"
    		    + "        <h1>Certificado sin validez acad&eacute;mica</h1>\r\n"
    		    + "        <div class=\"text-image\">\r\n"
    		    + "            <p class=\"text\">\r\n"
    		    + "                DEW 23/24 certifica que el alumn@ "+nombreApellido+", con DNI "+dni+", matriculado en el curso 2023/2024, ha obtenido\r\n"
    		    + "                las calificaciones que se muestran en la siguiente tabla:\r\n"
    		    + "            </p>\r\n"
    		    + "            <img src=\"fotos/h/1.png\" alt=\"Imagen del alumno\" class=\"image\">\r\n"
    		    + "        </div>\r\n"
    		    + "        <table class=\"table\">\r\n"
    		    + "            <thead>\r\n"
    		    + "                <tr>\r\n"
    		    + "                    <th>Acr&oacute;nimo</th>\r\n"
    		    + "                    <th>Asignatura</th>\r\n"
    		    + "                    <th>Cr&eacute;ditos</th>\r\n"
    		    + "                    <th>Curso</th>\r\n"
    		    + "                    <th>Nota</th>\r\n"
    		    + "                </tr>\r\n"
    		    + "            </thead>\r\n"
    		    + "            <tbody>\r\n"
    		    +					res
    		    + "            </tbody>\r\n"
    		    + "        </table>\r\n"
    		    + "        <p class=\"final\">En Valencia, a "+dia+" de "+nombreMes+" de "+año+"</p>\r\n"
    		    + "    </div>\r\n"
    		    + "</body>\r\n"
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