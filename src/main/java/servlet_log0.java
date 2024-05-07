




import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//creamos la clase que hereda de HttpServlet, de esta manera puede resolver peticiones HTTP
public class servlet_log0 extends HttpServlet {

	//identificador para la serialización
	private static final long serialVersionUID = 1L;

	//creamos la cabecera aparte
	String preTituloHTML5 = "<!DOCTYPE html>\n<html>\n<head>\n<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />";

    //constructor de la clase, que hereda directamente de la superclase
    public servlet_log0() {
        super();
    }
	//esto es lo que ejecuta cuando recibe petición GET
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//responde a la petición con "Served at:" seguido de la ruta de la solicitud
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	//esto es lo que se ejecuta cuando recibe petición POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//creamos un Writer de respuesta al cliente al que llamamos "out"
		PrintWriter out = response.getWriter();
		//establecemos el tipo de contenido como html
		response.setContentType("text/html");

		//creamos el string del resultado
		//contiene dirección de host remoto, fecha y hora actual, método de solicitud (será POST porque está en doPost) y la URL de la solicitud
		String result = request.getRemoteHost() + " - " +LocalDateTime.now() + " - " + request.getMethod() + " " + request.getRequestURL();

		//Lo dejo comentado porque no se si se ha llegado a llamar
		//out.println(preTituloHTML5)
		//creamos el documento HTML en el que mostramos el resultado
		out.println("<html><head><title>Log 0</title></head><body>");
		out.println("<span>" + result + "</span>");
		out.println("</body></html>");
			
		
	}

}
