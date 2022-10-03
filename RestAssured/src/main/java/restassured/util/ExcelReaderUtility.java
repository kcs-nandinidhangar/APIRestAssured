package restassured.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import restassured.constant.ApplicationConstant;
import restassured.dto.TestCase;

/**
 * the Class ExcelReaderUtility
 */

public class ExcelReaderUtility {

	/** The cached test cases. */
	private static List<TestCase> cachedTestCases = new ArrayList<>();

	/**
	 * Gets the test cases.
	 * 
	 * @param testCategory the test category
	 * @return the test cases
	 */
	public static List<TestCase> getTestCases(String testCategory) {
		if (CollectionUtils.isEmpty(cachedTestCases)) {
			Integer excelTabIndex = 0;
			cachedTestCases.addAll(getAllTestCases(ApplicationConstant.TESTCASE_FILE_NAME, excelTabIndex));
		}
		return findTestCasesForCategory(testCategory);
	}

	/**
	 * Gets the all test cases.
	 * 
	 * @return the all test cases
	 */
	public static List<TestCase> getAllTestCases() {
		if (CollectionUtils.isEmpty(cachedTestCases)) {
			Integer excelTabIndex = 0;
			cachedTestCases.addAll(getAllTestCases(ApplicationConstant.TESTCASE_FILE_NAME, excelTabIndex));
		}
		return cachedTestCases;
	}

	/**
	 * Find test cases for category.
	 * 
	 * @param testCategory the test category
	 * @return the list
	 */
	private static List<TestCase> findTestCasesForCategory(String testCategory) {
		List<TestCase> testCases = new ArrayList<>();
		for (TestCase testCase : cachedTestCases) {
			if (StringUtils.isNotBlank(testCategory) && StringUtils.isNotBlank(testCase.getTestCaseCategory())
					&& StringUtils.equalsIgnoreCase(testCategory.trim(), testCase.getTestCaseCategory().trim())
					&& !StringUtils.equalsIgnoreCase("No", testCase.getTestEnabled())) {
				testCases.add(testCase);
			}
		}
		return testCases;
	}

	/**
	 * Gets the all test cases.
	 * 
	 * @param excelFileName the excel file name
	 * @param excelTabIndex the excel tab index
	 * @return the all test cases
	 */
	private static List<TestCase> getAllTestCases(String excelFileName, Integer excelTabIndex) {
		List<TestCase> testCases = new ArrayList<>();
		try (FileInputStream excelFile = new FileInputStream(new File(excelFileName));
				Workbook workbook = new XSSFWorkbook(excelFile);) {
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			int rowCounter = 0;
			List<String> headerList = new ArrayList<>();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				if (rowCounter == 0) {
					getHeaderInformation(headerList, currentRow);
				}
				if (rowCounter > 0) {
					TestCase testCase = getTestCaseData(headerList, currentRow);
					testCases.add(testCase);
				}
				rowCounter++;
			}
		} catch (FileNotFoundException exception) {
			System.out.println("Failed to load file. Reason - {}");
		} catch (IOException exception) {
			System.out.println("Failed to read file. Reason - {}");
		}
		return testCases;
	}

	/**
	 * Gets the test case data.
	 * 
	 * @param headerList the header list
	 * @param currentRow the current row
	 * @return the test case data
	 */
	private static TestCase getTestCaseData(List<String> headerList, Row currentRow) {
		TestCase testCase = new TestCase();
		Iterator<Cell> cellIterator = currentRow.iterator();
		int count = 0;
		while (cellIterator.hasNext()) {
			Cell currentCell = cellIterator.next();
			String value = "";

			if (currentCell.getCellType() == CellType.STRING) {
				value = currentCell.getStringCellValue();
			} else if (currentCell.getCellType() == CellType.NUMERIC) {
				value = "" + currentCell.getNumericCellValue();
			}

			if (headerList.size() > count) {
				populateTestCaseInformation(testCase, headerList.get(count).trim(), value.trim());
			}
			count++;
		}
		return testCase;
	}

	/**
	 * Gets the header information.
	 * 
	 * @param headerList the header list
	 * @param currentRow the current row
	 * 
	 */
	private static void getHeaderInformation(List<String> headerList, Row currentRow) {
		Iterator<Cell> cellIterator = currentRow.iterator();
		while (cellIterator.hasNext()) {
			Cell currentCell = cellIterator.next();
			String value = currentCell.getStringCellValue();
			headerList.add(value);
		}
	}

	/**
	 * Populate test case information.
	 * 
	 * @param testCase   the test case
	 * @param fieldName  the field name
	 * @param fieldValue the field value
	 */
	private static void populateTestCaseInformation(TestCase testCase, String fieldName, String fieldValue) {
		switch (fieldName) {
		case "Test No.":
			testCase.setTestCaseNumber(fieldValue);
			break;
		case "Test Case Category":
			fieldValue = fieldValue.trim();
			fieldValue = fieldValue.toUpperCase();
			testCase.setTestCaseCategory(fieldValue);
			break;
		case "Description":
			testCase.setDescription(fieldValue);
			break;
		case "Expected Result":
			testCase.setExpectedResult(fieldValue);
			break;
		case "Actual Result":
			testCase.setActualResult(fieldValue);
			break;
		case "Tested By":
			testCase.setTestedBy(fieldValue);
			break;
		case "Test Execution Date":
			testCase.setTestExecutionDate(fieldValue);
			break;
		case "Test Execution Findings":
			testCase.setFindings("");
			break;
		case "Result":
			testCase.setTestCaseResult("");
			break;
		case "Is Enabled":
			testCase.setTestEnabled(StringUtils.isNotBlank(fieldValue) ? fieldValue.toUpperCase() : "YES");
			break;
		case "Test Input Data":
			testCase.setInputData(fieldValue);
			break;
		}
	}

	static Workbook book;
	static Sheet sheet;

	/**
	 * Read the data in excel
	 * 
	 * @param FilePath
	 * @param Sheetname
	 * @return
	 * @throws Exception
	 */
	public static Object[][] getTestData1(String FilePath, String Sheetname) throws Exception {

		FileInputStream file = new FileInputStream(FilePath);
		book = WorkbookFactory.create(file);
		sheet = book.getSheet(Sheetname);
		int totalRows = sheet.getLastRowNum();
		// System.out.println("totalRows= " + totalRows);
		int totalColums = sheet.getRow(0).getLastCellNum();
		// System.out.println("totalColums =" + totalColums);
		Object[][] data = new Object[totalRows][totalColums];
		for (int i = 0; i < totalRows; i++) {
			for (int j = 0; j < totalColums; j++) {
				DataFormatter formatter = new DataFormatter();
				data[i][j] = String.valueOf(formatter.formatCellValue(sheet.getRow(i + 1).getCell(j)));
				// System.out.println(data[i][j] );
			}
		}
		return data;
	}

	/*****************************/
}
