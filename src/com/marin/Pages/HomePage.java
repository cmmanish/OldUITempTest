
package com.marin.Pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class HomePage extends PageWidgetHandler {

	private static WebDriver driver = null;
	static Logger log = Logger.getLogger(HomePage.class);
	public static final String PAGE_TITTLE =
		"Marin: Client-Manish Auto-Dashboard";
	public static final String CAMPAIGNSTAB = "Campaigns";
	public static final String GROUPSTAB = "Groups";
	public static final String KEYWORDSSTAB = "Keywordss";

	public static final String ADMIN_LINK = "Admin";
	public static final String REPORTS_LINK = "Reports";
	public static final String LOGOUT = "Logout";

	public static final String CLIENT_NAME = "client_dd_0_input";

	public HomePage(WebDriver driver) {

		super();
		HomePage.driver = driver;

	}

	public static CampaignGridPage gotoCampaignPage(WebDriver driver) {

		try {
			HomePage.clickTab(driver, HomePage.CAMPAIGNSTAB);
		}
		catch (Exception e) {
			HomePage.clickTab(driver, HomePage.CAMPAIGNSTAB);
			e.printStackTrace();
		}
		log.info(driver.getTitle() + " loaded");

		return new CampaignGridPage(driver);
	}

	public static void gotoAdminPage(WebDriver driver) {

		try {
			HomePage.clickALink(driver, HomePage.ADMIN_LINK);
		}
		catch (Exception e) {
			HomePage.clickALink(driver, HomePage.ADMIN_LINK);
			e.printStackTrace();
		}

		log.info(driver.getTitle());

	}

	public static String getHomePageWelcomeMessage()
		throws Exception {

		return driver.getTitle();

	}

}
