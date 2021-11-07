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
import util.SendEmail;

@WebServlet("/forgot")
public class ForgotPasswordServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String userEmail;
    private String resetCode;
    
    ApplicationDao applicationDao = new ApplicationDao();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	loadForgotPasswordPage(request, response);
	}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	if (request.getParameter("resetPassword") != null) {
    		sendForgotPasswordEmail(request, response);
    	}
    	else if (request.getParameter("submitResetCode") != null) {
    		verifyResetCode(request, response);
    	}
    	else if (request.getParameter("changePassword") != null) {
    		changePassword(request, response);
    	}
    }
    
	private void sendForgotPasswordEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String subject = "Password Reset";
    	String message = "Please enter the code to change your password: ";
    	SendEmail resetEmail = new SendEmail(subject, message);
    	resetCode = resetEmail.generateRandomCode();
    	userEmail = request.getParameter("userEmail");
    	
    	boolean resetEmailSent = resetEmail.sendEmail(userEmail, resetCode);
    	if (resetEmailSent) {
    		loadEnterResetCodePage(request, response);
    	} else {
    		loadForgotPasswordPage(request, response);
    	}
    }
    
    private void verifyResetCode(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String userEnteredCode = request.getParameter("userEnteredResetCode");
    	
    	if (userEnteredCode.equals(resetCode)) {
    		loadChangePasswordPage(request, response);
    	} else {
    		loadEnterResetCodePage(request, response);
    	}
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException{
    	String message = "Password has been changed";
    	String newPassword = request.getParameter("newPassword");
    	String confirmNewPassword = request.getParameter("confirmNewPassword");
    	boolean passwordIsValid = applicationDao.checkRegistrationPassword(newPassword);
    	// TODO - Add error message if password is invalid
    	if (passwordIsValid && newPassword.equals(confirmNewPassword)) {
    		applicationDao.updatePassword(userEmail, newPassword);
    		HttpSession session = request.getSession();
    		session.setAttribute("verificationMessage", message);
    		response.sendRedirect("login");
    	} else {
    		loadChangePasswordPage(request, response);
    	}
	}
    
	private void loadForgotPasswordPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	RequestDispatcher view = request.getRequestDispatcher("html/forgot_password_page.jsp");
    	view.forward(request, response);
    }
    
    private void loadEnterResetCodePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	RequestDispatcher view = request.getRequestDispatcher("html/reset_code_page.jsp");
    	view.forward(request, response);
    }
    
    private void loadChangePasswordPage(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException{
    	RequestDispatcher view = request.getRequestDispatcher("html/change_password_page.jsp");
    	view.forward(request, response);
   	}
}
