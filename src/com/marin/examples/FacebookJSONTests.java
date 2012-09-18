
package com.marin.examples;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class FacebookJSONTests {

	static Logger log = Logger.getLogger(FacebookJSONTests.class);

	// public class FacebookResponse {
	// public String account_id;
	// public String campaign_id;
	// public String name;
	// public double dailyBudget;
	// public int campaign_Status;
	// public int daily_imps;
	// public String id;
	// public long start_time;
	// public long end_time;
	// public long updated_time;
	// }
	//
	// public static void parseJSONResponse() {
	//
	// String JSONResponseText =
	// "{'account_id': 72047375,'campaign_id': 6003981785807,'name': '5_16_Campaign'"
	// +
	// " 'daily_budget': 300,'campaign_status': 3, 'daily_imps': 0, 'id': '6003981785807', "
	// +
	// "'start_time': 1337188906, 'end_time': null, 'updated_time': 1339005036 }";
	//
	// Gson gson = new GsonBuilder().create();
	// FacebookResponse fbResponse = gson.fromJson(JSONResponseText,
	// FacebookResponse.class);
	// String campainName = fbResponse.name;
	// log.info(campainName);
	//
	// }
	//
	// public static void main() {
	//
	// parseJSONResponse();
	//
	// }

	public static void StringReverse(String str) {

		int len = str.length();

		String retStr = "";
		for (int i = len - 1; i >= 0; i--) {
			retStr += str.charAt(i);
		}
		log.info(retStr);

	}

	@SuppressWarnings("unchecked")
	public static void encodeJSON() {

		JSONObject obj = new JSONObject();
		obj.put("name", "foo");
		obj.put("num", new Integer(100));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));
		obj.put("nickname", null);
		System.out.print(obj);

	}

	@SuppressWarnings("unchecked")
	public static void encodeJSONUsingLinkedListHashMap() {

		@SuppressWarnings("rawtypes")
		Map obj = new LinkedHashMap();
		obj.put("name", "foo");
		obj.put("num", new Integer(100));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));
		obj.put("nickname", null);
		String jsonText = JSONValue.toJSONString(obj);
		System.out.print(jsonText);

	}

	public static void encodeJSONArrayUsingLinkedList() {

		@SuppressWarnings("rawtypes")
		LinkedList list = new LinkedList();
		list.add("foo");
		list.add(new Integer(100));
		list.add(new Double(1000.21));
		list.add(new Boolean(true));
		list.add(null);
		String jsonText = JSONValue.toJSONString(list);
		System.out.print(jsonText);

	}

	public static void main(String[] args) {

		// encodeJSONArrayUsingLinkedList();
		// StringReverse("Today is friday");
	}

}
