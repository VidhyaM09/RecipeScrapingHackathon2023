package com.recipesforpcoscondition;

import org.openqa.selenium.WebElement;

public class GetSetRecipes {

	private String recipeName;

	private String recipeId;
	
	private String recipeCategory;
	
	public String getRecipeCategory() {
		return recipeCategory;
	}

	public void setRecipeCategory(String string) {
		this.recipeCategory = string;
	}

	private WebElement insideRecipeDetails;

	public WebElement getInsideRecipeDetails() {
		return insideRecipeDetails;
	}

	public WebElement setInsideRecipeDetails(WebElement webElement) {
		return this.insideRecipeDetails = webElement;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	
	
}
