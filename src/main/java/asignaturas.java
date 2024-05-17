

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;


/**
 * Servlet implementation class asignaturas
 */
public class asignaturas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String apiUrl = "http://dew-joaalaz-2324.dsicv.upv.es:9090/CentroEducativo";
	private static final String preHTML5 = "<!DOCTYPE html>\n<html lang=\"es-es\">\n" +
			"<head>\n<meta charset=\"utf-8\" />\n" +
			"<title>Mi Gato</title>\n</head>\n<body>\n" +
			"<h1>Foto de un gato</h1>\n<h2>Recarga para ver otros...</h2>";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public asignaturas() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		try {
			// establecemos y abrimos la conexión
			URL url = new URL(apiUrl);
			HttpURLConnection conection = (HttpURLConnection) url.openConnection();
			
			conection.setDoInput(true);
			conection.setRequestMethod("POST");
			conection.setRequestProperty("accept", "application/json");
			
			//Try(BufferedReader br = new BuferedReader)
			
		}catch(Exception e) {
			
		}
		
	}

}
