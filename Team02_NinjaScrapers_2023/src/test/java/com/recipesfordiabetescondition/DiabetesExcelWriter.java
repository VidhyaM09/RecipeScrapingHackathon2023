package com.recipesfordiabetescondition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiabetesExcelWriter {
	public XSSFRow row;
	public XSSFCell cell;

	public void WriteData(String sheetname, int rownum, int column, String RecipeId, String recipeName,
			String RecipeCategory, String ingredientList, String prepTime, String cookingTime, String prepMethod,
			String nutrientValue, String targetCondition, String url) throws IOException {

		String projectDir = System.getProperty("user.dir");
		String path = projectDir + "/src/test/resources/ScrapedRecipesForDiabetes.xlsx";
		File file = new File(path);

		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);

		XSSFSheet sheet = wb.getSheet(sheetname);

		row = sheet.getRow(rownum + 1);
		cell = row.createCell(column);
		cell.setCellValue(RecipeId);

		row = sheet.getRow(rownum + 2);
		cell = row.createCell(column);
		cell.setCellValue(recipeName);

		row = sheet.getRow(rownum + 3);
		cell = row.createCell(column);
		cell.setCellValue(RecipeCategory);

		row = sheet.getRow(rownum + 4);
		cell = row.createCell(column);
		cell.setCellValue(ingredientList);

		row = sheet.getRow(rownum + 5);
		cell = row.createCell(column);
		cell.setCellValue(prepTime);

		row = sheet.getRow(rownum + 6);
		cell = row.createCell(column);
		cell.setCellValue(cookingTime);

		row = sheet.getRow(rownum + 7);
		cell = row.createCell(column);
		cell.setCellValue(prepMethod);

		row = sheet.getRow(rownum + 8);
		cell = row.createCell(column);
		cell.setCellValue(nutrientValue);

		row = sheet.getRow(rownum + 9);
		cell = row.createCell(column);
		cell.setCellValue(targetCondition);

		row = sheet.getRow(rownum + 10);
		cell = row.createCell(column);
		cell.setCellValue(url);

		FileOutputStream outputStream = new FileOutputStream(path);
		wb.write(outputStream);
		wb.close();
		inputStream.close();
		outputStream.close();

	}

}
