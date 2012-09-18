
package com.marin.Pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class CampaignGridPage extends PageWidgetHandler {

	static Logger log = Logger.getLogger(CampaignGridPage.class);

	private static WebDriver driver;
	public static final String PAGE_TITLE = "Marin: Campaign Summary";
	public static final String CREATEBUTTON = "id=campaigns_action_add";
	public static final String EDITBUTTON = "id=campaigns_action_edit";
	public static final String DELETEBUTTON = "id=campaigns_action_delete";

	public CampaignGridPage(WebDriver driver) {

		super();
		CampaignGridPage.driver = driver;
	}

	public static String getPageTittle() {

		return driver.getTitle();

	}
}
