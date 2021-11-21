package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
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
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processGetRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		processPostRequest(request, response);
	}
	
	private void searchFood(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String foodEnteredByUser = request.getParameter("foodSearch");
		String apiKey = "&pageSize=2&api_key=vd1grKROnzGA7p9T0rI1GZxgFV6Izs2htyjLALIj"; // TODO - REMOVE THIS FROM CODE
		String url = "https://api.nal.usda.gov/fdc/v1/foods/search?query=" + foodEnteredByUser + apiKey;
		try {
			JSONObject jsonFoodData = FoodDao.readJsonFromUrl(url);
			ArrayList<FoodModel> foodList = foodDao.setFoodModelArray(jsonFoodData);
			request.setAttribute("searchedListOfFood", foodList);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void addFoodToDatabase(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		ApplicationDao appDao = new ApplicationDao();
		String username = request.getSession().getAttribute("loggedInUser").toString();
		
		// user id is a fk in food table
		int userId = appDao.getUserId(username);
		String foodName = request.getParameter("foodName");
		String foodCal = request.getParameter("foodCalories");
		String foodFat = request.getParameter("foodFat");
		String foodProtein = request.getParameter("foodProtein");
		String foodCarbs = request.getParameter("foodCarbs");
		
		FoodModel foodModel = new FoodModel(foodName, foodCal, foodFat, foodProtein, foodCarbs);
		foodDao.insert(foodModel, userId);
		
	}
	
	private void deleteFoodFromDatabase(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		int food_id = Integer.parseInt(request.getParameter("foodId"));
		foodDao.delete(food_id);
	}
	
	private void displayDailyFoodList(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException  {
		ApplicationDao appDao = new ApplicationDao();
		String username = request.getSession().getAttribute("loggedInUser").toString();
		int user_id = appDao.getUserId(username);
		
		ArrayList<FoodModel> foodList = foodDao.getCurrentFoodList(user_id);
		double totalCalories = 0;
		for (FoodModel food : foodList) {
			double cals = Double.parseDouble(food.getCalories());
			totalCalories += cals;
		}
		request.getSession().setAttribute("totalDailyCalories", totalCalories);
		request.getSession().setAttribute("userListOfFood", foodList);
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
			searchFood(request, response);
			processGetRequest(request, response);
		}
		else if (request.getParameter("addToDailyList") != null) { 
			addFoodToDatabase(request, response);
			processGetRequest(request, response);
		}
		else if (request.getParameter("deleteFromDailyList") != null) {
			deleteFoodFromDatabase(request, response);
			processGetRequest(request, response);
		}
	}
}