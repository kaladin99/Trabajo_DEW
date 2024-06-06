

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class cerrar_sesion
 */
public class cerrar_sesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cerrar_sesion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession sesion = request.getSession();
		sesion.invalidate();
		/*
		if (sesion != null) {
	            // Invalidar la sesión actual
	            sesion.invalidate();
	        }
	        */
		 
		 /*
		 if (sesion != null) {
			    sesion.invalidate();
			    Cookie[] cookies = request.getCookies();
			    if (cookies != null) {
			        for (Cookie cookie : cookies) {
			            cookie.setValue("");
			            cookie.setPath("/");
			            cookie.setMaxAge(0);
			            //response.addCookie(cookie);
			        }
			    }
			}
			*/
		response.sendRedirect(request.getContextPath() + "/index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
