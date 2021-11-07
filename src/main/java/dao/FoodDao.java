package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import model.FoodModel;

public class FoodDao {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, ParseException {
		InputStream is = new URL(url).openStream();
	    try {
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    	String jsonText = readAll(rd);
	    	JSONParser jsonParser = new JSONParser();
	    	JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonText);
	    	return jsonObject;
	    } finally {
	    	is.close();
	    }
	}
	
	public ArrayList<FoodModel> setFoodModelArray(JSONObject jsonData) {
		ArrayList<FoodModel> foodList = new ArrayList<FoodModel>();
		JSONArray jsonArray = (JSONArray) jsonData.get("foods");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			FoodModel foodModel = new FoodModel();
			
			// Set food name, serving size, and serving size units
			foodModel.setFoodName(obj.get("description").toString());
			if (obj.get("servingSize") != null && obj.get("servingSizeUnit") != null) {
				foodModel.setServingSize(obj.get("servingSize").toString());
				foodModel.setServingSizeUnit(obj.get("servingSizeUnit").toString());
			}
			
			
			// Get calories and macronutrient information
			JSONArray arr = (JSONArray) obj.get("foodNutrients");
			for (int j = 0; j < arr.size(); j++) {
				JSONObject jOBJ = (JSONObject) arr.get(j);
				// protein
				if (jOBJ.get("nutrientName").equals("Protein")) {
					foodModel.setProtein(jOBJ.get("value").toString());
				}
				// carbs
				else if (jOBJ.get("nutrientName").equals("Carbohydrate, by difference")) {
					foodModel.setCarbohydrates(jOBJ.get("value").toString());
				}
				// calories
				else if (jOBJ.get("nutrientName").equals("Energy")) {
					foodModel.setCalories(jOBJ.get("value").toString());
				}
				// fat
				else if (jOBJ.get("nutrientName").equals("Total lipid (fat)")) {
					foodModel.setFat(jOBJ.get("value").toString());
				}
			}
			foodList.add(foodModel); // add the foodmodels to the listS
		}
		return foodList;
	}
	
	public boolean insert(FoodModel foodModel, int user_id) {
		try {
			String sql = "INSERT INTO FOOD (user_id, foodName, calories, protein, fat, carbs) VALUES(?, ?, ?, ?, ?, ?);";
			Connection connection = DBConnection.getConnectionToDatabase();
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, user_id);
			stmt.setString(2, foodModel.getFoodName());
			stmt.setString(3, foodModel.getCalories());
			stmt.setString(4, foodModel.getProtein());
			stmt.setString(5, foodModel.getFat());
			stmt.setString(6, foodModel.getCarbohydrates());
			stmt.executeUpdate();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(int food_id) {
		try {
			Connection connection = DBConnection.getConnectionToDatabase();
			String sql = "DELETE FROM food WHERE id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, food_id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<FoodModel> getCurrentFoodList(int user_id) {
		ArrayList<FoodModel> foodList = new ArrayList<FoodModel>();
		try {
			Date date = Date.valueOf(LocalDate.now());
			Connection connection = DBConnection.getConnectionToDatabase();
			String sql = "SELECT food.id, foodName, calories, protein, fat, carbs, insertDate FROM food "
					+ "INNER JOIN users on food.user_id = users.id "
					+ "WHERE users.id = ? AND food.insertDate = ?;";
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, user_id);
			stmt.setDate(2, date);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				FoodModel foodModel = new FoodModel();
				foodModel.setFood_id(rs.getInt("id"));
				foodModel.setFoodName(rs.getString("foodName"));
				foodModel.setCalories(rs.getString("calories"));
				foodModel.setProtein(rs.getString("protein"));
				foodModel.setFat(rs.getString("fat"));
				foodModel.setCarbohydrates(rs.getString("carbs"));
				foodModel.setInsertDate(rs.getTimestamp("insertDate"));
				foodList.add(foodModel);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foodList;
	}
}