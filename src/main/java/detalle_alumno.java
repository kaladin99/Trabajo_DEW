

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
 * Servlet implementation class detalle_alumno
 */
public class detalle_alumno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://localhost:9090/CentroEducativo";
	private String HTML= "<!DOCTYPE html>\r\n"
			+ "<html>\r\n"
			+ "<head>\r\n"
			+ "<meta charset=\"UTF-8\">\r\n"
			+ "<title>Insert title here</title>\r\n"
			+ "</head>\r\n";

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
		String asig = request.getParameter("nameAsignatura");
		
		if(sesion.getAttribute("key") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = response.getWriter();
		out.println(HTML);
		String alumno = fetchGet(request, "/alumnos/"+sesion.getAttribute("dni"));
		JSONObject nombreAlu = new JSONObject(alumno);        
        String nombre = nombreAlu.getString("nombre");
        String apellidos = nombreAlu.getString("apellidos");
        String nombreApellido = nombre+" "+apellidos;
        String asignaturaAlumno = fetchGet(request, "/alumnos/"+sesion.getAttribute("dni")+"/asignaturas");
		JSONArray asignaturasJSON = new JSONArray(asignaturaAlumno);
		String res="";
		for(int i = 0; i< asignaturasJSON.length(); i++) {
			String acronimo_asig = asignaturasJSON.getJSONObject(i).getString("asignatura");
			res+= acronimo_asig + " ";
		}
		out.println("<body>\r\n"
				+ "\t<p>DNI PRUEBA ALUMNO</p>\r\n"
				+ "\t<p>" + apellidos + ", " + nombre + " (" + sesion.getAttribute("dni") + ")" + "</br>" + res + "</p>\r\n"
				+ "<img src=\"fotos/h/1.png\" alt=\"Ejemplo de imagen\">"
				+ "</body>\r\n"
				+ "</html>\r\n");
        
		
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
