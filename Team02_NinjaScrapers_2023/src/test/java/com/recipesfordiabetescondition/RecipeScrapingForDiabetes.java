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
	List<WebElement> targetMorboidCondition = null;
	List<WebElement> recipeCategory = null;
	List<WebElement> recipeDetails = null;
	WebElement goInsideRecipe = null;
	String ingredientList = null;
	String preparationTime = null;
	String cookingTime = null;
	String preparationMethod = null;
	String nutritionalValue = null;
	String recipeUrl = null;

	@Test(priority = 1)
	public void displayRecipes() throws Throwable {

		getUrl("baseUrl");
		verifyTitle("Indian Recipes | Indian Vegetarian Recipes | Top Indian Veg Dishes");
		System.out.println();

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
			System.out.println();

			for (int j = 0; j < noOfRecipes; j++) {

				driver.navigate()
						.to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=" + i);
				System.out.println(
						"Recipes Starting from " + j + " out of " + noOfRecipes + " Recipes from Page No. " + i);
				System.out.println();
				// Getting Recipe Name
				recipeNames = driver.findElements(By.xpath("//span[@class='rcc_recipename']"));
				gs.setRecipeName(recipeNames.get(j).getText());
				System.out.println("RecipeName = " + gs.getRecipeName());

				System.out.println();
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

				// Getting Recipe Details
				System.out.println();
				recipeDetails = driver.findElements(By.xpath("//span[@class='rcc_recipename']"));
				goInsideRecipe = gs.setInsideRecipeDetails(recipeDetails.get(j));
				goInsideRecipe.click();

				// Getting Recipe Category
				System.out.println("Recipe/Food Category :");
				try {
					recipeCategory = driver.findElements(By.xpath("//a[@itemprop='recipeCategory']"));
					for (int p = 0; p < recipeCategory.size(); p++) {
						gs.setRecipeCategory(recipeCategory.get(p).getText());
						System.out.println(gs.getRecipeCategory());
					}

				} catch (Exception e) {
					System.out.println("Some alert pop up!");
				}

				// Getting Ingredient list
				System.out.println();
				ingredientList = driver.findElement(By.id("rcpinglist")).getText();
				System.out.println("IngredientList = " + ingredientList);

				// Getting Preparation Time
				System.out.println();
				try {
					preparationTime = driver.findElement(By.xpath("//p/time[@itemprop = 'prepTime']")).getText();
					System.out.println("PreparationTime = " + preparationTime);
				} catch (Exception e) {
					preparationTime = "Preparation time not found";
				}

				// Getting cooking time
				System.out.println();
				try {

					cookingTime = driver.findElement(By.xpath("//p/time[@itemprop = 'cookTime']")).getText();
					System.out.println("CookingTime = " + cookingTime);
				} catch (Exception e) {
					cookingTime = "cooking time not found";
				}

				// Getting Preparation Method
				System.out.println();
				preparationMethod = driver.findElement(By.id("ctl00_cntrightpanel_pnlRcpMethod")).getText();
				System.out.println("PreparationMethod = " + preparationMethod);

				// Getting Nutrient Value
				System.out.println();
				try {
					nutritionalValue = driver.findElement(By.id("rcpnutrients")).getText();
					System.out.println("NutritionalValue = " + nutritionalValue);

				} catch (Exception e) {
					nutritionalValue = "No nutritional value is found";

				}

				// Targetted Morboid Conditions
				System.out.println();
				targetMorboidCondition = driver
						.findElements(By.xpath("//div[@class='rcc_caticons']/img[@src='images/recipe/diabetic.gif']"));

				// Use Tesseract OCR to extract text from the image
				ITesseract tesseract = new Tesseract();

				String text = tesseract.doOCR(
						new File(System.getProperty("user.dir") + "/src/test/resources/images/diabeticRecipe.png"));

				// Output the extracted text
				System.out.println("Targetted Morboid Conditions = " + text);

				// Getting Recipe Url
				try {
					recipeUrl = driver.getCurrentUrl();
					System.out.println("Recipe URL = " + recipeUrl);
				} catch (Exception e) {
					System.out.println("No recipe url is found");
				}

			}

		}
	}
}
