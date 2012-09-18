package com.marin.PubAPI.facebook;

public class FbResponseObject {

    public class FbAllCampaingsResponseObject {

	public FbCampaignResponseObject[] data;
	public int count;
	public int limit;
	public String offset;
	public FbPaging paging;

    }

    public class FbPaging {

	String next;

    }

    public class FbCampaignResponseObject {

	public String account_id;
	public String campaign_id;
	public String name;
	public double daily_budget;
	public int campaign_status;
	public int daily_imps;
	public String id;
	public long start_time;
	public long end_time;
	public long updated_time;

    }

    public class FbAllGroupsResponseObject {

	public FbGroupResponseObject[] data;
	public String count;
	public String limit;
	public String offset;
	public String include_deleted;
	public Paging paging;

    }

    public class FbGroupResponseObject {

	public String adgroup_id;
	public String ad_id;
	public String campaign_id;
	public String name;
	public int adgroup_status;
	public int bid_type;
	public String max_bid;
	public Bid_Info bid_info;
	public String[] disapprove_reason_descriptions;
	public int ad_status;
	public String[] locations;
	public Impression_Control_Map[] impression_control_map;
	public String account_id;
	public String id;
	public String[] creative_ids;
	public Targeting targeting;
	public Conversion_Specs[] conversion_specs;
	public long start_time;
	public long end_time;
	public long created_time;
	public long updated_time;

    }

    public class Impression_Control_Map {

	int location;
	Control control;
    }

    public class Control {

	int impression_control_type;
	int user_impression_limit;
	int user_impression_limit_period;
	int user_impression_limit_period_unit;
    }

    public class Bid_Info {

	public String bid_info_code;
	public String bid_value; // need to fix this

    }

    public class Targeting {

	public int[] genders;
	public int age_max;
	public int age_min;
	public String[] zips;
	public Region[] regions;
	public String[] countries;
	public long broad_age;
	public String[] cities;
	public String[] keywords;
	public int[] relationship_statuses;
	public int[] interested_in;
	public Work_Networks[] work_networks;
	public College_Networks[] college_networks;
	public String[] college_majors;
	public String[] education_statuses;
	public String[] locales;

    }

    public class Region {

	public String id;
	public String name;

    }

    public class City {

	public String id;
	public String name;

    }

    public class Work_Networks {

	public String id;
	public String name;

    }

    public class College_Networks {

	public String id;
	public String name;

    }

    public class Paging {

	public String next;
    }

    public class Conversion_Specs {

	public String[] action_type; // need to fix this
	public long page;

    }

    public class FbAllCreativesResponseObject {

	public FbCreativeResponseObject[] data;
	public int count;
	public int limit;
	public String offset;
	public FbPaging paging;

    }

    public class FbCreativeResponseObject {
	public String view_tag;
	public String[] alt_view_tags;
	public String creative_id;
	public String type;
	public String title;
	public String body;
	public String image_hash;
	public String link_url;
	public String related_fan_page;
	public String name;
	public String run_status;
	public String preview_url;
	public String count_current_adgroups;
	public String id;
	public String image_url;
	public Action_Spec action_spec;

    }

    public class Action_Spec {

	public String page;
	public String action_type;

    }

    public class FbErrorResponseObject {

	String message;
	String type;
	String code;

    }

}