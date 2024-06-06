

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet implementation class cambiar_nota
 */
public class cambiar_nota extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://localhost:9090/CentroEducativo";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public cambiar_nota() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//A FALTA DE PROTEGER EL SERVLET DE ACCESOS NO AUTORIZADOS (NO KEY, NO PROFE, ETC)
		
		
		HttpSession sesion = request.getSession();

		//Obtener json recibido
		
		StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = request.getReader();
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } catch (IOException ex) {
            throw new ServletException("Error leyendo el cuerpo de la petición", ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw new ServletException("Error cerrando el BufferedReader", ex);
                }
            }
        }

        String body = stringBuilder.toString();

        JSONObject jsonObject = new JSONObject(body);

        String dni = jsonObject.getString("dni");
        String asignatura = jsonObject.getString("asignatura");
        String nota = 		jsonObject.getString("nota");

        
		
		try {
			/* Creamos una conexión PUT a centro educativo */
			String uri = urlCE+"/alumnos/" + dni + "/asignaturas/"+asignatura+"?key="+sesion.getAttribute("key").toString();
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			con.setDoInput(true);
		    String cookie = (String) sesion.getAttribute("cookies");
		    con.setRequestProperty("Cookie", cookie.split(";", 2)[0]);
		    con.setRequestProperty("Content-Type", "application/json");
			
		    try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
		        wr.writeBytes(nota);
		        wr.flush();
		    }
            
            int responseCode = con.getResponseCode();
            jsonObject.put("key", sesion.getAttribute("key").toString());
            jsonObject.put("uri", uri);
            jsonObject.put("cookie", cookie.split(";", 2)[0]);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            jsonObject.put("response code", responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Procesar la respuesta
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    StringBuilder responseContent = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        responseContent.append(inputLine);
                    }           
                    jsonObject.put("response", responseContent);
                    out.print(jsonObject);
                }
            } else {
            	// Leer el mensaje de error del servidor
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String inputLine;
                    StringBuilder errorContent = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        errorContent.append(inputLine);
                    }
                    System.out.println("Error Content: " + errorContent.toString());
                    jsonObject.put("response", errorContent);
                }
                
            	out.print(jsonObject);
            }
            
            con.disconnect();
		} catch (Exception e) {
			
			/* En  caso de que falle, redirigimos a la pagina de error correspondiente*/
			response.sendRedirect(request.getContextPath() + "/error_centro_educativo.html");
			return;
		}
		
		
		
		
		
	}

}
