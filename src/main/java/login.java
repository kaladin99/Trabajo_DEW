import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.*;


/**
 * Servlet implementation class login
 */
public class login extends HttpServlet {
	
	/* La finalidad de esta clase es unicamente para la realización del HashMap para la lista de usuarios del centro educativo */
	class UserCE {
		private String username;
		private String password;
	    public UserCE(String username, String password) {
	       this.username = username;
	       this.password = password;
	    }
	    
	    public String getUsername() { return this.username; }
	    public String getPassword() { return this.password; }
	}
	 
	/* Este hashmap contiene todos los usuarios disponibles del centro eductativo y su relacion de credenciales entre tomcat y centro educativo */
	Map<String, UserCE> users = new HashMap<String, UserCE>() {{
        put("12345678W", new UserCE("12345678W", "123456"));

	}};
	
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://localhost:9090/CentroEducativo";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
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

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); 
		
		/* Obtenemos la sesion actual, si no existe la crea*/
		HttpSession sesion = request.getSession();
		String tomcatUser = request.getRemoteUser(); //Nombre de usuario autenticado mediante tomcat (El alert user/password)
		if(sesion.getAttribute("key") == null) { //En caso de que la sesión que hemos obtenido no tenga key, significa que no la habiamos creado nosotros y es nueva
			if (tomcatUser != null) {
				sesion.setAttribute("dni", users.get(tomcatUser).getUsername()); //Añadimos a la sesión en usuario y contraseña
				sesion.setAttribute("password", users.get(tomcatUser).getPassword());
				try {
					/* Creamos una conexión POST a centro educativo /login */
					URL url = new URL(urlCE+"/login");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					con.setDoOutput(true);
					con.setRequestProperty("content-type", "application/json");
					
					//Le añadimos el cuerpo al mensaje mediante un JSON (En este caso se ha realizado el json de forma literal)
					
					DataOutputStream wr = new DataOutputStream (
							con.getOutputStream());
		            wr.writeBytes("{\"dni\":\""+ sesion.getAttribute("dni") +"\",\"password\":\""+sesion.getAttribute("password")+"\"}");
		            wr.close();
		            
		            // Obtenemos las cookies que nos ha devuelto el centro educativo
		            List<String> cookies = con.getHeaderFields().get("Set-Cookie"); 		            
		            
		            /* Leemos el mensaje que hemos obtenido del centro educativo, deberia ser la KEY */
		            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response2 = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response2.append(inputLine);
					}
					
					in.close();
					String key = response2.toString();
					
					/* Añadimos tanto la KEY como la COOKIE a la sesión actual*/
					sesion.setAttribute("key", key);
					sesion.setAttribute("cookies", cookies.get(0));
		            
		            con.disconnect();
				} catch (Exception e) {
					
					/* En  caso de que falle, invalidamos la sesión y redirigimos a la pagina de error correspondiente*/
					sesion.invalidate();
					response.sendRedirect(request.getContextPath() + "/error_centro_educativo.html");
					return;
				}	
			} else {
				response.sendRedirect(request.getContextPath() + "/error_autenticacion_tomcat.html");
			}
		}
		
		/* Comprobamos cual es el rol del usuario autenticado en tomcat y lo redirigimos al servlet correspondiente */
		if (request.isUserInRole("rolpro")) {
			//nothing yet
		} else if (request.isUserInRole("rolalu")) {
			response.sendRedirect(request.getContextPath() + "/alumno");
		}
	}

}
