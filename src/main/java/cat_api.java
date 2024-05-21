

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

import org.json.*;





/**
 * Servlet implementation class cat_api
 */
public class cat_api extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String apiUrl = "https://api.thecatapi.com/v1/images/search";
	private static final String preHTML5 = "<!DOCTYPE html>\n<html lang=\"es-es\">\n" +
			"<head>\n<meta charset=\"utf-8\" />\n" +
			"<title>Mi Gato</title>\n</head>\n<body>\n" +
			"<h1>Foto de mi gato</h1>\n<h2>Recarga para ver otros...</h2>";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cat_api() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		try {
			// establecemos y abrimos la conexión
			URL url = new URL(apiUrl);
			HttpURLConnection conection = (HttpURLConnection) url.openConnection();
			
			conection.setDoInput(true);
			conection.setRequestMethod("GET");
			conection.setRequestProperty("accept", "application/json");
			//creamos un buffer de lectura con los datos obtenidos de la conexión
			try(BufferedReader br = new BufferedReader(new InputStreamReader(conection.getInputStream(),"utf-8"))){
				
				String r = "";
				String resLine = null;
				//mientras haya linea que leer
				while ((resLine = br.readLine())!=null) {
					//lo vamos metiendo todo en r , trim quita los espacios en blanco del inicio y el final 
					r += resLine.trim();
					
				}
				JSONArray cats = new JSONArray(r);
				JSONObject cat = cats.getJSONObject(0);
				out.print(preHTML5);
				//out.println("<div><img src=\""+cat.getString("url")+"\" alt=\"el gato\"/></div>");
				out.println(r);
				out.println("<div><img src=\"" + cat.getString("url") + "\" alt=\"el gato\"/></div>");
				
				out.println("</body></html>");
				
			}
			
		}catch(Exception e) {
			response.sendError(500,"Hubo problemas al recuperar la información" +e);
			
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
