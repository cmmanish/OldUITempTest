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
import com.google.api.adwords.v201109_1.cm.AdGroupPage;
import com.google.api.adwords.v201109_1.cm.AdGroupServiceInterface;
import com.google.api.adwords.v201109_1.cm.OrderBy;
import com.google.api.adwords.v201109_1.cm.Predicate;
import com.google.api.adwords.v201109_1.cm.PredicateOperator;
import com.google.api.adwords.v201109_1.cm.Selector;
import com.google.api.adwords.v201109_1.cm.SortOrder;

/**
 * This example gets all ad groups in a campaign. To add an ad group, run
 * AddAdGroup.java. To get campaigns, run GetAllCampaigns.java.
 *
 * Tags: AdGroupService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllAdGroups {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupService.
      AdGroupServiceInterface adGroupService =
          user.getService(AdWordsService.V201109_1.ADGROUP_SERVICE);

      Long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"Id", "Name"});
      selector.setOrdering(new OrderBy[] {new OrderBy("Name", SortOrder.ASCENDING)});

      // Create predicates.
      Predicate campaignIdPredicate =
          new Predicate("CampaignId", PredicateOperator.IN, new String[] {campaignId.toString()});
      selector.setPredicates(new Predicate[] {campaignIdPredicate});

      // Get all ad groups.
      AdGroupPage page = adGroupService.get(selector);

      // Display ad groups.
      if (page.getEntries() != null) {
        for (AdGroup adGroup : page.getEntries()) {
          System.out.println("Ad group with name \"" + adGroup.getName()
              + "\" and id \"" + adGroup.getId() + "\" was found.");
        }
      } else {
        System.out.println("No ad groups were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
