package model;

import java.sql.Timestamp;

public class FoodModel {
	
	private String foodName;
	private String calories;
	private String protein;
	private String fat;
	private String carbohydrates;
	private String servingSizeUnit;
	private String servingSize;
	private Timestamp insertDate;
	private int food_id;
	
	public FoodModel() {
		
	}
	
	public FoodModel(String foodName, String cals, String fat, String protein, String carbs) {
		this.foodName = foodName;
		this.calories = cals;
		this.fat = fat;
		this.protein = protein;
		this.carbohydrates = carbs;
	}
	
	public String getFoodName() {
		return foodName;
	}
	
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	
	public String getCalories() {
		return calories;
	}
	
	public void setCalories(String calories) {
		this.calories = calories;
	}
	
	public String getFat() {
		return fat;
	}
	
	public void setFat(String fat) {
		this.fat = fat;
	}
	
	public String getCarbohydrates() {
		return carbohydrates;
	}
	
	public void setCarbohydrates(String carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public String getProtein() {
		return protein;
	}

	public void setProtein(String protein) {
		this.protein = protein;
	}

	public String getServingSizeUnit() {
		return servingSizeUnit;
	}

	public void setServingSizeUnit(String servingSizeUnit) {
		this.servingSizeUnit = servingSizeUnit;
	}

	public String getServingSize() {
		return servingSize;
	}

	public void setServingSize(String servingSize) {
		this.servingSize = servingSize;
	}

	public Timestamp getInsertDate() {
		return insertDate;
	}
	
	public void setInsertDate(Timestamp timestamp) {
		this.insertDate = timestamp;
	}

	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}
}
