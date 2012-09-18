/**
 * 
 */

package com.marin.testScripts;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;

import com.marin.Pages.CampaignGridPage;
import com.marin.Pages.CreateCampaignPage;
import com.marin.Pages.HomePage;

/**
 * @author mmadhusoodan
 */
public class testBAT extends WebDriverBaseClass {

	static Logger log = Logger.getLogger(testBAT.class);

	public testBAT()
		throws InterruptedException {

		super();
	}

	@Before
	public void setUp()
		throws Exception {

		login();

	}

	/**
	 * @throws java.lang.Exception
	 */

	@Test
	public void testCreateCampaign()
		throws Exception {

		HomePage.gotoCampaignPage(driver);
		CampaignGridPage.clickButton(driver, CampaignGridPage.CREATEBUTTON);
		CreateCampaignPage.typeInTextbox(
			driver, CreateCampaignPage.CAMPAIGN_NAME,
			CreateCampaignPage.campaignName);
		CreateCampaignPage.selectValue(driver, CreateCampaignPage.pcaID);
		CreateCampaignPage.clickButton(driver, CreateCampaignPage.NEXT_BUTTON);
		// CreateCampaignPage.selectRadioButton(
		// driver, CreateCampaignPage.END_DATE_ON_RB);
		CreateCampaignPage.typeInTextbox(
			driver, CreateCampaignPage.END_DATE, CreateCampaignPage.endDate);
		CreateCampaignPage.typeInTextbox(
			driver, CreateCampaignPage.DAILY_BUDGET,
			CreateCampaignPage.daily_Budget);
		CreateCampaignPage.clickButton(driver, CreateCampaignPage.SAVE_BUTTON);
		MSMAssertEquals(
			driver, CampaignGridPage.PAGE_TITLE,
			CampaignGridPage.getPageTittle());
		String executeScriptValue =
			(String) ((JavascriptExecutor) driver).executeScript("var a = $('.good').text(); return a;");
		String campaignCreatedText =
			"Campaign successfully created. See Activity Log for details.";
		MSMAssertEquals(driver, campaignCreatedText, executeScriptValue);
		HomePage.gotoAdminPage(driver);
		verifyTextOnGrid("Description", "Create: Facebook Campaign: " +
			CreateCampaignPage.campaignName + ".");
		verifyTextOnGrid("Status", "To be sent");

	}

	@After
	public void tearDown()
		throws Exception {

		logout();

	}

}
