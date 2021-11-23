package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import action.Action;
import action.AddFoodAction;
import action.DeleteFoodAction;
import action.SearchFoodAction;
import dao.ApplicationDao;
import dao.FoodDao;
import model.FoodModel;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FoodDao foodDao = new FoodDao();
	Action action;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processGetRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		processPostRequest(request, response);
	}
	
	private void processGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException  {
		displayDailyFoodList(request, response);
		RequestDispatcher view = request.getRequestDispatcher("html/home_page.jsp");
		view.forward(request, response);
	}
	
	private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException  {
		if (request.getParameter("searchForFood") != null){
			action = new SearchFoodAction();
			action.execute(request, response);
			processGetRequest(request, response);
		}
		else if (request.getParameter("addToDailyList") != null) { 
			action = new AddFoodAction();
			action.execute(request, response);
			processGetRequest(request, response);
		}
		else if (request.getParameter("deleteFromDailyList") != null) {
			action = new DeleteFoodAction();
			action.execute(request, response);
			processGetRequest(request, response);
		}
	}
	
	private void displayDailyFoodList(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException  {
		ApplicationDao appDao = new ApplicationDao();
		String username = request.getSession().getAttribute("loggedInUser").toString();
		long user_id = appDao.getUserId(username);
		
		ArrayList<FoodModel> foodList = foodDao.getCurrentFoodList(user_id);
		double totalCalories = 0;
		for (FoodModel food : foodList) {
			double cals = Double.parseDouble(food.getCalories());
			totalCalories += cals;
		}
		request.getSession().setAttribute("totalDailyCalories", totalCalories);
		request.getSession().setAttribute("userListOfFood", foodList);
	}
}