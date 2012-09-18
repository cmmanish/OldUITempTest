package com.marin.PubAPI.facebook;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marin.PubAPI.facebook.FbResponseObject.FbAllCampaingsResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbAllCreativesResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbAllGroupsResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbCampaignResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbCreativeResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbErrorResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbGroupResponseObject;

/**
 * Base class for service calls to json/rest APIs this has all the parse from
 * JSON
 * 
 * @author manish
 */
public abstract class AbstractJSONRestService {

    protected final Logger log = Logger
	    .getLogger(AbstractJSONRestService.class);

    protected String accountId = "87939945";
    protected String HOST = "https://graph.facebook.com";
    protected String PATH = "";
    protected String DATE_FORMAT_PARAM = "date_format=U&";
    protected final String ACCESS_TOKEN_KEY = "access_token=";
    protected int offset = 0;
    protected int limit = 1000;
    protected String OFFSET_AND_LIMIT = "offset=" + offset + "&limit=" + limit
	    + "&";
    protected String ACCESS_TOKEN_PARAM = "AAAAAMSp0eVoBABGh2bMaDe3HDqDkWCXoKc93Ik5gDuJmhgOCQH4Rz7NlibBKZA1OZCeqc91M9HwT7s44kx37nqvrbhk5Qrwy12o4s9HwZDZD";

    String response = "";

    public void buildAccessTokenParm() {

	// this method will build the access token that is need to Fb Rest Call
    }

    public String buildURI(String HOST, String PATH, String ACCESS_TOKEN_PARAM,
	    int offset, int limit) {
	OFFSET_AND_LIMIT = "&offset=" + offset + "&limit=" + limit + "&";
	String requestURI = HOST + PATH + DATE_FORMAT_PARAM + OFFSET_AND_LIMIT
		+ ACCESS_TOKEN_KEY + ACCESS_TOKEN_PARAM;

	log.info("requestURI =  " + requestURI);
	return requestURI;
    }

    public FbCampaignResponseObject doAFacebookGetCampaign(String campaignId)
	    throws Exception {

	HttpClient client = new HttpClient();
	PATH = "/" + campaignId;

	GetMethod method = new GetMethod(buildURI(HOST, PATH,
		ACCESS_TOKEN_PARAM, offset, limit));
	int statusCode = client.executeMethod(method);
	if (statusCode != HttpStatus.SC_OK) {
	    System.err.println("Method failed: " + method.getStatusLine());
	}

	response = method.getResponseBodyAsString();
	log.info(response);
	if (!response.contains("error")) {

	    return parseCampaignJSONResponse();
	}

	else
	    // need a better way to fix the error class
	    return parseCampaignJSONResponse();

    }

    public FbCreativeResponseObject doAFacebookGetCreative(String creativeId)
	    throws Exception {

	HttpClient client = new HttpClient();
	PATH = "/" + creativeId;
	GetMethod method = new GetMethod(buildURI(HOST, PATH,
		ACCESS_TOKEN_PARAM, offset, limit));
	int statusCode = client.executeMethod(method);
	if (statusCode != HttpStatus.SC_OK) {
	    System.err.println("Method failed: " + method.getStatusLine());
	}

	response = method.getResponseBodyAsString();
	// log.info(response);
	if (!response.contains("error")) {

	    return parseCreativeJSONResponse();
	}

	else
	    // need a better way to fix the error class
	    return parseCreativeJSONResponse();

    }

    public FbAllCampaingsResponseObject doAFacebookGetAllCampaigns(
	    String accountId) throws Exception {

	HttpClient client = new HttpClient();
	PATH = "/act_" + accountId + "/adcampaigns?";

	GetMethod method = new GetMethod(buildURI(HOST, PATH,
		ACCESS_TOKEN_PARAM, offset, limit));
	int statusCode = client.executeMethod(method);
	if (statusCode != HttpStatus.SC_OK) {
	    System.err.println("Method failed: " + method.getStatusLine());
	}

	response = method.getResponseBodyAsString();
	log.info(response);
	if (!response.contains("error")) {

	    return parseAllCampaignsJSONResponse();
	}

	else
	    log.info("error 	");
	// need a better way to fix the error class
	return parseAllCampaignsJSONResponse();

    }

    public FbAllGroupsResponseObject doAFacebookGetAllGroups(String accountId,
	    int offset, int limit) throws Exception {

	HttpClient client = new HttpClient();
	PATH = "/act_" + accountId + "/adgroups?";

	GetMethod method = new GetMethod(buildURI(HOST, PATH,
		ACCESS_TOKEN_PARAM, offset, limit));
	int statusCode = client.executeMethod(method);
	if (statusCode != HttpStatus.SC_OK) {
	    System.err.println("Method failed: " + method.getStatusLine());
	}

	response = method.getResponseBodyAsString();
	log.info("response is  " + response);
	if (!response.contains("error")) {

	    return parseAllGroupsJSONResponse();
	}

	else
	    log.info("error 	");
	// need a better way to fix the error class
	return parseAllGroupsJSONResponse();

    }

    public FbGroupResponseObject doAFacebookGetGroup(String accountId)
	    throws Exception {

	HttpClient client = new HttpClient();
	PATH = "/" + accountId;// 6004047686007adgroups
	GetMethod method = new GetMethod(buildURI(HOST, PATH,
		ACCESS_TOKEN_PARAM, offset, limit));
	int statusCode = client.executeMethod(method);
	if (statusCode != HttpStatus.SC_OK) {
	    System.err.println("Method failed: " + method.getStatusLine());
	}

	response = method.getResponseBodyAsString();
	log.info(response);
	if (!response.contains("error")) {

	    return parseGroupJSONResponse();
	}

	else
	    // need a better way to fix the error class
	    return parseGroupJSONResponse();

    }

    public FbAllCreativesResponseObject doAFacebookGetAllCreatives(
	    String accountId) throws Exception {

	HttpClient client = new HttpClient();
	PATH = "/act_" + accountId + "/adcreatives?";

	GetMethod method = new GetMethod(buildURI(HOST, PATH,
		ACCESS_TOKEN_PARAM, offset, limit));
	int statusCode = client.executeMethod(method);
	if (statusCode != HttpStatus.SC_OK) {
	    System.err.println("Method failed: " + method.getStatusLine());
	}

	response = method.getResponseBodyAsString();
	// log.info(response);
	if (!response.contains("error")) {

	    return parseAllCreativeJSONResponse();
	}

	else
	    log.info("error 	");
	// need a better way to fix the error class
	return parseAllCreativeJSONResponse();

    }

    public FbAllCampaingsResponseObject parseAllCampaignsJSONResponse() {

	Gson gson = new GsonBuilder().create();
	FbAllCampaingsResponseObject FbResponse = gson.fromJson(response,
		FbAllCampaingsResponseObject.class);

	return FbResponse;

    }

    public FbAllGroupsResponseObject parseAllGroupsJSONResponse() {

	Gson gson = new GsonBuilder().create();
	FbAllGroupsResponseObject FbResponse = gson.fromJson(response,
		FbAllGroupsResponseObject.class);

	return FbResponse;

    }

    public FbAllCreativesResponseObject parseAllCreativeJSONResponse() {

	Gson gson = new GsonBuilder().create();
	FbAllCreativesResponseObject FbResponse = gson.fromJson(response,
		FbAllCreativesResponseObject.class);

	return FbResponse;

    }

    public FbCampaignResponseObject parseCampaignJSONResponse() {

	Gson gson = new GsonBuilder().create();
	FbCampaignResponseObject FbResponse = gson.fromJson(response,
		FbCampaignResponseObject.class);

	return FbResponse;

    }

    public FbGroupResponseObject parseGroupJSONResponse() {

	Gson gson = new GsonBuilder().create();
	FbGroupResponseObject FbResponse = gson.fromJson(response,
		FbGroupResponseObject.class);

	return FbResponse;

    }

    public FbCreativeResponseObject parseCreativeJSONResponse() {

	Gson gson = new GsonBuilder().create();
	FbCreativeResponseObject FbResponse = gson.fromJson(response,
		FbCreativeResponseObject.class);

	return FbResponse;

    }

    public FbErrorResponseObject parseErrorJSONResponse() {

	Gson gson = new GsonBuilder().create();
	FbErrorResponseObject FbResponse = gson.fromJson(response,
		FbErrorResponseObject.class);

	return FbResponse;
    }

}
