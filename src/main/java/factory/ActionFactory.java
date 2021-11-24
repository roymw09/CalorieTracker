package factory;

import action.Action;
import action.AddFoodAction;
import action.DeleteFoodAction;
import action.LoginAction;
import action.LogoutAction;
import action.RegisterAction;
import action.ResetPasswordAction;
import action.SearchFoodAction;

public class ActionFactory {
	
	public Action getAction(String actionType) {
		
		if (actionType.equalsIgnoreCase("AddFood")) {
			return new AddFoodAction();
		}
		else if (actionType.equalsIgnoreCase("DeleteFood")) {
			return new DeleteFoodAction();
		}
		else if (actionType.equalsIgnoreCase("SearchFood")) {
			return new SearchFoodAction();
		}
		else if (actionType.equalsIgnoreCase("Login")) {
			return new LoginAction();
		}
		else if (actionType.equalsIgnoreCase("Logout")) {
			return new LogoutAction();
		}
		else if (actionType.equalsIgnoreCase("Register")) {
			return new RegisterAction();
		}
		else if (actionType.equalsIgnoreCase("ResetPassword")) {
			return new ResetPasswordAction();
		}
		return null;
	}
}
