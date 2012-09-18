package com.marin.PubAPI.facebook;

/*
 * This is facebook service which has getters that parse the JSON response
 * */
import java.util.HashMap;
import java.util.Map;

import com.marin.PubAPI.facebook.FbResponseObject.Bid_Info;
import com.marin.PubAPI.facebook.FbResponseObject.Conversion_Specs;
import com.marin.PubAPI.facebook.FbResponseObject.FbAllCampaingsResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbAllGroupsResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbCampaignResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbCreativeResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbErrorResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbGroupResponseObject;
import com.marin.PubAPI.facebook.FbResponseObject.FbPaging;
import com.marin.PubAPI.facebook.FbResponseObject.Work_Networks;

public class FacebookService extends AbstractJSONRestService {

    // public FacebookClientAccount fca;

    Map<String, String> campaignNameToId = new HashMap<String, String>();

    public Map<String, String> mapCampaignNameToIdFromResponse()
	    throws Exception {

	// this get all the campaigns from fb and
	// store Name Id map for future retrival
	FbAllCampaingsResponseObject response = doAFacebookGetAllCampaigns(accountId);
	int count = getCount(response);

	for (int j = 0; j < count; j++) {
	    campaignNameToId.put(getCampaignNameFromData(response, j),
		    getCampaignIdFromData(response, j));

	}
	return campaignNameToId;
    }

    Map<String, String> groupNameToId = new HashMap<String, String>();

    public Map<String, String> mapGroupNameToIdFromResponse() throws Exception {

	// get the groups in Incremental of 1000 i.e the limit
	FbAllGroupsResponseObject response = doAFacebookGetAllGroups(accountId,
		0, 500);
	int count = Integer.parseInt(getCount(response));
	int limit = Integer.parseInt(getLimit(response));
	int offset = Integer.valueOf(getOffset(response));

	while (offset <= count) {
	    for (int j = 0; j < (count - offset) && j < limit; j++) {

		String gName = getGroupNameFromData(response, j);
		String gId = getGroupIdFromData(response, j);
		groupNameToId.put(gName, gId);

	    }

	    response = doAFacebookGetAllGroups(accountId, offset + limit, limit);
	    offset = Integer.valueOf(getOffset(response));
	    // log.info("current_group_Count " + groupNameToId.size());
	}

	// log.info("total no of groups " + groupNameToId.size());
	return groupNameToId;
    }

    public FacebookService() throws Exception {

    }

    public String getCampaignNameFromCampaignId(
	    FbCampaignResponseObject response, String campaignId) {

	return response.name;
    }

    public String getCampaignName(FbCampaignResponseObject response) {

	return response.name;
    }

    public String getCampaignId(FbCampaignResponseObject response) {

	return response.campaign_id;
    }

    public String getAccountId(FbCampaignResponseObject response) {

	return response.account_id;
    }

    public double getDailyBudget(FbCampaignResponseObject response) {

	return response.daily_budget / 100;
    }

    public int getCampaignStatus(FbCampaignResponseObject response) {

	return response.campaign_status;
    }

    public int getDailyImps(FbCampaignResponseObject response) {

	return response.daily_imps;
    }

    public long getStartTime(FbCampaignResponseObject response) {

	return response.start_time;
    }

    public long getEndTime(FbCampaignResponseObject response) {

	return response.end_time;
    }

    public long getUpdatedTime(FbCampaignResponseObject response) {

	return response.updated_time;
    }

    // ////////////////////////////////////////////////////////////////////////////////

    public FbGroupResponseObject[] getData(FbAllGroupsResponseObject response) {

	return response.data;

    }

    public String getGroupId(FbGroupResponseObject response) {

	return response.adgroup_id;
    }

    public String getAdId(FbGroupResponseObject response) {

	return response.ad_id;
    }

    public String getCampaignId(FbGroupResponseObject response) {

	return response.campaign_id;
    }

    public String getGroupName(FbGroupResponseObject response) {

	return response.name;
    }

    public int getGroupStatus(FbGroupResponseObject response) {

	return response.adgroup_status;
    }

    public int getBidType(FbGroupResponseObject response) {

	return response.bid_type;
    }

    public String getMaxBid(FbGroupResponseObject response) {

	return response.max_bid;
    }

    public Bid_Info getBidInfo(FbGroupResponseObject response) {

	return response.bid_info;
    }

    public String[] getDisapproveReasonDescriptions(
	    FbGroupResponseObject response) {

	return response.disapprove_reason_descriptions;
    }

    public int getStatus(FbGroupResponseObject response) {

	return response.ad_status;
    }

    public String getAccountId(FbGroupResponseObject response) {

	return response.account_id;
    }

    public String getId(FbGroupResponseObject response) {

	return response.id;
    }

    public String[] getCreativeIds(FbGroupResponseObject response) {

	return response.creative_ids;
    }

    public int[] getGenderFromTargeting(FbGroupResponseObject response) {

	return response.targeting.genders;
    }

    public int getMaxAgeFromTargeting(FbGroupResponseObject response) {

	return response.targeting.age_max;
    }

    public int getMinAgeFromTargeting(FbGroupResponseObject response) {

	return response.targeting.age_min;
    }

    public String[] getZipsFromTargeting(FbGroupResponseObject response) {

	return response.targeting.zips;
    }

    public String[] getCountriesFromTargeting(FbGroupResponseObject response) {

	return response.targeting.countries;
    }

    public String[] getCitiesFromTargeting(FbGroupResponseObject response) {

	return response.targeting.cities;
    }

    public String[] getKeywordsFromTargeting(FbGroupResponseObject response) {

	return response.targeting.keywords;
    }

    public int[] getRelationshipStatusesFromTargeting(
	    FbGroupResponseObject response) {

	return response.targeting.relationship_statuses;
    }

    public int[] getInterestedInFromTargeting(FbGroupResponseObject response) {

	return response.targeting.interested_in;
    }

    public Work_Networks[] getWorkNetworksFromTargeting(
	    FbGroupResponseObject response) {

	return response.targeting.work_networks;
    }

    public String[] getLocalesFromTargeting(FbGroupResponseObject response) {

	return response.targeting.locales;
    }

    public Conversion_Specs[] getConversionSpecs(FbGroupResponseObject response) {

	return response.conversion_specs;
    }

    public long getStartTime(FbGroupResponseObject response) {

	return response.start_time;
    }

    public long getEndTime(FbGroupResponseObject response) {

	return response.end_time;
    }

    public long getUpdatedTime(FbGroupResponseObject response) {

	return response.updated_time;
    }

    public String getCount(FbAllGroupsResponseObject response) {

	return response.count;

    }

    public String getLimit(FbAllGroupsResponseObject response) {

	return response.limit;

    }

    public String getOffset(FbAllGroupsResponseObject response) {

	return response.offset;

    }

    public String getincludeDeleted(FbAllGroupsResponseObject response) {

	return response.include_deleted;

    }

    // ///////////////////////////////////////////////////////////////////////

    public String getViewTag(FbCreativeResponseObject response) {

	return response.view_tag;
    }

    public String[] getAltViewTag(FbCreativeResponseObject response) {

	return response.alt_view_tags;
    }

    public String getCreativeId(FbCreativeResponseObject response) {

	return response.creative_id;
    }

    public String getType(FbCreativeResponseObject response) {

	return response.type;
    }

    public String getTitle(FbCreativeResponseObject response) {

	return response.title;
    }

    public String getBody(FbCreativeResponseObject response) {

	return response.body;
    }

    public String getImageHash(FbCreativeResponseObject response) {

	return response.image_hash;
    }

    public String getLinkUrl(FbCreativeResponseObject response) {

	return response.link_url;
    }

    public String getRelatedFanPage(FbCreativeResponseObject response) {

	return response.related_fan_page;
    }

    public String getCreativeName(FbCreativeResponseObject response) {

	return response.name;
    }

    public String getRunStatus(FbCreativeResponseObject response) {

	return response.run_status;
    }

    public String getPreviewUrl(FbCreativeResponseObject response) {

	return response.preview_url;
    }

    public String getImageUrl(FbCreativeResponseObject response) {

	return response.image_url;
    }

    public String getCountCurrentAdgroups(FbCreativeResponseObject response) {

	return response.count_current_adgroups;
    }

    // //////////////////////
    public String getErrorMessage(FbErrorResponseObject response) {

	return response.message;
    }

    public String getErrorType(FbErrorResponseObject response) {

	return response.type;
    }

    public String getErrorCode(FbErrorResponseObject response) {

	return response.code;
    }

    // ////////////////////////////////////////////////////////////////////

    public FbCampaignResponseObject[] getData(
	    FbAllCampaingsResponseObject response) {

	return response.data;

    }

    public int getCount(FbAllCampaingsResponseObject response) {

	return response.count;

    }

    public int getLimit(FbAllCampaingsResponseObject response) {

	return response.limit;

    }

    public String getOffset(FbAllCampaingsResponseObject response) {

	return response.offset;

    }

    public FbPaging getPaging(FbAllCampaingsResponseObject response) {

	return response.paging;

    }

    // //////////////////////////////////////////////////////////////////////////////

    public String getGroupNameFromData(FbAllGroupsResponseObject response, int i) {

	return this.getGroupName((response.data)[i]);
    }

    public String getGroupIdFromData(FbAllGroupsResponseObject response, int i) {

	return this.getGroupId((response.data)[i]);
    }

    public int getGroupnStatusFromData(FbAllGroupsResponseObject response, int i) {

	return this.getGroupStatus((response.data)[i]);
    }

    public String getMaxBidFromData(FbAllGroupsResponseObject response, int i) {

	return this.getMaxBid((response.data)[i]);
    }

    public String[] getDisapproveReasonDescriptionsFromData(
	    FbAllGroupsResponseObject response, int i) {

	return this.getDisapproveReasonDescriptions((response.data)[i]);
    }

    public String[] getcreativeIdsFromData(FbAllGroupsResponseObject response,
	    int i) {

	return this.getCreativeIds((response.data)[i]);
    }

    public int[] getGenderTargetingFromData(FbAllGroupsResponseObject response,
	    int i) {

	return this.getGenderFromTargeting((response.data)[i]);
    }

    public int getMaxAgeTargetingFromData(FbAllGroupsResponseObject response,
	    int i) {

	return this.getMaxAgeFromTargeting((response.data)[i]);
    }

    public int getMinAgeTargetingFromData(FbAllGroupsResponseObject response,
	    int i) {

	return this.getMinAgeFromTargeting((response.data)[i]);
    }

    public String[] getZipsTargetingFromData(
	    FbAllGroupsResponseObject response, int i) {

	return this.getZipsFromTargeting((response.data)[i]);
    }

    public String[] getCountriesTargetingFromData(
	    FbAllGroupsResponseObject response, int i) {

	return this.getCountriesFromTargeting((response.data)[i]);
    }

    public String[] getKeywordsTargetingFromData(
	    FbAllGroupsResponseObject response, int i) {

	return this.getKeywordsFromTargeting((response.data)[i]);
    }

    public String[] getRelationshipStatusTargetingFromData(
	    FbAllGroupsResponseObject response, int i) {

	return this.getCountriesFromTargeting((response.data)[i]);
    }

    public int[] getInterestedInTargetingFromData(
	    FbAllGroupsResponseObject response, int i) {

	return this.getInterestedInFromTargeting((response.data)[i]);
    }

    public Work_Networks[] getWorkNetworksTargetingFromData(
	    FbAllGroupsResponseObject response, int i) {

	return this.getWorkNetworksFromTargeting((response.data)[i]);
    }

    public String[] getLocalesTargetingFromData(
	    FbAllGroupsResponseObject response, int i) {

	return this.getLocalesFromTargeting((response.data)[i]);
    }

    // //////////////////////////////////////////////////////////////////////////////
    public String getAccountIdFromData(FbAllCampaingsResponseObject response,
	    int i) {

	return this.getAccountId((response.data)[i]);
    }

    public String getCampaignIdFromData(FbAllCampaingsResponseObject response,
	    int i) {

	return this.getCampaignId((response.data)[i]);
    }

    public String getCampaignNameFromData(
	    FbAllCampaingsResponseObject response, int i) {

	return this.getCampaignName((response.data)[i]);
    }

    public double getCampaignBudgetFromData(
	    FbAllCampaingsResponseObject response, int i) {

	return this.getDailyBudget((response.data)[i]);
    }

    public long getCampaignStatusFromData(
	    FbAllCampaingsResponseObject response, int i) {

	return this.getCampaignStatus((response.data)[i]);
    }

    public int getCampaignDailyImprFromData(
	    FbAllCampaingsResponseObject response, int i) {

	return this.getDailyImps((response.data)[i]);
    }

    public long getCampaignStartTimeFromData(
	    FbAllCampaingsResponseObject response, int i) {

	return this.getStartTime((response.data)[i]);
    }

    public long getCampaignEndTimeFromData(
	    FbAllCampaingsResponseObject response, int i) {

	return this.getEndTime((response.data)[i]);
    }

    public long getCampaignUpdatedTimeFromData(
	    FbAllCampaingsResponseObject response, int i) {

	return this.getUpdatedTime((response.data)[i]);
    }

}
