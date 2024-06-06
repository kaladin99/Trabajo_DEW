

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class log_requests
 */
public class log_requests extends HttpFilter implements Filter {
	
	private FilterConfig filterConfig;
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public log_requests() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String result = request.getRemoteHost() + " - " +LocalDateTime.now() + " - " + httpRequest.getMethod() + " " + httpRequest.getRequestURL() + " Informacion formulario -->";
		Enumeration<String> names;
		
		try {
			 ServletContext context = getServletContext();
			
			 String BDFileName = context.getInitParameter("fichero-persistencia");
						
			 FileWriter fileWriter = new FileWriter(BDFileName,true);
			 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			 
			 names = request.getParameterNames();
			 String parametersResult = "";
			 while (names.hasMoreElements()) {
					String name = names.nextElement();
					parametersResult += " " + name + ": " + request.getParameter(name);
			 }
			
			 result += parametersResult + "\n";
			
			 bufferedWriter.write(result);
			 bufferedWriter.close();
			
		}catch(IOException e){
			
			
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = fConfig;
	}

	public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
