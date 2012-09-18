
package com.marin.testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.marin.Pages.AdminPage;
import com.marin.Pages.HomePage;
import com.marin.Pages.LoginPage;
import com.marin.PubAPI.google.GoogleServiceV201109;

public class WebDriverBaseClass extends TestCase {

	static HashMap<Integer, String> fieldValuesMap =
		new HashMap<Integer, String>();
	static HashMap<Integer, String> testCaseMap =
		new HashMap<Integer, String>();
	static Logger log = Logger.getLogger(WebDriverBaseClass.class);
	public static String URL = "";
	static public String email = "";
	static public String password = "";
	public static String browser = "";
	public static String screenshotDir = "";
	public static String client = "Manish Auto - 1005016 - PPS, Inc.";
	static long threeSec = 3000l;
	static long PAGETIMEOUT = 1000l;
	long startTime;
	long endTime;
	public static WebDriver driver = null;

	public WebDriverBaseClass() {

		getConfig();

	}

	public static void waitForCorrectPageToLoad(
		WebDriver driver, long secs, final String text) {

		(new WebDriverWait(driver, secs)).until(new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver d) {

				return d.getTitle().toLowerCase().startsWith(text);
			}
		});
	}

	protected void waitUntilAjaxRequestCompletes() {

		final String JQUERY_ACTIVE_CONNECTIONS_QUERY = "return $.active == 0;";
		new FluentWait<WebDriver>(driver).withTimeout(
			PAGETIMEOUT, TimeUnit.SECONDS).pollingEvery(
			threeSec, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver d) {

				JavascriptExecutor jsExec = (JavascriptExecutor) d;
				return (Boolean) jsExec.executeScript(JQUERY_ACTIVE_CONNECTIONS_QUERY);
			}
		});
	}

	public enum Tabs {
		Campaigns, Groups, Keywords, Creatives, Folders, Placements, History,
			Accounts, Publishers, NegativeKeywords, NegativePlacements,
			ExpandedKeywords
	};

	public void getConfig() {

		Properties prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("Config.xml");
			prop.loadFromXML(fis);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URL = prop.getProperty("url");
		// log.info("getConfig " + URL);
		browser = prop.getProperty("browser");

		switch (browser) {

		case "Firefox":
			driver = new FirefoxDriver();
			log.info(driver.getTitle());
			break;

		case "Chrome":
			driver = new ChromeDriver();
			log.info(driver.getTitle());
			break;

		case "InternetExplorer":
			driver = new InternetExplorerDriver();
			log.info(driver.getTitle());
			break;

		default:
			driver = new HtmlUnitDriver();
			log.info(driver.getTitle());
			break;
		}

		email = prop.getProperty("email");
		password = prop.getProperty("password");
		screenshotDir = prop.getProperty("screenshotDir");

	}

	public void postAllChangesToPublishers() {

		if (!(driver.getTitle().toString().equalsIgnoreCase("Marin: Activity Log"))) {

			HomePage.clickALink(driver, HomePage.ADMIN_LINK);

		}
		AdminPage.clickCheckBox(driver, AdminPage.ACTIVITY_LOG_SUPER_SELECT);
		AdminPage.clickButton(driver, AdminPage.ACTIVITY_LOG_POSTNOW_BUTTON);
		log.info("Posted to Publisher");

	}

	public void activityLogClearPendingChanges() {

		if (!(driver.getTitle().equalsIgnoreCase(AdminPage.PAGE_TITTLE))) {

			HomePage.clickALink(driver, HomePage.ADMIN_LINK);
		}

		AdminPage.clickCheckBox(driver, AdminPage.ACTIVITY_LOG_SUPER_SELECT);
		AdminPage.clickButton(driver, AdminPage.ACTIVITY_LOG_CANCEL_BUTTON);
		log.info("Cleared the Activity Log");

	}

	public void gotoURL()
		throws InterruptedException {

		try {
			driver.navigate().to(URL);
			log.info("URL: " + driver.getCurrentUrl());
		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void login()
		throws Exception {

		log.info("in login() -- WebDriverBaseClass()");
		startTime = new Date().getTime();
		gotoURL();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAs(email, password);
		MSMAssertEquals(
			driver, HomePage.PAGE_TITTLE, HomePage.getHomePageWelcomeMessage());
		activityLogClearPendingChanges();

		changeClient();

	}

	public void waitTillSentToPublisher()
		throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		int i = 1, j = 4;
		String actualString = null;
		for (int second = 0; second < 60; second++) {

			actualString =
				(String) js.executeScript(" var tr = $('#right_table').find('tr');" +
					" var td = $(tr[" +
					i +
					"]).find('td');" +
					" var t= $(td[" +
					j + "]).text().trim(); " + " return t;");
			if (second >= 60) {
				System.exit(1);
			}
			else {

				if (actualString.equalsIgnoreCase("Succeded")) {
					break;
				}
				else {
					Thread.sleep(1000);
				}
			}

		}

		// while (driver.getTitle().toString() != "Marin: Activity Log") {
		// waitFor(2000l);
		// }

	}

	public void waitFor(long secs) {

		try {
			Thread.sleep(secs);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeClient() {

		String clientName =
			" var tr = $('#client_dd_0_input').val(); return tr;";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String curClient = (String) js.executeScript(clientName);
		driver.findElement(By.id(HomePage.CLIENT_NAME)).clear();
		// driver.findElement(By.id(client)).sendKeys(newClient);
		log.info("current  Client " + curClient);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id(HomePage.CLIENT_NAME)).sendKeys(client);
		curClient = (String) js.executeScript(clientName);
		log.info("changed Client " + curClient);
		// driver.findElement(By.linkText(report)).click();
	}

	public void verifyTextOnGrid(String columnName, String expectedString) {

		String actualString = "";

		switch (columnName) {
		case "Description":
			JavascriptExecutor js = (JavascriptExecutor) driver;
			actualString =
				(String) js.executeScript(" var tr = $('#right_table').find('tr');" +
					" var td = $(tr[" +
					1 +
					"]).find('td');" +
					" var t= $(td[" +
					1 + "]).text().trim(); " + " return t;");

			MSMAssertEquals(driver, actualString, expectedString);
			break;

		case "Status":
			js = (JavascriptExecutor) driver;
			actualString =
				(String) js.executeScript(" var tr = $('#right_table').find('tr');"
					+ " var td = $(tr[1]).find('td');"
					+ " var t= $(td[4]).text().trim(); " + " return t;");

			MSMAssertEquals(driver, actualString, expectedString);
			break;
		}
	}

	public String getScreenshotName() {

		DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMMdd_HHmm");
		Date date = new Date();
		String dateAndTime = dateFormat2.format(date);
		String filePath =
			screenshotDir + getClass().getSimpleName() + "\\" + dateAndTime;
		File dir = new File(filePath);
		if (!dir.exists())
			dir.mkdirs();
		return filePath + "\\" + this.getName() + "_" + (new Date()).getTime() +
			".png";
	}

	public void MSMCaptureScreenshot(WebDriver driver, String fileName) {

		String userAgent =
			(String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");

		if (userAgent.contains("Mozilla")) {
			try {

				File scrFile =
					((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(fileName));

			}
			catch (IOException io) {

				io.printStackTrace();

			}
			catch (UnsupportedOperationException uoe) {

				uoe.printStackTrace();
			}
			catch (WebDriverException wde) {
				log.info("Unable to take screenshot");
				wde.printStackTrace();
			}
		}
	}

	public void MSMAssertEquals(WebDriver driver, String Expected, String Actual) {

		String fileName = getScreenshotName();
		if (!(Expected.equalsIgnoreCase(Actual))) {
			MSMCaptureScreenshot(driver, fileName);

			assertEquals("\nCheck Screenshot:" + fileName, Expected, Actual);
		}

		assertEquals("Verifed correctly ", Expected, Actual);
	}

	public void verifyOnPublisher(String campaignName) {

		String status = null;
		try {
			GoogleServiceV201109 googleService = new GoogleServiceV201109();
			status = googleService.getStatus(campaignName);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (status.equalsIgnoreCase("ACTIVE"))
			log.info(campaignName + "is " + status);
		else {

			log.info("something went wrong");
		}

	}

	public String getPageTittle() {

		return driver.getTitle();

	}

	public void logout() {

		activityLogClearPendingChanges();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.logout();
		endTime = new Date().getTime();
		log.info("Time to execute " + (endTime - startTime) / 1000 + " secs");
		String userAgent =
			(String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");

		// Controller.insertRecordIntoTable("BAT", "login_Logout", "PASS",
		// email, userAgent);
		driver.close();

	}
}
