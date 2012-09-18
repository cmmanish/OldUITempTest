
package com.marin.examples;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientPreferences {

	public static void main(String args[])
		throws Exception {

		HttpClient client = new HttpClient();

		System.err.println("The User Agent before changing it is: " +
			client.getParams().getParameter("http.useragent"));

		client.getParams().setParameter(
			"http.useragent", "Browser at Client level");

		System.err.println("Client's User Agent is: " +
			client.getParams().getParameter("http.useragent"));

		GetMethod method = new GetMethod("http://www.google.com");
		method.getParams().setParameter(
			"http.useragent", "Browser at Method level");
		try {
			client.executeMethod(method);
		}
		catch (Exception e) {
			System.err.println(e);
		}
		finally {
			method.releaseConnection();
		}
		System.err.println("Method's User Agent is: " +
			method.getParams().getParameter("http.useragent"));

	}
}
