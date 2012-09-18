// Copyright 2010 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package utils;

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.v201109.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109.cm.Selector;

/**
 * This demo shows how to use a proxy without using JVM parameters. The proxy
 * will be used by Axis and the HTTP client to fetch authentication tokens.
 */
public class ProxyDemo {
  public static void main(String[] args) throws Exception {
    // Set the proxy information.
    System.setProperty("http.proxyHost", "INSERT_PROXY_HOST_HERE");
    System.setProperty("http.proxyPort", "INSERT_PROXY_PORT_HERE");
    // Uncomment the following two lines if needed.
    //System.setProperty("http.proxyUser", "INSERT_PROXY_USER_HERE");
    //System.setProperty("http.proxyPassword", "INSERT_PROXY_PASSWORD_HERE");

    // Log SOAP XML request and response.
    AdWordsServiceLogger.log();

    // Get AdWordsUser from "~/adwords.properties".
    AdWordsUser user = new AdWordsUser();

    // Get the CampaignService.
    CampaignServiceInterface campaignService =
        user.getService(AdWordsService.V201109.CAMPAIGN_SERVICE);

    // Get all campaigns using proxy.
    campaignService.get(new Selector());
  }

}
