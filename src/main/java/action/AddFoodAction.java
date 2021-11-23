package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ApplicationDao;
import dao.FoodDao;
import model.FoodModel;

public class AddFoodAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addFoodToDatabase(request, response);
	}

	private void addFoodToDatabase(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {	
		FoodDao foodDao = new FoodDao();
		ApplicationDao appDao = new ApplicationDao();
		String username = request.getSession().getAttribute("loggedInUser").toString();
		
		// user id is a fk in food table
		long userId = appDao.getUserId(username);
		String foodName = request.getParameter("foodName");
		String foodCal = request.getParameter("foodCalories");
		String foodFat = request.getParameter("foodFat");
		String foodProtein = request.getParameter("foodProtein");
		String foodCarbs = request.getParameter("foodCarbs");
		
		FoodModel foodModel = new FoodModel(foodName, foodCal, foodFat, foodProtein, foodCarbs);
		foodDao.insert(foodModel, userId);
	}
}
