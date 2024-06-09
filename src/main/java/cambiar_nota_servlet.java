

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

/**
 * Servlet implementation class cambiar_nota_servlet
 */
public class cambiar_nota_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://localhost:9090/CentroEducativo";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public cambiar_nota_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion = request.getSession();
		String dni = request.getParameter("dni");
        String asignatura = request.getParameter("asig");
        String nota = 		request.getParameter("nota");
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
           
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
          
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Procesar la respuesta
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    StringBuilder responseContent = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        responseContent.append(inputLine);
                    }           
        			response.sendRedirect(request.getContextPath() + "/detalle_alumno?dni="+dni+"&asig="+asignatura);

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
                  
                }
                
            
            }
            
            con.disconnect();
		} catch (Exception e) {
			
			/* En  caso de que falle, redirigimos a la pagina de error correspondiente*/
			response.sendRedirect(request.getContextPath() + "/error_centro_educativo.html");
			return;
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
