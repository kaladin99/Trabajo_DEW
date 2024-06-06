

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

/**
 * Servlet implementation class prueba_nota_media
 */
public class prueba_nota_media extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://localhost:9090/CentroEducativo";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public prueba_nota_media() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String asignatura = request.getParameter("asig");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		HttpSession sesion = request.getSession();
		
		int error = -25;
		int nota = 0;
		int numNotas = 0;
		int res = 0;
		// agafe el alumnes i els recorrec
		String alumnos = fetchGet(request, "/alumnos");
		JSONArray alumnosJSON= new JSONArray(alumnos);
		out.println("funciona el print");
		out.println(alumnosJSON);
		
		try {
			out.println("entrada");
			for( int i = 0; i<alumnosJSON.length(); i++){
				out.println("dins primer for");
				// de cada alumne recorrec les assignatures que te 
				String dnialum = alumnosJSON.getJSONObject(i).getString("dni");
				out.println(dnialum);
				String alumnosasig = fetchGet(request, "/alumnos/"+ dnialum + "/asignaturas");
				out.println("alumnoasig = "+ alumnosasig);
				JSONArray asigalumJSON= new JSONArray(alumnosasig);
				out.println("asigalumJSON = "+ asigalumJSON);
				
				for (int j = 0; j < asigalumJSON.length();j++) {
					
					
					// si la assignatura es la que esta en la capçalera i no es null la afegisc a nota
					String asignaturaAlumno = asigalumJSON.getJSONObject(j).getString("asignatura");
					out.println("asignaturaAlumno = "+ asignaturaAlumno);
					//out.println("la asignatura per parametro es  = "+ asignatura);
					
					if(asignaturaAlumno.equals(asignatura)) {
						out.println("abans de notaasigalum = ");
						try {
							String notaAsigAlum =  asigalumJSON.getJSONObject(j).getString("nota");
							out.println("notaAsigAlum = "+ notaAsigAlum);
							if(!notaAsigAlum.equals("")) {
								int intNotaAsigAlum = Integer.parseInt(notaAsigAlum);
								nota += intNotaAsigAlum;
								numNotas ++;
								out.println( "la nota en la iteració "+i+ "es: "+ nota);
							}
						}catch(Exception e) {
							
						}
					}
				}
			
			
			}
				
		}catch(Exception e) {
			
			out.println("prueba de salida de error");
			out.println(error);
			
		}
		try {

			res = nota/numNotas;
			out.println(res);
			
		}catch (Exception e) {
			out.println("bro que estas fent");
		}
	
		
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

