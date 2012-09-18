
package com.marin.examples;/*
 * Yahoo! Web Services Example: search API via GET
 *
 * @author Daniel Jones www.danieljones.org
 * Copyright 2007 Daniel Jones
 *
 * This example illustrates how to perform a web service request via HTTP GET.
 * See the YahooWebServicePost example if you are interested in an example of
 * how to perform a web service request via POST.
 *
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class YahooWebServiceGet {

	public static void main(String[] args)
		throws Exception {

		String request =
			"https://graph.facebook.com/6003981785807?date_format=U&access_token=AAAAAMSp0eVoBAOtxZBgwvMnq2BibSZC5LYMvsbhV1WCxlPf01mUOvWWLgA0Ud7GSSOKeWkJCbswPXdbJEZC8SLQuZBXLgrjucOiYj4AP8AZDZD";

		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(request);

		// Send GET request
		int statusCode = client.executeMethod(method);

		System.out.println(statusCode);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + method.getStatusLine());
		}
		InputStream rstream = null;

		// Get the response body
		rstream = method.getResponseBodyAsStream();

		// Process the response from Yahoo! Web Services
		BufferedReader br = new BufferedReader(new InputStreamReader(rstream));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		br.close();
	}

}
