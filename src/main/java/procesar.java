import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class procesar
 */
public class procesar extends HttpServlet {
  private static final long serialVersionUID = 1L;

  String preTituloHTML5 = "<!DOCTYPE html>\n<html>\n<head>\n<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />";
  
  public procesar() {
    super();
    // TODO Auto-generated constructor stub
  }
    
  private static boolean isNumeric(String str){ // En sustitución de try/catch
    return str != null && str.matches("[0-9.]+");
  }
    
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    // No puede invocarse por GET
    response.sendError(405); // Method not allowed
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    int dato=0; // Compilador se queja al usar dato sin asegurar inicialización
    PrintWriter out = response.getWriter();
    String t=request.getParameter("dato");
    if (t == null) response.sendError(400); //  Error "Bad Request" si falta dato
    try {dato = Integer.parseInt(t);}
    catch (NumberFormatException ex){
      response.sendError(400); // Error "Bad Request" si dato no es entero
    }
    if (dato%2==0) { // Par
      response.setContentType("text/html");
      out.print(preTituloHTML5);
      out.print("<title>Procesar</title></head>\n<body>\n"+
            "<h1>Procesar</h1><p>El valor "+ t +" es par.</p>"+
    		"</body></html>");
    }
    else { // Impar
      response.setContentType("text/plain");
      out.print("Procesar\nEl valor "+ t +" es impar.");
    }
  }
}
