package testCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utilities.Constants;

public class BookFlightTickets {

	// creating object of ExcelUtils class
	static utilities.ExcelUtils excelUtils = new utilities.ExcelUtils();

	// using the Constants class values for excel file path
	static String excelFilePath = utilities.Constants.Path_TestData + Constants.File_TestData;

	public WebDriver driver;

	@BeforeTest
	public void operURL() {

		// setting the property of chrome browser and pass chromedriver path
		System.setProperty(Constants.DriverName, Constants.ChromeDriverpath);
		// Creating an object of ChromeDriver
		driver = new ChromeDriver();
		// launching URL
		driver.get(Constants.URL);
	}

	@Test
	public void applicationUnderTest() throws IOException, Throwable {

		// Select the From port
		Select fromPort = new Select(driver.findElement(By.name("fromPort")));
		fromPort.selectByVisibleText("Boston");
		// Select the To port
		Select toPort = new Select(driver.findElement(By.name("toPort")));
		toPort.selectByVisibleText("Rome");
		// Submit the details
		driver.findElement(By.xpath("//input[@type='submit']")).click();

		// Select the first
		driver.findElement(By.xpath("//tbody/tr[1]/td[1]/input[1]")).click();

		// Identify the WebElements for the student registration form
		WebElement Name = driver.findElement(By.xpath("//input[@id='inputName']"));
		WebElement Address = driver.findElement(By.xpath("//input[@id='address']"));
		WebElement city = driver.findElement(By.id("city"));
		WebElement state = driver.findElement(By.id("state"));
		WebElement zipcode = driver.findElement(By.id("zipCode"));
		WebElement creditCardNum = driver.findElement(By.id("creditCardNumber"));
		WebElement Month = driver.findElement(By.id("creditCardMonth"));
		WebElement Year = driver.findElement(By.id("creditCardYear"));
		WebElement NameOnCard = driver.findElement(By.id("nameOnCard"));

		WebElement PurchaseFlight = driver.findElement(By.xpath("//input[@type='submit']"));

		// calling the ExcelUtils class method to initialise the workbook and sheet
		excelUtils.setExcelFile(excelFilePath, "Passenger_Details");

		// iterate over all the row to print the data present in each cell.
		for (int i = 1; i <= excelUtils.getRowCountInSheet(); i++) {
			// Enter the values read from Excel in Name, Address, city, state, zipcode,
			// credit card details
			Name.sendKeys(excelUtils.getCellData(i, 0));
			Address.sendKeys(excelUtils.getCellData(i, 1));
			city.sendKeys(excelUtils.getCellData(i, 2));
			state.sendKeys(excelUtils.getCellData(i, 3));
			zipcode.sendKeys(excelUtils.getCellData(i, 4));
			Select cardType = new Select(driver.findElement(By.id("cardType")));
			cardType.selectByVisibleText("American Express");
			creditCardNum.sendKeys(excelUtils.getCellData(i, 5));
			Month.clear();
			Month.sendKeys(excelUtils.getCellData(i, 6));
			Year.clear();
			Year.sendKeys(excelUtils.getCellData(i, 7));
			NameOnCard.sendKeys(excelUtils.getCellData(i, 8));

			// Click on Purchase button
			PurchaseFlight.click();

			// Verify the confirmation message
			WebElement confirmationMessage = driver.findElement(By.xpath("//html/body/div[2]/div/h1"));

			// check if confirmation message is displayed
			if (confirmationMessage.isDisplayed()) {
				// if the message is displayed , write PASS in the excel sheet using the method
				// of ExcelUtils
				excelUtils.setCellValue(i, 9, "PASS", excelFilePath);
			} else {
				// if the message is not displayed , write FAIL in the excel sheet using the
				// method of ExcelUtils
				excelUtils.setCellValue(i, 9, "FAIL", excelFilePath);
			}

		}

	}

	@AfterTest
	public void Close() {
		// closing the driver
		driver.quit();
	}

}
