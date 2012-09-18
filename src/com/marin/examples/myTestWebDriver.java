
package com.marin.examples;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.marin.testScripts.WebDriverBaseClass;

public class myTestWebDriver extends WebDriverBaseClass {

	public static HashMap<Integer, String> fieldValuesMap =
		new HashMap<Integer, String>();
	static Logger log = Logger.getLogger(myTestWebDriver.class);
	static String campaignName = "Auto_GoogleCampaign3";

	public myTestWebDriver()
		throws InterruptedException {

		super();

	}

	public static void testWebDriver()
		throws InterruptedException {

		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://app.marinsoftware.com");

		log.info(driver.getTitle());
		driver.close();
	}

	// public static void testSeleniumRC()
	// throws InterruptedException {
	//
	// Selenium selenium =
	// new DefaultSelenium(
	// "localhost", 4444, "*firefox", "http://www.twitter.com");
	// selenium.start();
	// selenium.windowMaximize();
	// // selenium.open("http://www.facebook.com");
	//
	// }

	// public static void testConfigParsers()
	// throws Exception {
	//
	// Properties prop = new Properties();
	// FileInputStream fis =
	// new FileInputStream(
	// "/Users//pkrish//workspace//WebDriverUI//src//com//marin//testData//Config.xml");
	// prop.loadFromXML(fis);
	// log.info("URL " + prop.getProperty("url"));
	// log.info("Browser " + prop.getProperty("browser"));
	//
	// }

	// public static void testDataParsers()
	// throws Exception {
	//
	// DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	// DocumentBuilder loader = factory.newDocumentBuilder();
	// Document document =
	// loader.parse("/Users//pkrish//workspace//WebDriverUI//src//com//marin//testData//Data.xml");
	// DocumentTraversal traversal = (DocumentTraversal) document;
	// TreeWalker walker =
	// traversal.createTreeWalker(
	// document.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null,
	// true);
	// traverseLevel(walker);
	//
	// }
	//
	// public static void assignDataValues() {
	//
	// String email = fieldValuesMap.get(1);
	// String password = fieldValuesMap.get(2);
	// log.info("Email " + email);
	// log.info("Password " + password);
	//
	// }
	//
	// public static final void traverseLevel(TreeWalker walker) {
	//
	// HashMap<Integer, String> testCaseMap = new HashMap<Integer, String>();
	// Node curNode = walker.getCurrentNode();
	// String tagName = ((Element) curNode).getTagName();
	// int fCount = 0, tCount = 0;
	// if (tagName.equalsIgnoreCase("container")) {
	// log.info("Container starts ");
	// for (Node i = walker.firstChild(); i != null; i = walker.nextNode()) {
	// tagName = ((Element) i).getTagName();
	//
	// if (tagName.equalsIgnoreCase("testCase")) {
	// String testCaseName = ((Element) i).getAttribute("name");
	// log.info("testCaseName is  " + testCaseName);
	// testCaseMap.put(++tCount, testCaseName);
	// for (Node n = walker.firstChild(); n != null; n =
	// walker.nextNode()) {
	// tagName = ((Element) n).getTagName();
	// if (tagName.equalsIgnoreCase("field")) {
	// String fieldName =
	// ((Element) n).getAttribute("name");
	// String fieldValue =
	// ((Element) n).getAttribute("value");
	// log.info("field Name    " + fieldName);
	// log.info("field value    " + fieldValue);
	// fieldValuesMap.put(++fCount, fieldName + ":" +
	// fieldValue);
	// }
	// }
	// }
	//
	// }
	// }
	// }
	//
	// public static void testGoogleServices()
	// throws Exception {
	//
	// String GOOGLE_CAMPAIGN_UNIQUE = "Zdeleted Unique";
	// // String GOOGLE_CAMPAIGN_UNIQUE = "Zdeleted Unique";
	// // for now I create campaign manually from adwords
	// GoogleServiceV201109 googleService = new GoogleServiceV201109();
	// log.info("status is " + googleService.getStatus(campaignName));
	//
	// }

	public static void main(String[] args)
		throws Exception {

		// log.info("testSeleniumRC");
		// testSeleniumRC();
		testWebDriver();
		// testConfigParsers();
		// testDataParsers();
		// assignDataValues();
		// testGoogleServices();

	}
}
