
package com.marin.Pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class LoginPage extends PageWidgetHandler {

	public static Logger log = Logger.getLogger(HomePage.class);
	public final static String PAGE_TITTLE = "Login:";
	private static WebDriver driver;
	private final static String LOGIN = "email";
	private final static String PASSWORD = "saveable_password";
	private final static String SIGN_IN = "sign_in";
	private final String APP_VERSION_CSS_SELECTOR = "span[class='company']";
	private static final String ADMIN = "Admin";
	private static final String REPORTS = "Reports";
	private static final String LOGOUT = "Logout";

	public LoginPage(WebDriver driver) {

		super();
		LoginPage.driver = driver;

	}

	public HomePage loginAs(String username, String password) {

		try {
			log.info(getPageTittle());

			// MSMAssertEquals(driver, LoginPage.PAGE_TITTLE,
			// LoginPage.getPageTittle());
			if ((driver.getTitle()).equalsIgnoreCase(LoginPage.PAGE_TITTLE)) {
				log.info("Going to Login as " + username);

				typeInTextbox(driver, LOGIN, username);
				typeInTextbox(driver, PASSWORD, password);
				clickButton(driver, SIGN_IN);
			}
			else {

				log.info("In the Wrong Page, going to QUIT ");
				driver.close();

			}
		}

		catch (NoSuchElementException e) {
			e.printStackTrace();

		}
		return new HomePage(driver);
	}

	public LoginPage logout() {

		driver.findElement(By.linkText(LOGOUT)).click();
		log.info("Clicked " + LOGOUT);
		return new LoginPage(driver);
	}

	public static String getPageTittle() {

		return driver.getTitle();

	}
}
