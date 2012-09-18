package com.marin.PubAPI.facebook;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class myAdGroup {

    private static Logger log = Logger.getLogger(myAdGroup.class);

    public class Adgroup {
	public String adgroup_id;
	public String ad_id;
	public String campaign_id;
	public String name;
	public int adgroup_status;
	public int bid_type;
	public int max_bid;
	public Bid_Info bid_info;
	public String[] disapprove_reason_descriptions;
	public String ad_status;
	public String account_id;
	public String id;
	public String[] creative_ids;
	public Targeting targeting;
	public int age_max;
	public int age_min;
	public String[] zips;
	public String[] countries;
	public String[] keywords;
	public String[] relationship_statuses;
	public String[] interested_in;
	public Work_Networks work_networks;
	public String[] locales;
	public Conversion_Specs[] conversion_specs;
	public long start_time;
	public long end_time;
	public long updated_time;

    }

    public class Bid_Info {

	public String c1; // need to fix this

    }

    public class Targeting {

	public String gender;

    }

    public class Work_Networks {

	public String id;
	public String name;

    }

    public class Conversion_Specs {

	public String action_type; // need to fix this
	public String page;

    }

    public static void main(String[] args) throws HttpException, IOException {

	String request = "https://graph.facebook.com/6003996568607/adgroups?date_format=U&&offset=0&limit=500&"
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
	Adgroup adGroup = gson.fromJson(response, Adgroup.class);
	log.info(adGroup.account_id);
	log.info(adGroup.adgroup_id);
	log.info(adGroup.name);
	log.info(adGroup.start_time);
	log.info(adGroup.adgroup_status);

    }
}
