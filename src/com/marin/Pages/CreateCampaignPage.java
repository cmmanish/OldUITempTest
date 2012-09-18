
package com.marin.Pages;

import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class CreateCampaignPage extends PageWidgetHandler {

	static Logger log = Logger.getLogger(CreateCampaignPage.class);
	private WebDriver driver = null;
	public static final String PAGE_TITLE = "Marin: Create Campaign";

	public static final String CAMPAIGN_NAME = "id=jsCampaignAddCampaignName";
	public static final String PUBLISHER_CLIENTC_ACCOUNT_ID =
		"name = publisherClientAccountId";
	public static final String NEXT_BUTTON = "id=jsCampaignAddSave";
	public static final String END_DATE_ON_RB = "id=jsEndDateOnInput";
	public static final String END_DATE = "id=jsEndDateOnInput";
	public static final String DAILY_BUDGET = "id=dayBudget";
	public static final String SAVE_BUTTON = "id=jsSaveButton";

	public static final String campaignName = "AutoFBcampaignName" +
		(new Date()).getTime();;
	public static final String pcaID = "Facebook · Facebook QA";
	public static final String endDate = "9/30/12";
	public static final String daily_Budget = "1.01";

	public CreateCampaignPage(WebDriver driver) {

		super();
		this.driver = driver;

	}

	public String getPageTittle() {

		return driver.getTitle();

	}
}
