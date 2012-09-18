package com.marin.PubAPI.google;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.lib.AuthToken;
import com.google.api.adwords.lib.utils.ImageUtils;
import com.google.api.adwords.lib.utils.v201109.ReportUtils;
import com.google.api.adwords.v201109.cm.AdGroup;
import com.google.api.adwords.v201109.cm.AdGroupAd;
import com.google.api.adwords.v201109.cm.AdGroupAdOperation;
import com.google.api.adwords.v201109.cm.AdGroupAdReturnValue;
import com.google.api.adwords.v201109.cm.AdGroupAdServiceInterface;
import com.google.api.adwords.v201109.cm.AdGroupCriterion;
import com.google.api.adwords.v201109.cm.AdGroupCriterionOperation;
import com.google.api.adwords.v201109.cm.AdGroupCriterionReturnValue;
import com.google.api.adwords.v201109.cm.AdGroupCriterionServiceInterface;
import com.google.api.adwords.v201109.cm.AdGroupOperation;
import com.google.api.adwords.v201109.cm.AdGroupPage;
import com.google.api.adwords.v201109.cm.AdGroupReturnValue;
import com.google.api.adwords.v201109.cm.AdGroupServiceInterface;
import com.google.api.adwords.v201109.cm.AdGroupStatus;
import com.google.api.adwords.v201109.cm.AdScheduleTarget;
import com.google.api.adwords.v201109.cm.AdScheduleTargetList;
import com.google.api.adwords.v201109.cm.AgeRange;
import com.google.api.adwords.v201109.cm.Bid;
import com.google.api.adwords.v201109.cm.BiddableAdGroupCriterion;
import com.google.api.adwords.v201109.cm.BiddingStrategy;
import com.google.api.adwords.v201109.cm.Budget;
import com.google.api.adwords.v201109.cm.BudgetBudgetDeliveryMethod;
import com.google.api.adwords.v201109.cm.BudgetBudgetPeriod;
import com.google.api.adwords.v201109.cm.Campaign;
import com.google.api.adwords.v201109.cm.CampaignCriterion;
import com.google.api.adwords.v201109.cm.CampaignCriterionPage;
import com.google.api.adwords.v201109.cm.CampaignCriterionServiceInterface;
import com.google.api.adwords.v201109.cm.CampaignOperation;
import com.google.api.adwords.v201109.cm.CampaignPage;
import com.google.api.adwords.v201109.cm.CampaignReturnValue;
import com.google.api.adwords.v201109.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109.cm.CampaignStatus;
import com.google.api.adwords.v201109.cm.CampaignTargetPage;
import com.google.api.adwords.v201109.cm.CampaignTargetSelector;
import com.google.api.adwords.v201109.cm.CampaignTargetServiceInterface;
import com.google.api.adwords.v201109.cm.Carrier;
import com.google.api.adwords.v201109.cm.Dimensions;
import com.google.api.adwords.v201109.cm.Gender;
import com.google.api.adwords.v201109.cm.GeoPoint;
import com.google.api.adwords.v201109.cm.Image;
import com.google.api.adwords.v201109.cm.ImageAd;
import com.google.api.adwords.v201109.cm.Keyword;
import com.google.api.adwords.v201109.cm.KeywordMatchType;
import com.google.api.adwords.v201109.cm.Language;
import com.google.api.adwords.v201109.cm.Location;
import com.google.api.adwords.v201109.cm.ManualCPC;
import com.google.api.adwords.v201109.cm.ManualCPCAdGroupBids;
import com.google.api.adwords.v201109.cm.Media;
import com.google.api.adwords.v201109.cm.MediaMediaType;
import com.google.api.adwords.v201109.cm.MediaPage;
import com.google.api.adwords.v201109.cm.MediaServiceInterface;
import com.google.api.adwords.v201109.cm.Money;
import com.google.api.adwords.v201109.cm.NetworkSetting;
import com.google.api.adwords.v201109.cm.OperatingSystemVersion;
import com.google.api.adwords.v201109.cm.Operator;
import com.google.api.adwords.v201109.cm.OrderBy;
import com.google.api.adwords.v201109.cm.Paging;
import com.google.api.adwords.v201109.cm.Placement;
import com.google.api.adwords.v201109.cm.Platform;
import com.google.api.adwords.v201109.cm.Polygon;
import com.google.api.adwords.v201109.cm.Predicate;
import com.google.api.adwords.v201109.cm.PredicateOperator;
import com.google.api.adwords.v201109.cm.Proximity;
import com.google.api.adwords.v201109.cm.ReportDefinitionField;
import com.google.api.adwords.v201109.cm.ReportDefinitionReportType;
import com.google.api.adwords.v201109.cm.ReportDefinitionServiceInterface;
import com.google.api.adwords.v201109.cm.Selector;
import com.google.api.adwords.v201109.cm.SortOrder;
import com.google.api.adwords.v201109.cm.TargetList;
import com.google.api.adwords.v201109.cm.TemplateAd;
import com.google.api.adwords.v201109.cm.TemplateElement;
import com.google.api.adwords.v201109.cm.TemplateElementField;
import com.google.api.adwords.v201109.cm.TemplateElementFieldType;
import com.google.api.adwords.v201109.cm.TextAd;
import com.google.api.adwords.v201109.cm.Video;

public class GoogleServiceV201109 {
    public static final String[] allCampaignFields = { "Id", "Name", "Status",
	    "StartDate", "EndDate", "Settings", "BiddingStrategy", "Amount",
	    "Period", "AdServingOptimizationStatus", "TargetGoogleSearch",
	    "TargetSearchNetwork", "TargetContentNetwork",
	    "TargetContentContextual", "TargetPartnerSearchNetwork" };
    CampaignServiceInterface campaignService;
    CampaignTargetServiceInterface campaignTargetService;
    Selector selector = new Selector();
    BiddingStrategy biddingStrategy;
    Campaign campaign;
    CampaignPage page;
    AdWordsUser user;
    Keyword keyword;

    public static final String noEndDate = "20371230";
    public static final String[] allCampaignNetworks = { "Google Search",
	    "Search Network", "Content Network", "Content Contextual",
	    "Partner Search Network" };
    public static final String[] allOperatingSystems = { "Android", "WebOS",
	    "iOS" };
    public static final String[] allCampaignBiddingStratergies = { "ManualCPC",
	    "BudgetOptimizer", "ManualCPM", "ConversionOptimizer" };
    public static final String[] allDeviceTargets = { "Desktop",
	    "HighEndMobile", "Tablet" };
    public static final String[] allCampaignStatuses = { "ACTIVE", "PAUSED",
	    "DELETED" };
    public static final String[] allAdServingIDs = { "ROTATE", "OPTIMIZE",
	    "UNAVAILABLE" };
    public static final String[] allAgeRangeTargets = { "AGE_RANGE_18_24",
	    "AGE_RANGE_25_34", "AGE_RANGE_35_44", "AGE_RANGE_45_54",
	    "AGE_RANGE_55_64", "AGE_RANGE_65_UP", "AGE_RANGE_UNDETERMINED",
	    "UNKNOWN" };
    public static final String[] allGenderTargets = { "GENDER_MALE",
	    "GENDER_FEMALE", "GENDER_UNDETERMINED" };
    public static final String[] budgetCurrencies = { "$" };
    public static final String[] allBudgetPeriods = { "DAILY", "MONTHLY" };
    public static final String[] allAdScheduleWeekdays = { "MONDAY", "TUESDAY",
	    "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
    public static final String[] allAdScheduleMinutes = { "ZERO", "FIFTEEN",
	    "THIRTY", "FORTY_FIVE" };

    /**
     * Constructor for Google Service UserName and Password are in
     * adwords.properties file CampaignPage object 'page' will also be populated
     * during the constructor call
     * 
     * @throws Exception
     */
    public GoogleServiceV201109() throws Exception {
	try {
	    // AdWordsServiceLogger.log();
	    user = new AdWordsUser();
	    campaignService = user
		    .getService(AdWordsService.V201109.CAMPAIGN_SERVICE);
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("No campaigns were found.");
	}
    }

    /**
     * Gets all Campaigns belonging to the user given in Constructor
     * 
     * @return All Campaigns as CampaignPage
     * @throws Exception
     */
    public CampaignPage getAllCampaigns() throws Exception {
	selector = new Selector();
	selector.setFields(allCampaignFields);
	selector.setOrdering(new OrderBy[] { new OrderBy("Name",
		SortOrder.ASCENDING) });
	CampaignPage page = campaignService.get(selector);
	return page;
    }

    /**
     * Binds the given campaign to the 'campaign' object of this class This
     * campaign will be used for all following Google calls
     * 
     * @param campaignName
     *            - Name of the campaign under usage
     * @throws Exception
     */
    public void bindCampaign(String campaignName) throws Exception {
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	selector = new Selector();
	selector.setPredicates(new Predicate[] { campaignNamePredicate });
	selector.setFields(allCampaignFields);
	selector.setOrdering(new OrderBy[] { new OrderBy("Name",
		SortOrder.ASCENDING) });
	CampaignPage page = campaignService.get(selector);
	if (page.getEntries() != null) {
	    for (Campaign campaign : page.getEntries()) {
		if (campaignName.equalsIgnoreCase(campaign.getName())) {
		    this.campaign = campaign;
		    break;
		}
	    }
	} else {
	    System.out.println(campaignName + " campaign was NOT found.");
	}
    }

    /**
     * Method to get status of the given campaign
     * 
     * @param campaignName
     * @return "ACTIVE", "PAUSED", "DELETED"
     * @throws Exception
     */
    public String getStatus(String campaignName) throws Exception {
	bindCampaign(campaignName);
	if (campaign.getStatus() != null) {
	    return campaign.getStatus().getValue();
	} else {
	    System.out.println(campaignName + " campaign was NOT found.");
	}
	return null;
    }

    /**
     * Method to get Start Date of a Campaign
     * 
     * @param campaignName
     * @return Start Date as String in yyyyMMDD format
     * @throws Exception
     */
    public String getStartDate(String campaignName) throws Exception {
	bindCampaign(campaignName);
	return campaign.getStartDate();
    }

    /**
     * Method to get End Date of a Campaign
     * 
     * @param campaignName
     * @return End Date as String in yyyyMMDD format
     * @note returns 20371230 if 'No End Date Set'
     * @throws Exception
     */
    public String getEndDate(String campaignName) throws Exception {
	bindCampaign(campaignName);
	return campaign.getEndDate();
    }

    /**
     * Method to get Budget
     * 
     * @param campaignName
     * @return Budget as String in <currency><amount><period> format. Eg:
     *         $3.27DAILY
     * @throws Exception
     */
    public String getBudget(String campaignName) throws Exception {
	bindCampaign(campaignName);
	if (campaign.getBudget() != null) {
	    return budgetCurrencies[0]
		    + ((float) campaign.getBudget().getAmount()
			    .getMicroAmount() / 1000000)
		    + campaign.getBudget().getPeriod();
	} else {
	    System.out.println(campaignName + " campaign was NOT found.");
	}
	return null;
    }

    /**
     * Return Bidding Stratergy
     * 
     * @param campaignName
     * @return "ManualCPC", "BudgetOptimizer", "ManualCPM",
     *         "ConversionOptimizer"
     * @throws Exception
     */
    public String getBiddingStratergy(String campaignName) throws Exception {
	bindCampaign(campaignName);
	return campaign.getBiddingStrategy().getBiddingStrategyType();
    }

    /**
     * Returns Ad Serving Optimization Status
     * 
     * @param campaignName
     * @return "ROTATE", "OPTIMIZE", "UNAVAILABLE"
     * @throws Exception
     */
    public String getAdServingOptimizationStatus(String campaignName)
	    throws Exception {
	bindCampaign(campaignName);
	return campaign.getAdServingOptimizationStatus().getValue();
    }

    /**
     * Returns Network Targets for a given Campaign
     * 
     * @param campaignName
     * @return "Google Search", "Search Network", "Content Network",
     *         "Content Contextual"
     * @throws Exception
     */
    public String[] getNetworkTargets(String campaignName) throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	bindCampaign(campaignName);
	NetworkSetting tmp1 = campaign.getNetworkSetting();
	if (tmp1.getTargetGoogleSearch())
	    temp1.add(allCampaignNetworks[0]);
	if (tmp1.getTargetSearchNetwork())
	    temp1.add(allCampaignNetworks[1]);
	if (tmp1.getTargetContentNetwork())
	    temp1.add(allCampaignNetworks[2]);
	if (tmp1.getTargetContentContextual())
	    temp1.add(allCampaignNetworks[3]);
	if (tmp1.getTargetPartnerSearchNetwork())
	    temp1.add(allCampaignNetworks[4]);
	String[] campNetworks = new String[temp1.size()];
	return temp1.toArray(campNetworks);
    }

    /**
     * Returns Geo Targets for given Campaign
     * 
     * @param campaignName
     * @return all Geo Targets as a String array
     * @throws Exception
     */
    public String[] getGeoTargetsAsStrings(String campaignName)
	    throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignCriterionServiceInterface campaignCriterionService = user
		.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	Selector serviceSelector = new Selector();
	String[] criterionFields = { "Id", "CriteriaType", "LocationName" };
	serviceSelector
		.setPredicates(new Predicate[] { campaignNamePredicate });
	serviceSelector.setFields(criterionFields);
	serviceSelector.setOrdering(new OrderBy[] { new OrderBy("CriteriaType",
		SortOrder.ASCENDING) });
	Paging paging = new Paging();
	paging.setNumberResults(1000);
	Integer startIndex = 0;
	try {
	    while (true) {
		paging.setStartIndex(startIndex);
		serviceSelector.setPaging(paging);
		CampaignCriterionPage campaignCriterionPage = campaignCriterionService
			.get(serviceSelector);
		for (CampaignCriterion critTemp : campaignCriterionPage
			.getEntries()) {
		    if (critTemp.getCriterion() instanceof Location) {
			Location tmp = (Location) critTemp.getCriterion();
			temp1.add(tmp.getLocationName());
		    }
		}
		startIndex = startIndex + 1000;
	    }
	} catch (NullPointerException e) {
	}
	String[] geoLocationNames = new String[temp1.size()];
	return temp1.toArray(geoLocationNames);
    }

    /**
     * Returns Language Targets for given Campaign
     * 
     * @param campaignName
     * @return all Language Targets as a String array
     * @throws Exception
     */
    public String[] getLanguageTargetsAsStrings(String campaignName)
	    throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignCriterionServiceInterface campaignCriterionService = user
		.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	Selector serviceSelector = new Selector();
	String[] criterionFields = { "Id", "CriteriaType", "LanguageName" };
	serviceSelector
		.setPredicates(new Predicate[] { campaignNamePredicate });
	serviceSelector.setFields(criterionFields);
	serviceSelector.setOrdering(new OrderBy[] { new OrderBy("CriteriaType",
		SortOrder.ASCENDING) });
	Paging paging = new Paging();
	paging.setNumberResults(1000);
	Integer startIndex = 0;
	try {
	    while (true) {
		paging.setStartIndex(startIndex);
		serviceSelector.setPaging(paging);
		CampaignCriterionPage campaignCriterionPage = campaignCriterionService
			.get(serviceSelector);
		for (CampaignCriterion critTemp : campaignCriterionPage
			.getEntries()) {
		    if (critTemp.getCriterion() instanceof Language) {
			Language tmp = (Language) critTemp.getCriterion();
			temp1.add(tmp.getName());
		    }
		}
		startIndex = startIndex + 1000;
	    }
	} catch (NullPointerException e) {
	}
	String[] langsNames = new String[temp1.size()];
	return temp1.toArray(langsNames);
    }

    /**
     * Returns Age Range Targets for given Campaign
     * 
     * @param campaignName
     * @return "AGE_RANGE_18_24", "AGE_RANGE_25_34", "AGE_RANGE_35_44",
     *         "AGE_RANGE_45_54", "AGE_RANGE_55_64", "AGE_RANGE_65_UP",
     *         "AGE_RANGE_UNDETERMINED", "UNKNOWN"
     * @throws Exception
     */
    public String[] getAgeRangeTargets(String campaignName) throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignCriterionServiceInterface campaignCriterionService = user
		.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	Selector serviceSelector = new Selector();
	String[] criterionFields = { "Id", "CriteriaType", "AgeRangeType" };
	serviceSelector
		.setPredicates(new Predicate[] { campaignNamePredicate });
	serviceSelector.setFields(criterionFields);
	serviceSelector.setOrdering(new OrderBy[] { new OrderBy("CriteriaType",
		SortOrder.ASCENDING) });
	Paging paging = new Paging();
	paging.setNumberResults(1000);
	Integer startIndex = 0;
	try {
	    while (true) {
		paging.setStartIndex(startIndex);
		serviceSelector.setPaging(paging);
		CampaignCriterionPage campaignCriterionPage = campaignCriterionService
			.get(serviceSelector);
		for (CampaignCriterion critTemp : campaignCriterionPage
			.getEntries()) {
		    if (critTemp.getCriterion() instanceof AgeRange) {
			AgeRange tmp = (AgeRange) critTemp.getCriterion();
			temp1.add(tmp.getAgeRangeType().getValue());
		    }
		}
		startIndex = startIndex + 1000;
	    }
	} catch (NullPointerException e) {
	}
	String[] langsNames = new String[temp1.size()];
	return temp1.toArray(langsNames);
    }

    /**
     * Returns Mobile Carrier Targets for given Campaign
     * 
     * @param campaignName
     * @return all Mobile Carrier Targets as a String array. Eg: ATT, ORANGE,
     *         VODAFONE
     * @throws Exception
     */
    public String[] getMobileCarrierTargets(String campaignName)
	    throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignCriterionServiceInterface campaignCriterionService = user
		.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	Selector serviceSelector = new Selector();
	String[] criterionFields = { "Id", "CriteriaType", "CarrierName" };
	serviceSelector
		.setPredicates(new Predicate[] { campaignNamePredicate });
	serviceSelector.setFields(criterionFields);
	serviceSelector.setOrdering(new OrderBy[] { new OrderBy("CriteriaType",
		SortOrder.ASCENDING) });
	Paging paging = new Paging();
	paging.setNumberResults(1000);
	Integer startIndex = 0;
	try {
	    while (true) {
		paging.setStartIndex(startIndex);
		serviceSelector.setPaging(paging);
		CampaignCriterionPage campaignCriterionPage = campaignCriterionService
			.get(serviceSelector);
		for (CampaignCriterion critTemp : campaignCriterionPage
			.getEntries()) {
		    if (critTemp.getCriterion() instanceof Carrier) {
			Carrier tmp = (Carrier) critTemp.getCriterion();
			temp1.add(tmp.getName());
		    }
		}
		startIndex = startIndex + 1000;
	    }
	} catch (NullPointerException e) {
	}
	String[] langsNames = new String[temp1.size()];
	return temp1.toArray(langsNames);
    }

    /**
     * Returns Operating System Targets for given Campaign
     * 
     * @param campaignName
     * @return "Android", "WebOS", "iOS"
     * @throws Exception
     */
    // FIXME: OS Needs some more work. Please ignore the values for now
    public String[] getOperatingSystemTargets(String campaignName)
	    throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignCriterionServiceInterface campaignCriterionService = user
		.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	Selector serviceSelector = new Selector();
	String[] criterionFields = { "Id", "CriteriaType" };
	serviceSelector
		.setPredicates(new Predicate[] { campaignNamePredicate });
	serviceSelector.setFields(criterionFields);
	serviceSelector.setOrdering(new OrderBy[] { new OrderBy("CriteriaType",
		SortOrder.ASCENDING) });
	Paging paging = new Paging();
	paging.setNumberResults(1000);
	Integer startIndex = 0;
	try {
	    while (true) {
		paging.setStartIndex(startIndex);
		serviceSelector.setPaging(paging);
		CampaignCriterionPage campaignCriterionPage = campaignCriterionService
			.get(serviceSelector);
		for (CampaignCriterion critTemp : campaignCriterionPage
			.getEntries()) {
		    if (critTemp.getCriterion() instanceof OperatingSystemVersion) {
			OperatingSystemVersion tmp = (OperatingSystemVersion) critTemp
				.getCriterion();
			temp1.add(tmp.getId().toString());
		    }
		}
		startIndex = startIndex + 1000;
	    }
	} catch (NullPointerException e) {
	}
	String[] langsNames = new String[temp1.size()];
	return temp1.toArray(langsNames);
    }

    /**
     * Returns Gender Targets for given Campaign
     * 
     * @param campaignName
     * @return "GENDER_MALE", "GENDER_FEMALE", "GENDER_UNDETERMINED"
     * @throws Exception
     */
    public String[] getGenderTargets(String campaignName) throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignCriterionServiceInterface campaignCriterionService = user
		.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	Selector serviceSelector = new Selector();
	String[] criterionFields = { "Id", "CriteriaType", "GenderType" };
	serviceSelector
		.setPredicates(new Predicate[] { campaignNamePredicate });
	serviceSelector.setFields(criterionFields);
	serviceSelector.setOrdering(new OrderBy[] { new OrderBy("CriteriaType",
		SortOrder.ASCENDING) });
	Paging paging = new Paging();
	paging.setNumberResults(1000);
	Integer startIndex = 0;
	try {
	    while (true) {
		paging.setStartIndex(startIndex);
		serviceSelector.setPaging(paging);
		CampaignCriterionPage campaignCriterionPage = campaignCriterionService
			.get(serviceSelector);
		for (CampaignCriterion critTemp : campaignCriterionPage
			.getEntries()) {
		    if (critTemp.getCriterion() instanceof Gender) {
			Gender tmp = (Gender) critTemp.getCriterion();
			temp1.add(tmp.getGenderType().getValue());
		    }
		}
		startIndex = startIndex + 1000;
	    }
	} catch (NullPointerException e) {
	}
	String[] langsNames = new String[temp1.size()];
	return temp1.toArray(langsNames);
    }

    /**
     * Returns Platform (Device) Targets for given Campaign
     * 
     * @param campaignName
     * @return "Desktop", "HighEndMobile", "Tablet"
     * @throws Exception
     */
    public String[] getPlatformTargets(String campaignName) throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignCriterionServiceInterface campaignCriterionService = user
		.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	Selector serviceSelector = new Selector();
	String[] criterionFields = { "Id", "CriteriaType", "PlatformName" };
	serviceSelector
		.setPredicates(new Predicate[] { campaignNamePredicate });
	serviceSelector.setFields(criterionFields);
	serviceSelector.setOrdering(new OrderBy[] { new OrderBy("CriteriaType",
		SortOrder.ASCENDING) });
	Paging paging = new Paging();
	paging.setNumberResults(1000);
	Integer startIndex = 0;
	try {
	    while (true) {
		paging.setStartIndex(startIndex);
		serviceSelector.setPaging(paging);
		CampaignCriterionPage campaignCriterionPage = campaignCriterionService
			.get(serviceSelector);
		for (CampaignCriterion critTemp : campaignCriterionPage
			.getEntries()) {
		    if (critTemp.getCriterion() instanceof Platform) {
			Platform tmp = (Platform) critTemp.getCriterion();
			temp1.add(tmp.getPlatformName());
		    }
		}
		startIndex = startIndex + 1000;
	    }
	} catch (NullPointerException e) {
	}
	String[] langsNames = new String[temp1.size()];
	return temp1.toArray(langsNames);
    }

    /**
     * Returns Polygon Targets for given Campaign
     * 
     * @param campaignName
     * @return any Polygon Targets as a String array
     * @throws Exception
     */
    public String[] getPolygonTargets(String campaignName) throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignCriterionServiceInterface campaignCriterionService = user
		.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	Selector serviceSelector = new Selector();
	String[] criterionFields = { "Id", "CriteriaType", "Vertices" };
	serviceSelector
		.setPredicates(new Predicate[] { campaignNamePredicate });
	serviceSelector.setFields(criterionFields);
	serviceSelector.setOrdering(new OrderBy[] { new OrderBy("CriteriaType",
		SortOrder.ASCENDING) });
	Paging paging = new Paging();
	paging.setNumberResults(1000);
	Integer startIndex = 0;
	try {
	    while (true) {
		paging.setStartIndex(startIndex);
		serviceSelector.setPaging(paging);
		CampaignCriterionPage campaignCriterionPage = campaignCriterionService
			.get(serviceSelector);
		for (CampaignCriterion critTemp : campaignCriterionPage
			.getEntries()) {
		    if (critTemp.getCriterion() instanceof Polygon) {
			Polygon tmp = (Polygon) critTemp.getCriterion();
			String geoPtsString = "";
			GeoPoint[] geoPoints = tmp.getVertices();
			for (GeoPoint geoPoint : geoPoints) {
			    geoPtsString += geoPoint
				    .getLongitudeInMicroDegrees().toString()
				    + ","
				    + geoPoint.getLongitudeInMicroDegrees()
					    .toString();
			}
			temp1.add(geoPtsString);
		    }
		}
		startIndex = startIndex + 1000;
	    }
	} catch (NullPointerException e) {
	}
	String[] langsNames = new String[temp1.size()];
	return temp1.toArray(langsNames);
    }

    /**
     * Returns Proximity Targets for given Campaign
     * 
     * @param campaignName
     * @return any Proximity Targets as a String array
     * @throws Exception
     */
    public String[] getProximityTargets(String campaignName) throws Exception {
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignCriterionServiceInterface campaignCriterionService = user
		.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);
	Predicate campaignNamePredicate = new Predicate("CampaignName",
		PredicateOperator.EQUALS, new String[] { campaignName });
	Selector serviceSelector = new Selector();
	String[] criterionFields = { "Id", "CriteriaType", "GeoPoint",
		"RadiusDistanceUnits", "RadiusInUnits", "Address" };
	serviceSelector
		.setPredicates(new Predicate[] { campaignNamePredicate });
	serviceSelector.setFields(criterionFields);
	serviceSelector.setOrdering(new OrderBy[] { new OrderBy("CriteriaType",
		SortOrder.ASCENDING) });
	Paging paging = new Paging();
	paging.setNumberResults(1000);
	Integer startIndex = 0;
	try {
	    while (true) {
		paging.setStartIndex(startIndex);
		serviceSelector.setPaging(paging);
		CampaignCriterionPage campaignCriterionPage = campaignCriterionService
			.get(serviceSelector);
		for (CampaignCriterion critTemp : campaignCriterionPage
			.getEntries()) {
		    if (critTemp.getCriterion() instanceof Proximity) {
			Proximity tmp = (Proximity) critTemp.getCriterion();
			temp1.add(tmp.getGeoPoint()
				.getLongitudeInMicroDegrees()
				+ ", "
				+ tmp.getGeoPoint().getLatitudeInMicroDegrees()
				+ " "
				+ tmp.getRadiusInUnits()
				+ "/"
				+ tmp.getRadiusDistanceUnits());
		    }
		}
		startIndex = startIndex + 1000;
	    }
	} catch (NullPointerException e) {
	}
	String[] langsNames = new String[temp1.size()];
	return temp1.toArray(langsNames);
    }

    /**
     * Returns AdSchedule Targets for given Campaign
     * 
     * @param campaignName
     * @return Ad Schedules with WEEKDAYS HOURS MINUTES info
     * @throws Exception
     */
    public String[] getAdScheduleTargets(String campaignName) throws Exception {
	bindCampaign(campaignName);
	ArrayList<String> temp1 = new ArrayList<String>();
	CampaignTargetServiceInterface campaignTargetService = user
		.getService(AdWordsService.V201109.CAMPAIGN_TARGET_SERVICE);
	CampaignTargetSelector serviceSelector = new CampaignTargetSelector();
	serviceSelector.setCampaignIds(new long[] { campaign.getId() });
	CampaignTargetPage campaignTargetPage = campaignTargetService
		.get(serviceSelector);
	for (TargetList targets : campaignTargetPage.getEntries()) {
	    if (targets instanceof AdScheduleTargetList) {
		AdScheduleTargetList tmp = (AdScheduleTargetList) targets;
		AdScheduleTarget[] tmp1 = tmp.getTargets();
		if (tmp1 != null)
		    for (AdScheduleTarget tmp2 : tmp1) {
			temp1.add(tmp2.getDayOfWeek().getValue() + ","
				+ tmp2.getStartHour() + ":"
				+ tmp2.getStartMinute() + " - "
				+ tmp2.getEndHour() + ":" + tmp2.getEndMinute());
		    }
	    }
	}
	String[] geoLocationNames = new String[temp1.size()];
	return temp1.toArray(geoLocationNames);
    }

    // TODO: Working on download reports. Eg: Cost Report
    public void downloadCostReport() {
	try {
	    AdWordsServiceLogger.log();

	    // Get AdWordsUser from "~/adwords.properties".
	    AdWordsUser user = new AdWordsUser();
	    user.setAuthToken(new AuthToken(user.getEmail(), user.getPassword())
		    .getAuthToken());

	    ReportDefinitionServiceInterface reportDefinitionService = user
		    .getService(AdWordsService.V201109.REPORT_DEFINITION_SERVICE);
	    ReportDefinitionField[] reportDefinitionFields = reportDefinitionService
		    .getReportFields(ReportDefinitionReportType.CAMPAIGN_PERFORMANCE_REPORT);
	    // Display report fields.
	    System.out.println("Available fields for report:");

	    for (ReportDefinitionField reportDefinitionField : reportDefinitionFields) {
		System.out
			.print("\t" + reportDefinitionField.getFieldName()
				+ "(" + reportDefinitionField.getFieldType()
				+ ") := [");
		if (reportDefinitionField.getEnumValues() != null) {
		    for (String enumValue : reportDefinitionField
			    .getEnumValues()) {
			System.out.print(enumValue + ", ");
		    }
		}
		System.out.println("]");
	    }

	    String fileName = File.createTempFile("reportdownload", ".report")
		    .getAbsolutePath();
	    long reportDefinitionId = Long
		    .parseLong("INSERT_REPORT_DEFINITION_ID_HERE");

	    ReportUtils.downloadReport(user, reportDefinitionId,
		    new FileOutputStream(new File(fileName)));

	    System.out.println("Report downloaded to: " + fileName);

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Gets all Groups belonging to the user given in Constructor
     * 
     * @return All Groups for the given campaign as AdGroupPage object
     * @throws Exception
     */
    public AdGroupPage getAllGroups(Campaign campaign) throws Exception {
	AdGroupServiceInterface adGroupService = user
		.getService(AdWordsService.V201109.ADGROUP_SERVICE);

	Selector selector = new Selector();
	selector.setFields(new String[] { "Id", "Name" });
	selector.setOrdering(new OrderBy[] { new OrderBy("Name",
		SortOrder.ASCENDING) });

	Predicate campaignIdPredicate = new Predicate("CampaignId",
		PredicateOperator.IN, new String[] { campaign.getId()
			.toString() });
	selector.setPredicates(new Predicate[] { campaignIdPredicate });

	AdGroupPage page = adGroupService.get(selector);
	return page;
    }

    /**
     * Get all the videos used for Template Ads
     * 
     * @return MediaPage with all Media
     * @throws Exception
     */
    public MediaPage getAllVideos() throws Exception {
	MediaServiceInterface mediaService = user
		.getService(AdWordsService.V201109.MEDIA_SERVICE);

	// Create selector.
	Selector selector = new Selector();
	selector.setFields(new String[] { "MediaId", "Name" });
	selector.setOrdering(new OrderBy[] { new OrderBy("MediaId",
		SortOrder.ASCENDING) });

	// Create predicates.
	Predicate typePredicate = new Predicate("Type", PredicateOperator.IN,
		new String[] { "VIDEO" });
	selector.setPredicates(new Predicate[] { typePredicate });

	// Get all videos.
	MediaPage page = mediaService.get(selector);

	// Display videos.
	if (page.getEntries() != null) {
	    for (Media video : page.getEntries()) {
		System.out.println("Video with id '" + video.getMediaId()
			+ "' and name '" + video.getName() + "' was found.");
	    }
	} else {
	    System.out.println("No videos were found.");
	}
	return page;
    }

    /**
     * Add a Campaign with the given values
     * 
     * @param campaignName
     *            : Name of the Campaign
     * @param campaignStatus
     *            : Status - CampaignStatus.PAUSED, CampaignStatus.ACTIVE,
     *            CampaignStatus.DELETED
     * @param microBudget
     *            : Budget in microLong format. Eg: $50.00 = 50000000L
     * @param budgetPeriod
     *            : BudgetBudgetPeriod.DAILY, BudgetBudgetPeriod.MONTHLY
     * @param biddingStrategy
     *            : BudgetOptimizer, ConversionOptimizer, ManualCPC, ManualCPM,
     *            PercentCPA
     * @param targetGoogleSearch
     *            : Google Search network target
     * @param targetSearchNetwork
     *            : Search Network. Requires Google Search
     * @param targetContentNetwork
     *            : Content Network
     * @param targetContentContextual
     *            : Content Contextual. Required Content Network
     * @param targetPartnerSearchNetwork
     *            : Google Partner Search Network
     * @return Campaign object
     * @throws Exception
     */
    public Campaign addCampaign(String campaignName,
	    CampaignStatus campaignStatus, Long microBudget,
	    BudgetBudgetPeriod budgetPeriod, BiddingStrategy biddingStrategy,
	    boolean targetGoogleSearch, boolean targetSearchNetwork,
	    boolean targetContentNetwork, boolean targetContentContextual,
	    boolean targetPartnerSearchNetwork) throws Exception {
	Campaign newCampaign = null;
	// Create campaign.
	Campaign campaign = new Campaign();
	campaign.setName(campaignName);
	campaign.setStatus(campaignStatus);
	campaign.setBiddingStrategy(biddingStrategy);

	// Create budget.
	Budget budget = new Budget();
	budget.setPeriod(budgetPeriod);
	budget.setAmount(new Money(null, microBudget));
	budget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);
	campaign.setBudget(budget);

	// Set the campaign network options to Search and Search Network.
	NetworkSetting networkSetting = new NetworkSetting();
	networkSetting.setTargetGoogleSearch(targetGoogleSearch);
	networkSetting.setTargetSearchNetwork(targetSearchNetwork);
	networkSetting.setTargetContentNetwork(targetContentNetwork);
	networkSetting
		.setTargetPartnerSearchNetwork(targetPartnerSearchNetwork);
	networkSetting.setTargetContentContextual(targetContentContextual);
	campaign.setNetworkSetting(networkSetting);

	// Create operations.
	CampaignOperation operation = new CampaignOperation();
	operation.setOperand(campaign);
	operation.setOperator(Operator.ADD);

	CampaignOperation[] operations = new CampaignOperation[] { operation };

	// Add campaign.
	CampaignReturnValue result = campaignService.mutate(operations);

	// Display campaigns.
	if (result != null && result.getValue() != null) {
	    for (Campaign campaignResult : result.getValue()) {
		System.out.println("Campaign with name \""
			+ campaignResult.getName() + "\" and id \""
			+ campaignResult.getId() + "\" was added.");
	    }
	    newCampaign = result.getValue()[0];
	} else {
	    System.out.println("No campaigns were added.");
	}
	return newCampaign;
    }

    /**
     * Creates a Ad Group under the given Campaign with values provided
     * 
     * @param campaign
     *            : Campaign object
     * @param adGroupName
     *            : Ad Group Name
     * @param adGroupStatus
     *            : AdGroupStatus.ENABLED, AdGroupStatus.PAUSED,
     *            AdGroupStatus.DELETED
     * @param maxCPC
     *            : Group bid in microLong format. Eg: $10.00 = 10000000L
     * @return Ad Group object
     * @throws Exception
     */
    public AdGroup addAdGroup(Campaign campaign, String adGroupName,
	    AdGroupStatus adGroupStatus, Long maxCPC) throws Exception {
	AdGroup newAdGroup = null;
	AdGroupServiceInterface adGroupService = user
		.getService(AdWordsService.V201109.ADGROUP_SERVICE);

	// Create ad group.
	AdGroup adGroup = new AdGroup();
	adGroup.setName(adGroupName);
	adGroup.setStatus(adGroupStatus);
	adGroup.setCampaignId(campaign.getId());

	// Create ad group bid.
	ManualCPCAdGroupBids adGroupBids = new ManualCPCAdGroupBids();
	adGroupBids.setKeywordMaxCpc(new Bid(new Money(null, maxCPC)));
	adGroup.setBids(adGroupBids);

	// Create operations.
	AdGroupOperation operation = new AdGroupOperation();
	operation.setOperand(adGroup);
	operation.setOperator(Operator.ADD);

	AdGroupOperation[] operations = new AdGroupOperation[] { operation };

	// Add ad group.
	AdGroupReturnValue result = adGroupService.mutate(operations);

	if (result != null && result.getValue() != null) {
	    for (AdGroup adGroupResult : result.getValue()) {
		System.out.println("Ad Group with name \""
			+ adGroupResult.getName() + "\" and id \""
			+ adGroupResult.getId() + "\" was added.");
	    }
	    newAdGroup = result.getValue()[0];
	} else {
	    System.out.println("No ad groups were added.");
	}
	return newAdGroup;
    }

    /**
     * Use this method to create a Campaign with default values as below
     * DEFAULT: Status - PAUSED, Budget - $50.00 Budget Period - DAILY, DEFAULT:
     * Network - Search only, Bidding Strategy - Manual CPC
     * 
     * @param campaignName
     * @return
     * @throws Exception
     */
    public Campaign addCampaign(String campaignName) throws Exception {
	Campaign newCampaign = null;
	// Create campaign.
	Campaign campaign = new Campaign();
	campaign.setName(campaignName);
	campaign.setStatus(CampaignStatus.PAUSED);
	campaign.setBiddingStrategy(new ManualCPC());

	// Create budget.
	Budget budget = new Budget();
	budget.setPeriod(BudgetBudgetPeriod.DAILY);
	budget.setAmount(new Money(null, 50000000L));
	budget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);
	campaign.setBudget(budget);

	// Set the campaign network options to Search and Search Network.
	NetworkSetting networkSetting = new NetworkSetting();
	networkSetting.setTargetGoogleSearch(true);
	networkSetting.setTargetSearchNetwork(true);
	networkSetting.setTargetContentNetwork(true);
	networkSetting.setTargetPartnerSearchNetwork(false);
	networkSetting.setTargetContentContextual(true);
	campaign.setNetworkSetting(networkSetting);

	// Create operations.
	CampaignOperation operation = new CampaignOperation();
	operation.setOperand(campaign);
	operation.setOperator(Operator.ADD);

	CampaignOperation[] operations = new CampaignOperation[] { operation };

	// Add campaign.
	CampaignReturnValue result = campaignService.mutate(operations);

	if (result != null && result.getValue() != null) {
	    for (Campaign campaignResult : result.getValue()) {
		System.out.println("Campaign with name \""
			+ campaignResult.getName() + "\" and id \""
			+ campaignResult.getId() + "\" was added.");
	    }
	    newCampaign = result.getValue()[0];
	} else {
	    System.out.println("No campaigns were added.");
	}
	return newCampaign;
    }

    /**
     * Use this method to create a Ad Group in a Campaign with default values as
     * mentioned below. DEFAULT: Status: Enabled, Max CPC=$10.00
     * 
     * @param campaign
     *            : Campaign under which group is created
     * @param adGroupName
     *            : Name of the Ad Group
     * @return AdGroup object
     * @throws Exception
     */
    public AdGroup addAdGroup(Campaign campaign, String adGroupName)
	    throws Exception {
	AdGroup newAdGroup = null;
	AdGroupServiceInterface adGroupService = user
		.getService(AdWordsService.V201109.ADGROUP_SERVICE);

	// Create ad group.
	AdGroup adGroup = new AdGroup();
	adGroup.setName(adGroupName);
	adGroup.setStatus(AdGroupStatus.ENABLED);
	adGroup.setCampaignId(campaign.getId());

	// Create ad group bid.
	ManualCPCAdGroupBids adGroupBids = new ManualCPCAdGroupBids();
	adGroupBids.setKeywordMaxCpc(new Bid(new Money(null, 10000000L)));
	adGroup.setBids(adGroupBids);

	// Create operations.
	AdGroupOperation operation = new AdGroupOperation();
	operation.setOperand(adGroup);
	operation.setOperator(Operator.ADD);

	AdGroupOperation[] operations = new AdGroupOperation[] { operation };

	// Add ad group.
	AdGroupReturnValue result = adGroupService.mutate(operations);
	if (result != null && result.getValue() != null) {
	    for (AdGroup adGroupResult : result.getValue()) {
		System.out.println("Ad Group with name \""
			+ adGroupResult.getName() + "\" and id \""
			+ adGroupResult.getId() + "\" was added.");
	    }
	    newAdGroup = result.getValue()[0];
	} else {
	    System.out.println("No ad groups were added.");
	}

	return newAdGroup;
    }

    /**
     * Use this method to create different types of ADs in a Group. Ask
     * btellakula if you need this implementation. Until then, this is P2 and
     * used as is.
     * 
     * @param adGroup
     * @return AdGroupAd object with all the ads created
     * @throws Exception
     */
    public AdGroupAd[] addSampleAdGroupAd(AdGroup adGroup) throws Exception {
	AdGroupAd[] newAds = null;
	// Get the AdGroupAdService.
	AdGroupAdServiceInterface adGroupAdService = user
		.getService(AdWordsService.V201109.ADGROUP_AD_SERVICE);
	AdGroupAdOperation[] operations;

	// Create text ad.
	TextAd textAd = new TextAd();
	textAd.setHeadline("Auto - TextAd ");
	textAd.setDescription1("Description Line 1");
	textAd.setDescription2("Description Line 2");
	textAd.setDisplayUrl("www.pps.com");
	textAd.setUrl("http://www.pps.com");

	// Create ad group ad.
	AdGroupAd textAdGroupAd = new AdGroupAd();
	textAdGroupAd.setAdGroupId(adGroup.getId());
	textAdGroupAd.setAd(textAd);

	// Create image ad.
	ImageAd imageAd = new ImageAd();
	imageAd.setName("Auto - ImageAd " + System.currentTimeMillis());
	imageAd.setDisplayUrl("www.pps.com");
	imageAd.setUrl("http://pps.com");

	// Create image.
	Image image = new Image();
	image.setData(ImageUtils.getImageDataFromUrl("http://goo.gl/g09EP"));
	imageAd.setImage(image);

	// Create ad group ad.
	AdGroupAd imageAdGroupAd = new AdGroupAd();
	imageAdGroupAd.setAdGroupId(adGroup.getId());
	imageAdGroupAd.setAd(imageAd);

	// Create operations.
	AdGroupAdOperation textAdGroupAdOperation = new AdGroupAdOperation();
	textAdGroupAdOperation.setOperand(textAdGroupAd);
	textAdGroupAdOperation.setOperator(Operator.ADD);

	AdGroupAdOperation imageAdGroupAdOperation = new AdGroupAdOperation();
	imageAdGroupAdOperation.setOperand(imageAdGroupAd);
	imageAdGroupAdOperation.setOperator(Operator.ADD);

	// Create template ad, using the Click to Play Video template (id 9).
	MediaPage allVideos = getAllVideos();
	if (allVideos.getEntries() != null) {
	    TemplateAd templateAd = new TemplateAd();
	    templateAd.setTemplateId(9L);
	    templateAd.setDimensions(new Dimensions(300, 250));
	    templateAd.setName("Auto - TemplateAd "
		    + System.currentTimeMillis());
	    templateAd.setDisplayUrl("www.pps.com");
	    templateAd.setUrl("http://www.pps.com");

	    // Create template ad data.
	    long videoMediaId = allVideos.getEntries()[0].getMediaId();
	    Image startImage = new Image();
	    startImage.setData(ImageUtils
		    .getImageDataFromUrl("http://goo.gl/g09EP"));
	    startImage.setType(MediaMediaType.IMAGE);
	    Video video = new Video();
	    video.setMediaId(videoMediaId);
	    video.setType(MediaMediaType.VIDEO);

	    templateAd
		    .setTemplateElements(new TemplateElement[] { new TemplateElement(
			    "adData", new TemplateElementField[] {
				    new TemplateElementField("startImage",
					    TemplateElementFieldType.IMAGE,
					    null, startImage),
				    new TemplateElementField("displayUrlColor",
					    TemplateElementFieldType.ENUM,
					    "#ffffff", null),
				    new TemplateElementField("video",
					    TemplateElementFieldType.VIDEO,
					    null, video) }) });

	    // Create ad group ad.
	    AdGroupAd templateAdGroupAd = new AdGroupAd();
	    templateAdGroupAd.setAdGroupId(adGroup.getId());
	    templateAdGroupAd.setAd(templateAd);

	    AdGroupAdOperation templateAdGroupAdOperation = new AdGroupAdOperation();
	    templateAdGroupAdOperation.setOperand(templateAdGroupAd);
	    templateAdGroupAdOperation.setOperator(Operator.ADD);
	    operations = new AdGroupAdOperation[] { textAdGroupAdOperation,
		    imageAdGroupAdOperation, templateAdGroupAdOperation };
	} else {
	    operations = new AdGroupAdOperation[] { textAdGroupAdOperation,
		    imageAdGroupAdOperation };
	}

	// Add ads.
	AdGroupAdReturnValue result = adGroupAdService.mutate(operations);

	if (result != null && result.getValue() != null) {
	    for (AdGroupAd adGroupAdResult : result.getValue()) {
		System.out.println("Ad with id  \""
			+ adGroupAdResult.getAd().getId() + "\""
			+ " and type \"" + adGroupAdResult.getAd().getAdType()
			+ "\" was added.");
	    }
	    newAds = result.getValue();
	} else {
	    System.out.println("No ads were added.");
	}
	return newAds;
    }

    /**
     * Creates a Placement in the given adGroup
     * 
     * @param adGroup
     *            : Group to add
     * @param placementUrl
     *            : URL for the placement
     * @return Placement object
     * @throws Exception
     */
    public Placement addAdGroupPlacement(AdGroup adGroup, String placementUrl)
	    throws Exception {
	Placement newPlacement = null;
	AdGroupCriterionServiceInterface adGroupCriterionService = user
		.getService(AdWordsService.V201109.ADGROUP_CRITERION_SERVICE);

	// Create placement.
	Placement placement = new Placement();
	placement.setUrl(placementUrl);

	// Create biddable ad group criterion for placement.
	BiddableAdGroupCriterion placementBiddableAdGroupCriterion = new BiddableAdGroupCriterion();
	placementBiddableAdGroupCriterion.setAdGroupId(adGroup.getId());
	placementBiddableAdGroupCriterion.setCriterion(placement);

	// Create operations.
	AdGroupCriterionOperation placementAdGroupCriterionOperation = new AdGroupCriterionOperation();
	placementAdGroupCriterionOperation
		.setOperand(placementBiddableAdGroupCriterion);
	placementAdGroupCriterionOperation.setOperator(Operator.ADD);

	AdGroupCriterionOperation[] operations = new AdGroupCriterionOperation[] { placementAdGroupCriterionOperation };

	// Add placement.
	AdGroupCriterionReturnValue result = adGroupCriterionService
		.mutate(operations);

	// Display ad group criteria.
	if (result != null && result.getValue() != null) {
	    for (AdGroupCriterion adGroupCriterionResult : result.getValue()) {
		newPlacement = (Placement) adGroupCriterionResult
			.getCriterion();
		System.out.println("Placement with url \""
			+ newPlacement.getUrl() + "\" and id \""
			+ newPlacement.getId() + "\" was added.");
	    }
	} else {
	    System.out.println("No ad group criteria were added.");
	}
	return newPlacement;
    }

    /**
     * Creates a keyword in the given adGroup
     * 
     * @param adGroup
     *            : Group to add
     * @param keywordText
     *            : keyword text
     * @param keywordMatchType
     *            : BROAD, EXACT, PHRASE
     * @return Keyword object
     * @throws Exception
     */
    public Keyword addAdGroupKeyword(AdGroup adGroup, String keywordText,
	    KeywordMatchType keywordMatchType) throws Exception {
	Keyword newKeyword = null;
	AdGroupCriterionServiceInterface adGroupCriterionService = user
		.getService(AdWordsService.V201109.ADGROUP_CRITERION_SERVICE);

	// Create keyword.
	Keyword keyword = new Keyword();
	keyword.setText(keywordText);
	keyword.setMatchType(keywordMatchType);

	// Create biddable ad group criterion.
	BiddableAdGroupCriterion keywordBiddableAdGroupCriterion = new BiddableAdGroupCriterion();
	keywordBiddableAdGroupCriterion.setAdGroupId(adGroup.getId());
	keywordBiddableAdGroupCriterion.setCriterion(keyword);

	// Create operations.
	AdGroupCriterionOperation keywordAdGroupCriterionOperation = new AdGroupCriterionOperation();
	keywordAdGroupCriterionOperation
		.setOperand(keywordBiddableAdGroupCriterion);
	keywordAdGroupCriterionOperation.setOperator(Operator.ADD);

	AdGroupCriterionOperation[] operations = new AdGroupCriterionOperation[] { keywordAdGroupCriterionOperation };

	// Add keyword.
	AdGroupCriterionReturnValue result = adGroupCriterionService
		.mutate(operations);

	if (result != null && result.getValue() != null) {
	    for (AdGroupCriterion adGroupCriterionResult : result.getValue()) {
		newKeyword = (Keyword) adGroupCriterionResult.getCriterion();
		System.out.println("Keyword with text \""
			+ newKeyword.getText() + "\" and id \""
			+ newKeyword.getId() + "\" was added.");
	    }
	} else {
	    System.out.println("No ad group criteria were added.");
	}
	return newKeyword;
    }
}
