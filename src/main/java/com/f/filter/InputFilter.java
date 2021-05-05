package com.f.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class InputFilter
 */
@WebFilter("/InputFilter")
public class InputFilter implements Filter {

    /**
     * Default constructor. 
     */
    public InputFilter() {
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
		HttpServletRequest req = (HttpServletRequest) request;
		PrintWriter out = response.getWriter();
		String numargs[] = {
				"id",
				"class_id",
				"price",
				"alcohol",
				"als",
				"ale",
				"prs",
				"pre"
			};
		String text[] = {
			"ID",	
			"���ID",
			"�۸�",
			"�ƾ���",
			"��Ͷ���",
			"��߶���",
			"��߼۸�",
			"��߼۸�"
		};
		for(int i = 0;i<numargs.length;i++) {
			if(req.getParameter(numargs[i]) == null || req.getParameter(numargs[i]).trim().isEmpty()) {
				
			} else
			if(checkInt(req.getParameter(numargs[i]))) {
				
			} else {
				out.println("<script language='javascript'>");
				out.println("alert('�����"+text[i]+"��ʽ����ȷ������������');");
				out.println("window.location.href='javascript:history.go(-1)';");
				out.println("</script>");
				out.flush();
				out.close();
				break;
			}
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	//�ж�����
	public boolean checkInt(String str) {
		if(str == null || str.trim().equals("")) {
			
		} else {
			Pattern pattern = Pattern.compile("[0-9]*");
			return pattern.matcher(str).matches();
		}
		return false;
		
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
