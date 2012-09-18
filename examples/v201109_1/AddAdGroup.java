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
import com.google.api.adwords.v201109_1.cm.AdGroup;
import com.google.api.adwords.v201109_1.cm.AdGroupOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupReturnValue;
import com.google.api.adwords.v201109_1.cm.AdGroupServiceInterface;
import com.google.api.adwords.v201109_1.cm.AdGroupStatus;
import com.google.api.adwords.v201109_1.cm.Bid;
import com.google.api.adwords.v201109_1.cm.ManualCPCAdGroupBids;
import com.google.api.adwords.v201109_1.cm.Money;
import com.google.api.adwords.v201109_1.cm.Operator;

/**
 * This example adds an ad group to a campaign. To get campaigns, run
 * GetAllCampaigns.java.
 *
 * Tags: AdGroupService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AddAdGroup {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupService.
      AdGroupServiceInterface adGroupService =
          user.getService(AdWordsService.V201109_1.ADGROUP_SERVICE);

      long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create ad group.
      AdGroup adGroup = new AdGroup();
      adGroup.setName("Earth to Mars Cruises #" + System.currentTimeMillis());
      adGroup.setStatus(AdGroupStatus.ENABLED);
      adGroup.setCampaignId(campaignId);

      // Create ad group bid.
      ManualCPCAdGroupBids adGroupBids = new ManualCPCAdGroupBids();
      adGroupBids.setKeywordMaxCpc(new Bid(new Money(null, 10000000L)));
      adGroup.setBids(adGroupBids);

      // Create operations.
      AdGroupOperation operation = new AdGroupOperation();
      operation.setOperand(adGroup);
      operation.setOperator(Operator.ADD);

      AdGroupOperation[] operations = new AdGroupOperation[]{operation};

      // Add ad group.
      AdGroupReturnValue result = adGroupService.mutate(operations);

      // Display new ad groups.
      if (result != null && result.getValue() != null) {
        for (AdGroup adGroupResult : result.getValue()) {
          System.out.println("Ad group with name \""
              + adGroupResult.getName() + "\" and id \""
              + adGroupResult.getId() + "\" was added.");
        }
      } else {
        System.out.println("No ad groups were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
