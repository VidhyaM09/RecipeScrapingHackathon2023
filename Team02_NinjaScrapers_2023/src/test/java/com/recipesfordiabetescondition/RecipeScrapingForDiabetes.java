package com.recipesfordiabetescondition;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.recipes.utils.PropertyFileReader;
import com.seleniumbase.BaseClass;
import org.openqa.selenium.TakesScreenshot;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class RecipeScrapingForDiabetes extends BaseClass {

	static String browserName;
	private static BaseClass seleniumBase;

	@BeforeTest
	public static void before() throws Throwable {
		// Get browser Type from property file
		browserName = PropertyFileReader.getGlobalValue("browserName");
		// Initialize the driver
		seleniumBase = new BaseClass();
		seleniumBase.setDriver(browserName);
	}

	GetSetRecipes gs = new GetSetRecipes();
	List<WebElement> recipeCards = null;
	List<WebElement> page = null;
	List<WebElement> recipeNames = null;
	List<WebElement> recipeIds = null;
	List<WebElement> recipeCategory=null;

	@Test(priority = 1)
	public void displayRecipes() throws Throwable {

		getUrl("baseUrl");
		verifyTitle("Indian Recipes | Indian Vegetarian Recipes | Top Indian Veg Dishes");

		driver.findElement(By.partialLinkText("RECIPES")).click();
		driver.findElement(By.partialLinkText("Diabetic recipes")).click();

		// pagination
		page = driver.findElements(By.xpath("//a[@class='respglink']"));
		int numberOfPages = 24;
		for (int i = 1; i <= numberOfPages; i++) {
			driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=" + i);

			// To Get Recipe Cards
			recipeCards = driver.findElements(By.xpath("//article[@class='rcc_recipecard']"));
			int noOfRecipes = recipeCards.size();
			System.out.println("Page No: " + i + ", NumberOfrecipes = " + noOfRecipes);

			for (int j = 0; j < noOfRecipes; j++) {

				driver.navigate()
						.to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=" + i);
				System.out.println(
						"Recipes Starting from " + j + " out of " + noOfRecipes + " Recipes from Page No. " + i);

				// Getting Recipe Name
				recipeNames = driver.findElements(By.xpath("//span[@class='rcc_recipename']"));
				gs.setRecipeName(recipeNames.get(j).getText());
				System.out.println("RecipeName = " + gs.getRecipeName());

				// Getting Recipe ID
				try {
					recipeIds = driver.findElements(By.xpath("//div[@class='rcc_rcpno']/span"));
					String id = recipeIds.get(j).getText();
					String[] splitNewline = id.split("\n");
					String recipeId = splitNewline[0];
					String[] splitHash = recipeId.split("#");
					String finalId = splitHash[1];
					gs.setRecipeId(finalId);
					System.out.println("RecipeId = " + gs.getRecipeId());
				} catch (Exception e) {
					Assert.fail("Error while getting receipe id");
				}
				
				
				
				

			}

		}
	}
}
