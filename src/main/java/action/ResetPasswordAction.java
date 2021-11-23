package action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ApplicationDao;
import util.SendEmail;

public class ResetPasswordAction implements Action {

	private String userEmail;
    private String resetCode;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	if (request.getParameter("resetPassword") != null) {
    		sendForgotPasswordEmail(request, response);
    		System.out.println(resetCode);
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
    	request.getSession().setAttribute("resetCode", resetCode);
    	request.getSession().setAttribute("email", userEmail);
    	boolean resetEmailSent = resetEmail.sendEmail(userEmail, resetCode);
    	if (resetEmailSent) {
    		loadEnterResetCodePage(request, response);
    	} else {
    		loadForgotPasswordPage(request, response);
    	}
    }
    
    private void verifyResetCode(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	resetCode = request.getSession().getAttribute("resetCode").toString();
    	String userEnteredCode = request.getParameter("userEnteredResetCode");
    	if (userEnteredCode.equals(resetCode)) {
    		loadChangePasswordPage(request, response);
    		request.getSession().setAttribute("resetCode", null);
    	} else {
    		loadEnterResetCodePage(request, response);
    	}
    }
    
    private void changePassword(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException{
    	ApplicationDao applicationDao = new ApplicationDao();
    	String message = "Password has been changed";
    	String newPassword = request.getParameter("newPassword");
    	String confirmNewPassword = request.getParameter("confirmNewPassword");
    	boolean passwordIsValid = applicationDao.checkRegistrationPassword(newPassword);
    	userEmail = request.getSession().getAttribute("email").toString();
    	// TODO - Add error message if password is invalid
    	if (passwordIsValid && newPassword.equals(confirmNewPassword)) {
    		applicationDao.updatePassword(userEmail, newPassword);
    		request.getSession().setAttribute("verificationMessage", message);
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