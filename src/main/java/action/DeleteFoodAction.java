package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.FoodDao;

public class DeleteFoodAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		deleteFoodFromDatabase(request, response);
	}

	private void deleteFoodFromDatabase(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		FoodDao foodDao = new FoodDao();
		long food_id = Long.parseLong(request.getParameter("foodId"));
		foodDao.delete(food_id);
	}
}
