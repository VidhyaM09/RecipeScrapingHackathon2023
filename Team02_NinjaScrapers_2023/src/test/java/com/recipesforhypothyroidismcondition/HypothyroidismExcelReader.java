package com.recipesforhypothyroidismcondition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HypothyroidismExcelReader {

	public static String[][] getData(String sheetname) throws IOException {
		String projectDir = System.getProperty("user.dir");
		String path = projectDir + "/src/test/resources/IngredientsForHypothyroidism.xlsx";
		File ExcelFile = new File(path);
		FileInputStream FIS = new FileInputStream(ExcelFile);
		XSSFWorkbook workbook = new XSSFWorkbook(FIS);
		XSSFSheet sheet = workbook.getSheet(sheetname);
		int row = sheet.getLastRowNum();
		Row rowcell = sheet.getRow(0);
		int totcol = rowcell.getLastCellNum();
		DataFormatter format = new DataFormatter();
		String testdata[][] = new String[row][totcol];
		for (int i = 1; i <= row; i++) {
			for (int j = 0; j < totcol; j++) {
				testdata[i - 1][j] = format.formatCellValue(sheet.getRow(i).getCell(j));
			}
		}

		return testdata;
	}

	public static int getLastColumn(String sheetname,String path) throws IOException {
		FileInputStream FIS = new FileInputStream(new File(path));
		XSSFWorkbook workbook = new XSSFWorkbook(FIS);
		XSSFSheet sheet = workbook.getSheet(sheetname);
		int row = sheet.getLastRowNum();
		Row rowcell = sheet.getRow(row);
		int totcol = rowcell.getLastCellNum();
		return totcol + 1;
	}

}
