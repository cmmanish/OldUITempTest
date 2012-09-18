
package com.marin.testScripts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.marin.Pages.HomePage;

public class LoginTest extends WebDriverBaseClass {

	static Logger log = Logger.getLogger(HomePage.class);
	private static WebDriver driver = null;
	private static String URL = "";
	static public String email = "";
	static public String password = "";
	private static String browser = "";
	private static String client = "";
	private static long threeSec = 3000l;
	private static long PAGETIMEOUT = 1000l;

	public LoginTest() {

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
		if (browser.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
			log.info(driver.getTitle());

		}
		email = prop.getProperty("email");
		password = prop.getProperty("password");

	}

	@Before
	public void before() {

	}

	@Test
	@After
	public void after() {

		driver.get(URL);
		driver.manage().deleteAllCookies();
		driver.quit();
	}

}
