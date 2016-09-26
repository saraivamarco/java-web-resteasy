package pt.sasha.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CORSFilter
 */
@WebFilter(
		urlPatterns = { "/upload" }, 
		initParams = { 
				@WebInitParam(name = "cors.allowed.headers", value = "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers"), 
				@WebInitParam(name = "cors.exposed.headers", value = "Access-Control-Allow-Origin,Access-Control-Allow-Credentials"), 
				@WebInitParam(name = "cors.support.credentials", value = "false"), 
				@WebInitParam(name = "cors.preflight.maxage", value = "10")
		})
public class CORSFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CORSFilter() {
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
		HttpServletRequest req = (HttpServletRequest) request;
	    System.out.println("Request request.getMethod()");
	
	    HttpServletResponse resp = (HttpServletResponse) response;
//	    resp.addHeader("Access-Control-Allow-Origin","*");
//	    resp.addHeader("Access-Control-Allow-Methods","GET,POST");
//	    resp.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
	    
	    resp.addHeader("Access-Control-Allow-Origin", "*");
	    resp.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	    resp.addHeader("Access-Control-Allow-Credentials", "true");
	    resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	    resp.addHeader("Access-Control-Max-Age", "1209600");
	    	
	    // Just ACCEPT and REPLY OK if OPTIONS
	    if ( req.getMethod().equals("OPTIONS") ) {
	        resp.setStatus(200);
	        return;
	    }
	    chain.doFilter(req, resp);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
