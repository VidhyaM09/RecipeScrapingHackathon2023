package com.recipesforhypertensioncondition;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.recipes.utils.LoggerLoad;
import com.recipes.utils.PropertyFileReader;
import com.recipesfordiabetescondition.GetSetRecipes;

import com.seleniumbase.BaseClass;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.util.List;

public class Hypertension extends BaseClass {
	static String browserName;
	private static BaseClass seleniumBase;

	public void recipeScrapingForHypertension() throws Throwable {

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
		String recipeName = null;

		browserName = PropertyFileReader.getGlobalValue("browserName");
		// Initialize the driver
		seleniumBase = new BaseClass();
		seleniumBase.setDriver(browserName);
		getUrl("baseUrl");
		verifyTitle("Indian Recipes | Indian Vegetarian Recipes | Top Indian Veg Dishes");
		System.out.println("***************");

		WebElement recipies = driver.findElement(By.xpath("//div[contains(text(),'RECIPES')]"));

		recipies.click();

		driver.findElement(By.partialLinkText("High Fiber")).click();
		page = driver.findElements(By.xpath("//a[@class='respglink']"));
		int numberOfPages = page.size();
		for (int i = 1; i <= numberOfPages; i++) {
			driver.navigate().to("https://www.tarladalal.com/recipes-for-high-fiber-819?pageindex=" + i);

			// To Get Recipe Cards
			recipeCards = driver.findElements(By.xpath("//article[@class='rcc_recipecard']"));
			int noOfRecipes = recipeCards.size();
			System.out.println("Page No: " + i + ", NumberOfrecipes = " + noOfRecipes);
			System.out.println("*************************");

			for (int j = 0; j < noOfRecipes; j++) {

				driver.navigate().to("https://www.tarladalal.com/recipes-for-high-fiber-819?pageindex=" + i);
				LoggerLoad
						.info("Recipes Starting from " + j + " out of " + noOfRecipes + " Recipes from Page No. " + i);
				System.out.println("**************************");

				// Getting Recipe Name
				recipeNames = driver.findElements(By.xpath("//span[@class='rcc_recipename']"));
				gs.setRecipeName(recipeNames.get(j).getText());
				System.out.println("RecipeName = " + gs.getRecipeName());
				recipeName = gs.getRecipeName();

				System.out.println("****************************");
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

				String RecipeId = gs.getRecipeId();

				// Getting Recipe Details
				System.out.println("***********************");
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
				String RecipeCategory = gs.getRecipeCategory();

				// Getting Ingredient list
				System.out.println("*********************");
				ingredientList = driver.findElement(By.id("rcpinglist")).getText();
				System.out.println("IngredientList = " + ingredientList);

				// Getting Preparation Time
				System.out.println("*********************");
				try {
					preparationTime = driver.findElement(By.xpath("//p/time[@itemprop = 'prepTime']")).getText();
					System.out.println("PreparationTime = " + preparationTime);

				} catch (Exception e) {
					preparationTime = "Preparation time not found";
				}
				String prepTime = preparationTime;
				// Getting cooking time
				System.out.println("*********************");
				try {

					cookingTime = driver.findElement(By.xpath("//p/time[@itemprop = 'cookTime']")).getText();
					System.out.println("CookingTime = " + cookingTime);

				} catch (Exception e) {
					cookingTime = "cooking time not found";
				}
				String cookTime = preparationTime;

				// Getting Preparation Method
				System.out.println("*********************");
				preparationMethod = driver.findElement(By.id("ctl00_cntrightpanel_pnlRcpMethod")).getText();
				System.out.println("PreparationMethod = " + preparationMethod);
				String prepMethod = preparationMethod;

				// Getting Nutrient Value
				System.out.println("*********************");
				try {
					nutritionalValue = driver.findElement(By.id("rcpnutrients")).getText();
					System.out.println("NutritionalValue = " + nutritionalValue);

				} catch (Exception e) {
					nutritionalValue = "No nutritional value is found";

				}
				String nutrientValue = nutritionalValue;

				// Targetted Morboid Conditions
				System.out.println("*********************");

				// Use Tesseract OCR to extract text from the image
				ITesseract tesseract = new Tesseract();

				String text = tesseract.doOCR(
						new File(System.getProperty("user.dir") + "/src/test/resources/images/hypertensionRecipe.jpg"));

				// Output the extracted text
				System.out.println("Targetted Morboid Conditions = " + text);
				String targetCondition = text;

				// Getting Recipe Url
				try {
					recipeUrl = driver.getCurrentUrl();
					System.out.println("Recipe URL = " + recipeUrl);
					// tempUrl = driver.getCurrentUrl();
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				} catch (Exception e) {
					System.out.println("No recipe url is found");
				}

				String url = driver.getCurrentUrl();

				String[][] ExcludeCode = HypertensionExcelReader.getData("Sheet1");
				int Exsize = ExcludeCode.length;
				System.out.println("Exsize=" + Exsize);
				boolean isElimIngredExists = false;
				for (int k = 0; k < Exsize; k++) {

					int t = ingredientList.toLowerCase().indexOf(ExcludeCode[k][0].toLowerCase());
					if (t != -1) {
						System.out.println("Elim Matched " + ExcludeCode[k][0]);
						isElimIngredExists = true;
						break;
					}

				}

				String[][] AddCode = HypertensionExcelReader.getData("Sheet2");
				int Addsize = AddCode.length;
				System.out.println("Addsize=" + Addsize);
				boolean isAddIngredientExists = false;
				for (int k = 0; k < Addsize; k++) {

					int t = ingredientList.toLowerCase().indexOf(AddCode[k][0].toLowerCase());
					if (t != -1) {
						System.out.println("Add Matched " + AddCode[k][0]);
						isAddIngredientExists = true;
						break;
					}

				}

				String[][] allergyCode = HypertensionExcelReader.getData("Sheet3");
				int allergysize = allergyCode.length;
				System.out.println("allergysize=" + allergysize);
				boolean isAllergyIngredientExists = false;
				for (int k = 0; k < allergysize; k++) {
					int t = ingredientList.toLowerCase().indexOf(allergyCode[k][0].toLowerCase());
					if (t != -1) {
						System.out.println("Allergy Matched" + allergyCode[k][0]);
						isAllergyIngredientExists = true;
						break;
					}

				}

				String[][] nutsallergyCode = HypertensionExcelReader.getData("Sheet4");
				int nutsallergysize = nutsallergyCode.length;
				System.out.println("nutsallergysize=" + nutsallergysize);
				boolean isnutsAllergyIngredientExists = false;
				for (int k = 0; k < nutsallergysize; k++) {

					int t = ingredientList.toLowerCase().indexOf(nutsallergyCode[k][0].toLowerCase());
					if (t != -1) {
						System.out.println("Nuts Allergy Matched" + nutsallergyCode[k][0]);
						isnutsAllergyIngredientExists = true;
						break;
					}

				}
				System.out.println("elimexists - " + !isElimIngredExists);
				System.out.println("addexists - " + isAddIngredientExists);
				System.out.println("allergyexists - " + isAllergyIngredientExists);
				System.out.println("nutsallergyexists - " + isnutsAllergyIngredientExists);

				String projectDir = System.getProperty("user.dir");
				String path1 = projectDir + "/src/test/resources/ScrapedRecipesForHypertension.xlsx";
				String path2 = projectDir + "/src/test/resources/AllergyRecipes.xlsx";
				String path3 = projectDir + "/src/test/resources/NutsAllergyRecipes.xlsx";

				int Excelcolumn1 = HypertensionExcelReader.getLastColumn("Sheet1", path1);
				System.out.println("Starting column=" + Excelcolumn1);

				int Excelcolumn2 = HypertensionExcelReader.getLastColumn("AllergyToAdd", path2);
				System.out.println("Starting column=" + Excelcolumn2);

				int Excelcolumn3 = HypertensionExcelReader.getLastColumn("NutsAllergyToAdd", path3);
				System.out.println("Starting column=" + Excelcolumn3);

				if ((!isElimIngredExists) && (isAddIngredientExists)) {
					System.out.println("Added to excel " + recipeName);
					HypertensionExcelWriter excelWriter = new HypertensionExcelWriter();
					excelWriter.WriteData("Sheet1", 0, Excelcolumn1++, RecipeId, recipeName, RecipeCategory,
							ingredientList, prepTime, cookingTime, prepMethod, nutrientValue, targetCondition, url,
							path1);
				}

				if ((!isElimIngredExists) && (isAllergyIngredientExists)) {
					System.out.println("Added to excel " + recipeName);
					HypertensionExcelWriter excelWriter = new HypertensionExcelWriter();
					excelWriter.WriteData("AllergyToAdd", 0, Excelcolumn2++, RecipeId, recipeName, RecipeCategory,
							ingredientList, prepTime, cookingTime, prepMethod, nutrientValue, targetCondition, url,
							path2);
				}

				if ((!isElimIngredExists) && (isnutsAllergyIngredientExists)) {
					System.out.println("Added to excel " + recipeName);
					HypertensionExcelWriter excelWriter = new HypertensionExcelWriter();
					excelWriter.WriteData("NutsAllergyToAdd", 0, Excelcolumn3++, RecipeId, recipeName, RecipeCategory,
							ingredientList, prepTime, cookingTime, prepMethod, nutrientValue, targetCondition, url,
							path3);
				}
				driver.navigate().back();
			}
		}
		driver.quit();
	}

	public static void main(String[] args) throws Throwable {

		Hypertension recipe = new Hypertension();

		recipe.recipeScrapingForHypertension();

	}
}
