

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.*;


/**
 * Servlet implementation class vista_alumno
 */
public class vista_alumno extends HttpServlet {
	/* Class para crear un map con usuarios del Centro Educativo */
		
	private String urlCE = "http://localhost:9090/CentroEducativo";
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
		HttpSession sesion = request.getSession();
		
		
		if(sesion.getAttribute("key") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String key = sesion.getAttribute("key").toString();
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = response.getWriter();
		out.println(preHTML5);
		
		String asignaturas = fetchGet(request, "/asignaturas");
		out.println("<p> res.toString(): "+asignaturas+"</p>");
		
		String alumno = fetchGet(request, "/alumnos/"+sesion.getAttribute("dni"));

		out.println("<p> ALUMNO: "+alumno+"</p>");
		
		JSONArray asignaturasJSON = new JSONArray(asignaturas);
		JSONObject asignatura0 = asignaturasJSON.getJSONObject(0);
		out.println("<p> asignatura0: "+asignatura0+"</p>");
	
		out.println("</body></html>");

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
