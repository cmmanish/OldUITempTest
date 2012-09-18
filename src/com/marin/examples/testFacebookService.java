
package com.marin.examples;

/*
 * this is a test file which tests the facebook service.  basically this is how the calls will be made from framework s
 * */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.marin.PubAPI.facebook.FacebookService;
import com.marin.PubAPI.facebook.FbResponseObject.FbCampaignResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbCreativeResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbGroupResponseObject;

public class testFacebookService {

	String campaignName = " Facebook_Auto_Campaign"; // campaign under
	// question
	String accountId = ""; // this value will be in the
	// properties file
	String groupName = "group8252"; // so now will

	// be global

	public testFacebookService()
		throws IOException {

	}

	public void testGetCampainDetails(String campaignId)
		throws Exception {

		FacebookService fb = new FacebookService();
		FbCampaignResponseObject response =
			fb.doAFacebookGetCampaign(campaignId);

		System.out.println("campaignId " + fb.getCampaignId(response));
		System.out.println("campaignName " + fb.getCampaignName(response));
		System.out.println("dailyBudget " + fb.getDailyBudget(response));
		System.out.println("Status " + fb.getCampaignStatus(response));
		System.out.println("StartTime " + fb.getStartTime(response));
		System.out.println("UpdatedTime " + fb.getUpdatedTime(response));
		// testGroupObject();

	}

	public void testGroupObject()
		throws Exception {

		// System.out.println("IN testGroupObject() accountId " + accountId);
		FacebookService fb = new FacebookService();

		Map<String, String> groupNameToId = new HashMap<String, String>();
		groupNameToId = fb.mapGroupNameToIdFromResponse();

		String groupId = groupNameToId.get(groupName);
		FbGroupResponseObject response = fb.doAFacebookGetGroup(groupId);

		if (groupId != null) {
			System.out.println("groupId " + groupId);
			System.out.println("groupName " + groupName);
		}
		else
			System.out.println("groupName " + groupName + " Not Found in " +
				groupId);

		// int[] gender = fb.getGenderFromTargeting(response);
		//
		// for (int i = 0; i < gender.length; i++)
		// System.out.println("gender " + gender[i]);

		int maxAge = fb.getMaxAgeFromTargeting(response);
		int minAge = fb.getMinAgeFromTargeting(response);

		String[] cities = fb.getCitiesFromTargeting(response);
		for (int i = 0; i < cities.length; i++)
			System.out.println("cities " + cities[i]);
		String[] creativeId = fb.getCreativeIds(response);
		int creativeCount = creativeId.length;
		for (int i = 0; i < creativeCount; i++) {
			System.out.println("__________--------__________");

			testCreativeObject(creativeId[i]);

		}
	}

	public void testCreativeObject(String creativeId)
		throws Exception {

		FacebookService fb = new FacebookService();
		FbCreativeResponseObject response =
			fb.doAFacebookGetCreative(creativeId);
		System.out.println("creativeId " + creativeId);
		System.out.println("Creative Name - " + fb.getCreativeName(response));
		int creativeType = Integer.parseInt(fb.getType(response));
		System.out.println("Type " + creativeType);

		switch (creativeType) {

		case 1:
			// System.out.println("Type " + creativeType);
			System.out.println("Title - " + fb.getTitle(response));
			System.out.println("Body - " + fb.getBody(response));
			System.out.println("Image Hash - " + fb.getImageHash(response));
			System.out.println("Link URL - " + fb.getLinkUrl(response));
			System.out.println("Run Status - " + fb.getRunStatus(response));
			System.out.println("Preview URL - " + fb.getPreviewUrl(response));
			System.out.println("Image URL - " + fb.getImageUrl(response));

			break;
		case 27:
			// System.out.println("Type " + creativeType);
			System.out.println("Related Fan Page - " +
				fb.getRelatedFanPage(response));
			System.out.println("Title " + fb.getTitle(response));
			System.out.println("Run Status " + fb.getRunStatus(response));
			System.out.println(fb.getPreviewUrl(response));
			break;
		case 25:
			// System.out.println("Type " + creativeType);
			System.out.println("Related Fan Page - " +
				fb.getRelatedFanPage(response));
			System.out.println("Title " + fb.getTitle(response));
			System.out.println("Run Status " + fb.getRunStatus(response));
			System.out.println("Preview URL " + fb.getPreviewUrl(response));
			break;

		}

		System.out.println("---------------------------------");

	}

	public void testCampaignObject()
		throws Exception {

		System.out.println("testGetAllCampaign() ");
		FacebookService fb = new FacebookService();
		Map<String, String> campaignNameToId = new HashMap<String, String>();
		campaignNameToId = fb.mapCampaignNameToIdFromResponse();

		String campaignId = campaignNameToId.get(campaignName);
		if (campaignId != null) {
			System.out.println("campaignId " + campaignId);

		}
		else {
			System.out.println("campaign " + campaignName + "not found");
		}
		// getAllInfoAboutTheCurrentCampaign for the current campaign

		testGetCampainDetails(campaignId);

	}

	public void readProps()
		throws IOException {

		Properties prop = new Properties();
		FileInputStream fis =
			new FileInputStream(
				"/Users//pkrish//workspace//WebDriverUI//facebook.properties");
		prop.load(fis);

		System.out.println("ACCOUNT_ID " + prop.getProperty("ACCOUNT_ID"));
		System.out.println("ACCESS_TOKEN_PARAM " +
			prop.getProperty("ACCESS_TOKEN_PARAM"));
	}

	public static void main(String[] args)
		throws Exception {

		testFacebookService myTests = new testFacebookService();
		// myTests.readProps();
		// myTests.testCampaignObject();
		myTests.testGroupObject();

	}
}
