
package com.marin.examples;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONTests {

	static Logger log = Logger.getLogger(JSONTests.class);

	public class AddressBook {

		public String firstName;
		public String lastName;
		public int age;
		public Address address;
		public LinkedList<Phone> phoneNumber;
	}

	public class Address {

		public String streetAddress;
		public String city;
		public String state;
		public String postalCode;
	}

	public class Phone {

		public String type;
		public String number;
	}

	public static void parseJSON() {

		String JSONText =
			"{'firstName': 'John','lastName':'Smith',"
				+ "'address':{'streetAddress': '21 2nd Street','city':'New York','state': 'NY','postalCode' : '10021'},'age': 25 }  "; // ,
		// "
		// +
		// "'phoneNumber':[{ 'type'  : 'home','number': '212 555-1234'}," +
		// "{'type'  : 'fax','number': '646 555-4567'}]}";

		Gson gson = new GsonBuilder().create();
		AddressBook mAddressBook = gson.fromJson(JSONText, AddressBook.class);
		String mName = mAddressBook.firstName;
		String city = mAddressBook.address.city;
		log.info(mName);
		log.info(city);
	}

	public static void main(String[] args)
		throws JSONException {

		parseJSON();

	}
}
