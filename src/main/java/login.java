import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.*;

/**
 * Servlet implementation class login
 */
public class login extends HttpServlet {
	private String[] users = new String[] {"12345678W"};
	private static final long serialVersionUID = 1L;
	private String urlCE = "http://DEW-jrevvil-2324.dsicv.upv.es:9090/CentroEducativo";
	private static final String preHTML5 = "<!DOCTYPE html>\n<html lang=\"es-es\">\n" +
			"<head>\n<meta charset=\"utf-8\" />\n" +
			"<title>Login?</title>\n</head>\n<body>\n" +
			"<h1>Login?</h1>\n";

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
		PrintWriter out = response.getWriter();
		out.println(preHTML5);
		HttpSession sesion = request.getSession();
		String tomcatUser = request.getRemoteUser();
		if(sesion.getAttribute("key") == null) {
			if (tomcatUser != null) {
				sesion.setAttribute("dni", tomcatUser);
				sesion.setAttribute("password", "123456");
				try {
					
					URL url = new URL(urlCE+"/login");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					
					
					con.setRequestMethod("POST");
					con.setDoOutput(true);
					con.setRequestProperty("content-type", "application/json");
					
					DataOutputStream wr = new DataOutputStream (
							con.getOutputStream());
		            wr.writeBytes("{\"dni\":\""+ tomcatUser +"\",\"password\":\""+"123456"+"\"}");
		            wr.close();
		            
		            List<String> cookies = con.getHeaderFields().get("Set-Cookie"); 
		            out.println ("<p> cookies: "+cookies+"</p>");
		            
		            
		            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response2 = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response2.append(inputLine);
					}
					
					in.close();
					String key = response2.toString();
					out.println ("<p> JSON Response: "+key+"</p>");
					
					sesion.setAttribute("key", key);
					sesion.setAttribute("cookies", cookies);
		            
		            con.disconnect();
		            
					
				} catch (Exception e) {
					sesion.invalidate();
					request.logout();
					response.sendError(500, "Hubo problemas al recuperar la información." + e);
					
					return;
				}	
			}
		
		} else {
			out.println ("<p> sesion.getAttribute(\"dni\"): "+sesion.getAttribute("dni")+"</p>");
			out.println ("<p> sesion.getAttribute(\"key\"): "+sesion.getAttribute("key")+"</p>");
			out.println ("<p> sesion.getAttribute(\"cookies\"): "+sesion.getAttribute("cookies")+"</p>");
			out.println ("<p> sesion.getAttribute(\"password\"): "+sesion.getAttribute("password")+"</p>");
		}
		//out.println ("<p> sesion.isNew(): "+sesion.isNew()+"</p>");
		out.println ("<p> request.getRemoteUser(): "+request.getRemoteUser()+"</p>");
		out.println ("<p> request.isUserInRole(\"mirol\"): "+request.isUserInRole("mirol")+"</p>");
		out.println ("<p> request.getUserPrincipal(): "+request.getUserPrincipal()+"</p>");
		
		if (request.isUserInRole("rolpro")) {
			//nothing yet
		} else if (request.isUserInRole("rolalu")) {
			response.sendRedirect(request.getContextPath() + "/vista_alumno");
		}
		
		
		
		
		/*try {
			
			URL url = new URL(urlCE+"/login");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("content-type", "application/json");
			
			DataOutputStream wr = new DataOutputStream (
					con.getOutputStream());
            wr.writeBytes("{\"dni\":\""+ dni +"\",\"password\":\""+password+"\"}");
            wr.close();
            List<String> cookies = con.getHeaderFields().get("Set-Cookie"); 
            out.println ("<p> cookies: "+cookies+"</p>");
            
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response2 = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response2.append(inputLine);
			}
			
			in.close();
			String key = response2.toString();
			out.println ("<p> JSON Response: "+key+"</p>");
            
            con.disconnect();
            
            
			
			
			
			
		} catch (Exception e) {
			response.sendError(500, "Hubo problemas al recuperar la información." + e);
			return;
		}*/

		/*
		try {
			URL url = new URL(urlCE+"/login");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("content-type", "application/json");
			
			DataOutputStream wr = new DataOutputStream (
            con.getOutputStream());
            wr.writeBytes("{\"dni\":\""+ dni +"\",\"password\":\""+password+"\"}");
            wr.close();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response2 = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response2.append(inputLine);
			}
			
			in.close();
			String key = response2.toString();
			out.println ("<p> JSON Response: "+key+"</p>");
            
            con.disconnect();
            
            
            
            try {
            	
            	URL url_asignaturas = new URL(urlCE+"/asignaturas?key="+key);
    			HttpURLConnection con2 = (HttpURLConnection) url_asignaturas.openConnection();
    			
    			con2.setRequestMethod("GET");
    			con2.setDoOutput(true);
    			con2.setDoInput(true);
    			
    			if (con2.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
    				BufferedReader in2 = new BufferedReader(new InputStreamReader(con.getInputStream()));
    				String inputLine2;
    				StringBuffer responseGET = new StringBuffer();

    				while ((inputLine2 = in2.readLine()) != null) {
    					responseGET.append(inputLine);
    				}
    				in2.close();

    				// print result
    				out.println("<p>PARRAFO¿</p>");
    				out.println ("<p> JSON Response: "+response.toString()+"</p>");
    			
    			} else {
    				System.out.println("GET request did not work.");
    			}
    			
    			
                con2.disconnect();
            	
            	
            	
            	
            } catch(Exception e) {
    			response.sendError(500, "Hubo problemas al recuperar la información." + e);
    			return;
    		}
            

		} catch(Exception e) {
			response.sendError(500, "Hubo problemas al recuperar la información." + e);
			return;
		}*/
			
		out.println("</body></html>");
	}

}
