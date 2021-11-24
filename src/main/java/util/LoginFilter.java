package util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession(false);
		
		String loginURL = request.getContextPath() + "/login";
		String registerURL = request.getContextPath() + "/register";
		String verifyURL = request.getContextPath() + "/verify";
		String forgotURL = request.getContextPath() + "/forgot";
 		
		boolean loggedIn = session != null && session.getAttribute("loggedInUser") != null;
		boolean loginOrRegisterRequest = loginURL.equals(request.getRequestURI()) || registerURL.equals(request.getRequestURI())
					|| verifyURL.equals(request.getRequestURI()) || forgotURL.equals(request.getRequestURI());
		
		// CSS wont load unless you check for it in the request and forward it
		boolean checkForCSS = request.getRequestURI().endsWith(".css");
		if (checkForCSS) {
			chain.doFilter(request, response);
			return;
		}
		
		// filter requests based on login status
		if (!loggedIn && loginOrRegisterRequest) {
			chain.doFilter(request, response);
			return;
		} 
		else if (loggedIn && !loginOrRegisterRequest){
			chain.doFilter(request, response);
			return;
		}
		else if (loggedIn && loginOrRegisterRequest) {
			response.sendRedirect("home");
			return;
		}
		else {
			response.sendRedirect("login");
			return;
		}
	}
}