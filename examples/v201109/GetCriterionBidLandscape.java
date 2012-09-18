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
import com.google.api.adwords.v201109.cm.BidLandscapeLandscapePoint;
import com.google.api.adwords.v201109.cm.CriterionBidLandscape;
import com.google.api.adwords.v201109.cm.CriterionBidLandscapePage;
import com.google.api.adwords.v201109.cm.DataServiceInterface;
import com.google.api.adwords.v201109.cm.Predicate;
import com.google.api.adwords.v201109.cm.PredicateOperator;
import com.google.api.adwords.v201109.cm.Selector;

/**
 * This example gets a bid landscape for an ad group and a criterion. To get ad
 * groups, run GetAllAdGroups.java. To get criteria, run
 * GetAllAdGroupCriteria.java.
 *
 * Tags: BidLandscapeService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetCriterionBidLandscape {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the DataService.
      DataServiceInterface dataService =
          user.getService(AdWordsService.V201109.DATA_SERVICE);

      Long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");
      Long criterionId = Long.parseLong("INSERT_CRITERION_ID_HERE");

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"AdGroupId", "CriterionId", "StartDate", "EndDate",
          "Bid", "LocalClicks", "LocalCost", "MarginalCpc", "LocalImpressions"});

      // Create predicates.
      Predicate adGroupIdPredicate =
          new Predicate("AdGroupId", PredicateOperator.IN, new String[] {adGroupId.toString()});
      Predicate criterionIdPredicate =
          new Predicate("CriterionId", PredicateOperator.IN, new String[] {criterionId.toString()});
      selector.setPredicates(new Predicate[] {adGroupIdPredicate, criterionIdPredicate});

      // Get bid landscape for ad group criteria.
      CriterionBidLandscapePage page = dataService.getCriterionBidLandscape(selector);

      // Display bid landscapes.
      if (page.getEntries() != null) {
        for (CriterionBidLandscape criterionBidLandscape : page.getEntries()) {
          System.out.println("Criterion bid landscape with ad group id \""
              + criterionBidLandscape.getAdGroupId() + "\", criterion id \""
              + criterionBidLandscape.getCriterionId() + "\", start date \""
              + criterionBidLandscape.getStartDate() + "\", end date \""
              + criterionBidLandscape.getEndDate() + "\", with landscape points: ");

          for (BidLandscapeLandscapePoint bidLanscapePoint
              : criterionBidLandscape.getLandscapePoints()) {
            System.out.println("\t{bid: " + bidLanscapePoint.getBid().getMicroAmount()
                + " clicks: " + bidLanscapePoint.getClicks() + " cost: "
                + bidLanscapePoint.getCost().getMicroAmount() + " marginalCpc: "
                + bidLanscapePoint.getMarginalCpc().getMicroAmount() + " impressions: "
                + bidLanscapePoint.getImpressions() + "}");
          }
          System.out.println(" was found.");
        }
      } else {
        System.out.println("No criterion bid landscapes were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
