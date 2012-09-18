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
import com.google.api.adwords.v201109_1.cm.AdGroupBidLandscape;
import com.google.api.adwords.v201109_1.cm.AdGroupBidLandscapePage;
import com.google.api.adwords.v201109_1.cm.BidLandscapeLandscapePoint;
import com.google.api.adwords.v201109_1.cm.DataServiceInterface;
import com.google.api.adwords.v201109_1.cm.Predicate;
import com.google.api.adwords.v201109_1.cm.PredicateOperator;
import com.google.api.adwords.v201109_1.cm.Selector;

/**
 * This example gets a bid landscape for an ad group. To get ad groups, run
 * GetAllAdGroups.java.
 *
 * Tags: BidLandscapeService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAdGroupBidLandscape {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the DataService.
      DataServiceInterface dataService =
          user.getService(AdWordsService.V201109_1.DATA_SERVICE);

      Long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"AdGroupId", "LandscapeType", "LandscapeCurrent",
          "StartDate", "EndDate", "Bid", "LocalClicks", "LocalCost", "MarginalCpc",
          "LocalImpressions"});

      // Create predicates.
      Predicate adGroupIdPredicate =
          new Predicate("AdGroupId", PredicateOperator.IN, new String[] {adGroupId.toString()});
      selector.setPredicates(new Predicate[] {adGroupIdPredicate});

      // Get bid landscape for ad group criteria.
      AdGroupBidLandscapePage page = dataService.getAdGroupBidLandscape(selector);

      // Display bid landscapes.
      if (page.getEntries() != null) {
        for (AdGroupBidLandscape adGroupBidLandscape : page.getEntries()) {
          System.out.println("Ad group bid landscape with ad group id \""
              + adGroupBidLandscape.getAdGroupId() + "\", type \""
              + adGroupBidLandscape.getType() + "\", current \""
              + adGroupBidLandscape.getLandscapeCurrent() + "\", start date \""
              + adGroupBidLandscape.getStartDate() + "\", end date \""
              + adGroupBidLandscape.getEndDate() + "\", with landscape points: ");

          for (BidLandscapeLandscapePoint bidLanscapePoint
              : adGroupBidLandscape.getLandscapePoints()) {
            System.out.println("\t{bid: " + bidLanscapePoint.getBid().getMicroAmount()
                + " clicks: " + bidLanscapePoint.getClicks() + " cost: "
                + bidLanscapePoint.getCost().getMicroAmount() + " marginalCpc: "
                + bidLanscapePoint.getMarginalCpc().getMicroAmount() + " impressions: "
                + bidLanscapePoint.getImpressions() + "}");
          }
          System.out.println(" was found.");
        }
      } else {
        System.out.println("No ad group bid landscapes were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
