package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ApplicationDao;
import dao.FoodDao;
import factory.ActionFactory;
import model.FoodModel;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FoodDao foodDao = new FoodDao();
	ActionFactory actionFactory = new ActionFactory();
	
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
			ActionFactory actionFactory = new ActionFactory();
			actionFactory.getAction("SearchFood").execute(request, response);
			processGetRequest(request, response);
		}
		else if (request.getParameter("addToDailyList") != null) { 
			ActionFactory actionFactory = new ActionFactory();
			actionFactory.getAction("AddFood").execute(request, response);
			processGetRequest(request, response);
		}
		else if (request.getParameter("deleteFromDailyList") != null) {
			ActionFactory actionFactory = new ActionFactory();
			actionFactory.getAction("DeleteFood").execute(request, response);
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