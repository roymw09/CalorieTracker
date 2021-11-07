package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ApplicationDao;
import model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ApplicationDao applicationDao = new ApplicationDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loadLoginPage(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		loadHomePage(request, response);
	}
	
	
	private boolean verifyLogin(User user) {
		return applicationDao.verifyLogin(user);
	}
	
	// load home page or redirect back to the login page if the username or password entered are not valid
	private void loadHomePage(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String errorMessage = "Invalid username / password";
		User user = new User(null, username, password);
		if (verifyLogin(user)) {
			System.out.println("LOGIN SUCCESS");
			request.getSession().setAttribute("loggedInUser", username);
			response.sendRedirect("home");
		} else {
			request.setAttribute("loginErrorMessage", errorMessage);
			loadLoginPage(request, response);
		}
    }
	
	private void loadLoginPage(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("html/login_page.jsp");
    	view.forward(request, response);
	}
}