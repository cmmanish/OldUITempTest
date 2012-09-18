
package com.marin.Pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class PageWidgetHandler {

	static Logger log = Logger.getLogger(PageWidgetHandler.class);

	public static void printPageTittle(WebDriver driver) {

		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String pageTittle = driver.getTitle();
		log.info(pageTittle);
	}

	public static void typeInTextbox(
		WebDriver driver, String keyValue, String inputText) {

		try {

			if (!(keyValue.contains("="))) {

				driver.findElement(By.name(keyValue)).sendKeys(inputText);
				log.info("Entered " + keyValue);
			}
			else {
				String key = keyValue.split("=")[0];
				String value = keyValue.split("=")[1];

				if (key.equalsIgnoreCase("id")) {

					driver.findElement(By.id(value)).sendKeys(inputText);
				}
				else if (key.equalsIgnoreCase("name")) {

					driver.findElement(By.name(value)).sendKeys(inputText);
				}
				else if (key.equalsIgnoreCase("text")) {

					driver.findElement(By.name(value)).sendKeys(inputText);
				}

				log.info("Typed " + value);
			}
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();

		}
		printPageTittle(driver);

	}

	public static void clickTab(WebDriver driver, String keyValue) {

		try {

			if (!(keyValue.contains("="))) {

				driver.findElement(By.linkText(keyValue)).click();
				log.info("Clicked " + keyValue);
			}
			else {
				String key = keyValue.split("=")[0];
				String value = keyValue.split("=")[1];

				if (key.equalsIgnoreCase("id")) {

					driver.findElement(By.id(value)).click();
				}
				else if (key.equalsIgnoreCase("name")) {

					driver.findElement(By.name(value)).click();
				}
				else if (key.equalsIgnoreCase("text")) {

					driver.findElement(By.linkText(value)).click();
				}

				log.info("Clicked " + value);
			}
			log.info("In " + driver.getTitle());
		}
		catch (NoSuchElementException e) {

			e.printStackTrace();

		}
	}

	public static void selectRadioButton(WebDriver driver, String keyValue) {

		if (!(keyValue.contains("="))) {

			driver.findElement(By.linkText(keyValue)).click();
			log.info("Clicked " + keyValue);
		}
		else {
			String key = keyValue.split("=")[0];
			String value = keyValue.split("=")[1];

			if (key.equalsIgnoreCase("id")) {

				driver.findElement(By.id(value)).click();
			}
			else if (key.equalsIgnoreCase("name")) {

				driver.findElement(By.name(value)).click();
			}
		}

		log.info("Radio button clicked ");

		printPageTittle(driver);
	}

	public static void clickCheckBox(WebDriver driver, String keyValue) {

		if (!(keyValue.contains("="))) {

			driver.findElement(By.linkText(keyValue)).click();
			log.info("Clicked " + keyValue);
		}
		else {
			String key = keyValue.split("=")[0];
			String value = keyValue.split("=")[1];

			if (key.equalsIgnoreCase("id")) {

				driver.findElement(By.id(value)).click();
			}
			else if (key.equalsIgnoreCase("name")) {

				driver.findElement(By.name(value)).click();
			}
			else if (key.equalsIgnoreCase("text")) {

				driver.findElement(By.linkText(value)).click();
			}

			log.info("Clicked " + value);
		}
		printPageTittle(driver);
	}

	public static void clickALink(WebDriver driver, String keyValue) {

		if (!(keyValue.contains("="))) {

			driver.findElement(By.linkText(keyValue)).click();
			log.info("Clicked " + keyValue);
		}
		else {
			String key = keyValue.split("=")[0];
			String value = keyValue.split("=")[1];

			if (key.equalsIgnoreCase("id")) {

				driver.findElement(By.id(value)).click();
			}
			else if (key.equalsIgnoreCase("name")) {

				driver.findElement(By.name(value)).click();
			}
			else if (key.equalsIgnoreCase("text")) {

				driver.findElement(By.linkText(value)).click();
			}

			log.info("Clicked " + value);
		}

		printPageTittle(driver);
	}

	public static void selectValue(WebDriver driver, String value) {

		List<WebElement> options = driver.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (value.equalsIgnoreCase(option.getText())) {
				option.click();
				log.info("selected " + option.getText());
				break;
			}
		}
		printPageTittle(driver);

	}

	public static void clickButton(WebDriver driver, String keyValue) {

		if (!(keyValue.contains("="))) {

			driver.findElement(By.name(keyValue)).click();
			log.info("Clicked " + keyValue);
		}
		else {
			String key = keyValue.split("=")[0];
			String value = keyValue.split("=")[1];

			if (key.equalsIgnoreCase("id")) {

				driver.findElement(By.id(value)).click();
			}
			else if (key.equalsIgnoreCase("name")) {

				driver.findElement(By.name(value)).click();
			}
			else if (key.equalsIgnoreCase("text")) {

				driver.findElement(By.name(value)).click();
			}

			log.info("Clicked " + value);
			// assertEquals(
			// "Not in " + driver.getTitle(), CreateCampaignPage.PAGE_TITLE,
			// driver.getTitle());
		}
		log.info("In " + driver.getTitle());

	}

}
