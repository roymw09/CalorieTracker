package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ApplicationDao;
import model.User;

public class LoginAction implements Action {

	ApplicationDao applicationDao = new ApplicationDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String errorMessage = "Invalid username / password";
		User user = new User(null, username, password);
		if (applicationDao.verifyLogin(user)) {
			System.out.println("LOGIN SUCCESS");
			request.getSession().setAttribute("loggedInUser", username);
			response.sendRedirect("home");
		} else {
			request.setAttribute("loginErrorMessage", errorMessage);
			RequestDispatcher view = request.getRequestDispatcher("html/login_page.jsp");
	    	view.forward(request, response);
		}
	}
}
