

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
 * Servlet implementation class alumnos_de_asignatura
 */
public class alumnos_de_asignatura extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://localhost:9090/CentroEducativo";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public alumnos_de_asignatura() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		String asig = request.getParameter("asig");
		
		if(sesion.getAttribute("key") == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		//String key = sesion.getAttribute("key").toString();
		
		String alumnosDeAsignatura = fetchGet(request, "/asignaturas/" + asig + "/alumnos");
		JSONArray alumnosDeAsignaturaJSON = new JSONArray(alumnosDeAsignatura);
		JSONArray jsonResponse = new JSONArray();
		
		for(int i = 0; i< alumnosDeAsignaturaJSON.length(); i++) {
			String dni = alumnosDeAsignaturaJSON.getJSONObject(i).getString("alumno");
			String alumno = fetchGet(request, "/alumnos/" + dni);
			JSONObject alumnoJSON = new JSONObject(alumno);
			
			//OBTENER FOTO EN BASE64 DEL ALUMNO
			File file = new File(getServletContext().getRealPath("/assets/fotos/" + dni + ".pngb64"));
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String fileContentString = new String(fileContent, StandardCharsets.UTF_8);
            
			alumnoJSON.put("nota", alumnosDeAsignaturaJSON.getJSONObject(i).getString("nota"));
			alumnoJSON.put("img", fileContentString);
			jsonResponse.put(alumnoJSON);
		}
		
		
		
		PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonResponse);

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
