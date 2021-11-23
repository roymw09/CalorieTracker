package action;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import dao.FoodDao;
import model.FoodModel;

public class SearchFoodAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		searchFood(request, response);
	}
	
	private void searchFood(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		FoodDao foodDao = new FoodDao();
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
}
