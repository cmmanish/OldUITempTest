package com.marin.PubAPI.facebook;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class myCampaign {

    private static Logger log = Logger.getLogger(myAdGroup.class);

    public class FbCampaignResponseObject {

	public String account_id;
	public String campaign_id;
	public String name;
	public long daily_budget;
	public long campaign_Status;
	public int daily_imps;
	public String id;
	public long start_time;
	public long end_time;
	public long updated_time;

    }

    public static void main(String[] args) throws HttpException, IOException {

	String request = "https://graph.facebook.com/6003996568607?date_format=U&"
		+ "access_token=AAAAAMSp0eVoBAOtxZBgwvMnq2BibSZC5LYMvsbhV1WCxlPf01mUOvWWLgA0Ud7GSSOKeWkJCbswPXdbJEZC8SLQuZBXLgrjucOiYj4AP8AZDZD";

	log.info(request);
	HttpClient client = new HttpClient();
	GetMethod method = new GetMethod(request);
	int statusCode = client.executeMethod(method);
	if (statusCode != HttpStatus.SC_OK) {
	    System.err.println("Method failed: " + method.getStatusLine());
	}
	String response = method.getResponseBodyAsString();

	log.info("response from Facebook \n" + response);
	Gson gson = new GsonBuilder().create();
	FbCampaignResponseObject campaign = gson.fromJson(response,
		FbCampaignResponseObject.class);

	log.info(campaign.campaign_id);

	log.info(campaign.name);
	log.info(campaign.campaign_Status);

	log.info(campaign.daily_budget);

    }
}
