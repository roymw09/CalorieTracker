package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.ApplicationDao;
import model.User;
import util.SendEmail;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ApplicationDao applicationDao = new ApplicationDao();
	private String errorMessage;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loadRegisterPage(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		processRequest(request, response);
	}
	
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	if (request.getParameter("signUpButton") != null) {
    		signup(request, response);
    	}
    	else if (request.getParameter("verifyButton") != null) {
    		verifyAuthenticationCode(request, response);
    	}
    }
	
	private boolean verifyRegistration(User user) {
		if (!applicationDao.checkRegistrationEmail(user.getEmail())) {
			errorMessage = "Email is invalid";
			return false;
		}
		else if (!applicationDao.checkRegistrationUsername(user.getUsername())) {
			errorMessage = "Username is invalid";
			return false;
		}
		else if (!applicationDao.checkRegistrationPassword(user.getPassword())) {
			errorMessage = "Password is invalid";
			return false;
		}
		else if (applicationDao.checkIfAccountExists(user)) {
			errorMessage = "An account with the email or username you entered already exists";
			return false;
		}
		return true;
	}
	
    private void sendVerificationEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String subject = "Email Verification";
    	String message = "Please enter the code to verify your account ";
    	SendEmail verifyEmail = new SendEmail(subject, message);
    	String email = request.getParameter("email");
    	String code = verifyEmail.generateRandomCode();
    	
    	boolean verificationEmailSent = verifyEmail.sendEmail(email, code);
    	if (verificationEmailSent) {
    		HttpSession session = request.getSession();
    		session.setAttribute("authCode", code);
    		loadVerifyPage(request, response);
    		//response.sendRedirect("verify");
    	} else {
    		loadRegisterPage(request, response);
    	}
    }
    
    private void verifyAuthenticationCode(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("user");
    	String verificationCode = session.getAttribute("authCode").toString();
    	String codeEnteredByUser = request.getParameter("authCode");
    	String verificationMessage = "You're account has been verified! You can now log in";
    	
    	if (codeEnteredByUser.equals(verificationCode)) {
    		applicationDao.insert(user);
    		session.setAttribute("verificationMessage", verificationMessage);
    		response.sendRedirect("login");
    	} else {
    		loadVerifyPage(request, response);
    	}
    }
    
    private void signup(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User(email, username, password);
		
		if (verifyRegistration(user)) {
			request.getSession().setAttribute("user", user);
			sendVerificationEmail(request, response);
		} else {
			request.setAttribute("registerErrorMessage", errorMessage);
			loadRegisterPage(request, response);
		}
    }
    
    private void loadRegisterPage(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	RequestDispatcher view = request.getRequestDispatcher("html/register_page.jsp");
    	view.forward(request, response);
    }
    
    private void loadVerifyPage(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	RequestDispatcher view = request.getRequestDispatcher("html/verify_email_page.jsp");
    	view.forward(request, response);
    }
}