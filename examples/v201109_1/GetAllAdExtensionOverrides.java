// Copyright 2012 Google Inc. All Rights Reserved.
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

package v201109_1;

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.v201109_1.cm.AdExtensionOverride;
import com.google.api.adwords.v201109_1.cm.AdExtensionOverridePage;
import com.google.api.adwords.v201109_1.cm.AdExtensionOverrideSelector;
import com.google.api.adwords.v201109_1.cm.AdExtensionOverrideServiceInterface;

/**
 * This example gets all ad extension overrides for a campaign. To add ad
 * extension overrides, run AddAdExtensionOverride.java. To get campaigns, run
 * GetAllCampaigns.java.
 *
 * Tags: AdExtensionOverrideService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllAdExtensionOverrides {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdExtensionOverrideService.
      AdExtensionOverrideServiceInterface adExtensionOverrideService =
          user.getService(AdWordsService.V201109_1.AD_EXTENSION_OVERRIDE_SERVICE);

      long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create selector.
      AdExtensionOverrideSelector selector = new AdExtensionOverrideSelector();
      selector.setCampaignIds(new long[] {campaignId});

      // Get all ad extension overrides.
      AdExtensionOverridePage page = adExtensionOverrideService.get(selector);

      // Display ad extension overrides.
      if (page.getEntries() != null) {
        for (AdExtensionOverride adExtensionOverride : page.getEntries()) {
          System.out.println("Ad extension override with ad id \"" + adExtensionOverride.getAdId()
              + "\", ad extension id \"" + adExtensionOverride.getAdExtension().getId()
              + "\", and type \"" + adExtensionOverride.getAdExtension().getAdExtensionType()
              + "\" was found.");
        }
      } else {
        System.out.println("No extension overrides were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
