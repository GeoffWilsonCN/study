package com.f.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class Filter
 */
@WebFilter("/Filter")
public class ChaFilter implements javax.servlet.Filter {

    /**
     * Default constructor. 
     */
    public ChaFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see ChaFilter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ChaFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		ChaRequest chaRequest = new ChaRequest(req);
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
class ChaRequest extends HttpServletRequestWrapper {
	private HttpServletRequest request;
	public ChaRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
		// TODO Auto-generated constructor stub
	}
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if(value == null) {
			return null;
		}
		String method = super.getMethod();
		if("get".equalsIgnoreCase(method)){
			try {
				value = new String(value.getBytes("iso-8859-1"),"utf-8");
			} catch (Exception e) {
				// TODO: handle exception
				throw new RuntimeException(e);
			}
		}
		return value;
	}
}
	/**
	 * @see ChaFilter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
