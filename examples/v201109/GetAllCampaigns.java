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
import com.google.api.adwords.v201109.cm.Campaign;
import com.google.api.adwords.v201109.cm.CampaignPage;
import com.google.api.adwords.v201109.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109.cm.OrderBy;
import com.google.api.adwords.v201109.cm.Selector;
import com.google.api.adwords.v201109.cm.SortOrder;

/**
 * This example gets all campaigns. To add a campaign, run AddCampaign.java.
 *
 * Tags: CampaignService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllCampaigns {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignService.
      CampaignServiceInterface campaignService =
          user.getService(AdWordsService.V201109.CAMPAIGN_SERVICE);

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"Id", "Name"});
      selector.setOrdering(new OrderBy[] {new OrderBy("Name", SortOrder.ASCENDING)});
      
      // Get all campaigns.
      CampaignPage page = campaignService.get(selector);

      // Display campaigns.
      if (page.getEntries() != null) {
        for (Campaign campaign : page.getEntries()) {
          System.out.println("Campaign with name \"" + campaign.getName() + "\" and id \""
              + campaign.getId() + "\" was found.");
        }
      } else {
        System.out.println("No campaigns were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
