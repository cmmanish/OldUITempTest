// Copyright 2011, Google Inc. All Rights Reserved.
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

package v201109;

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.v201109.cm.CampaignTargetPage;
import com.google.api.adwords.v201109.cm.CampaignTargetSelector;
import com.google.api.adwords.v201109.cm.CampaignTargetServiceInterface;
import com.google.api.adwords.v201109.cm.TargetList;

/**
 * This example gets all campaign targets for a campaign. To set campaign
 * targets, run SetCampaignTargets.java. To get campaigns, run
 * GetAllCampaigns.java.
 *
 * Tags: CampaignTargetService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllCampaignTargets {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignTargetService.
      CampaignTargetServiceInterface campaignTargetService =
          user.getService(AdWordsService.V201109.CAMPAIGN_TARGET_SERVICE);

      long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create selector.
      CampaignTargetSelector selector = new CampaignTargetSelector();
      selector.setCampaignIds(new long[] {campaignId});

      // Get all campaign targets.
      CampaignTargetPage page = campaignTargetService.get(selector);

      // Display campaign targets.
      if (page.getEntries() != null) {
        for (TargetList targetList : page.getEntries()) {
          System.out.println("Campaign target with campaign id \"" + targetList.getCampaignId()
              + "\" and of type \"" + targetList.getTargetListType() + "\" was found.");
        }
      } else {
        System.out.println("No campaign targets were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
