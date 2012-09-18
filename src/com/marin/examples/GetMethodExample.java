
package com.marin.examples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;

public class GetMethodExample {

	public static void main(String args[]) {

		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");
		client.getParams().setParameter(
			"http.connection.timeout", new Integer(5000));

		GetMethod method = new GetMethod();
		FileOutputStream fos = null;

		try {

			method.setURI(new URI("http://www.google.com", true));
			int returnCode = client.executeMethod(method);

			if (returnCode != HttpStatus.SC_OK) {
				System.err.println("Unable to fetch default page, status code: " +
					returnCode);
			}

			System.err.println(method.getResponseBodyAsString());

			method.setURI(new URI("http://www.google.com/images/logo.gif", true));
			returnCode = client.executeMethod(method);

			if (returnCode != HttpStatus.SC_OK) {
				System.err.println("Unable to fetch image, status code: " +
					returnCode);
			}

			byte[] imageData = method.getResponseBody();
			fos = new FileOutputStream(new File("google.gif"));
			fos.write(imageData);

			HostConfiguration hostConfig = new HostConfiguration();
			hostConfig.setHost(
				"www.yahoo.com", null, 80, Protocol.getProtocol("http"));

			method.setURI(new URI("/", true));

			client.executeMethod(hostConfig, method);

			System.err.println(method.getResponseBodyAsString());

		}
		catch (HttpException he) {
			System.err.println(he);
		}
		catch (IOException ie) {
			System.err.println(ie);
		}
		finally {
			method.releaseConnection();
			if (fos != null)
				try {
					fos.close();
				}
				catch (Exception fe) {
				}
		}

	}
}
