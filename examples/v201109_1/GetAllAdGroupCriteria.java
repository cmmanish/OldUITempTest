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
import com.google.api.adwords.v201109_1.cm.AdGroupCriterion;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionPage;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionServiceInterface;
import com.google.api.adwords.v201109_1.cm.OrderBy;
import com.google.api.adwords.v201109_1.cm.Predicate;
import com.google.api.adwords.v201109_1.cm.PredicateOperator;
import com.google.api.adwords.v201109_1.cm.Selector;
import com.google.api.adwords.v201109_1.cm.SortOrder;

/**
 * This example gets all ad group criteria in an ad group. To add ad
 * group criteria, run AddAdGroupCriteria.java. To get ad groups, run
 * GetAllAdGroups.java.
 *
 * Tags: AdGroupCriterionService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllAdGroupCriteria {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupCriterionService.
      AdGroupCriterionServiceInterface adGroupCriterionService =
          user.getService(AdWordsService.V201109_1.ADGROUP_CRITERION_SERVICE);

      Long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"Id", "AdGroupId"});
      selector.setOrdering(new OrderBy[] {new OrderBy("AdGroupId", SortOrder.ASCENDING)});

      // Create predicates.
      Predicate adGroupIdPredicate =
          new Predicate("AdGroupId", PredicateOperator.IN, new String[] {adGroupId.toString()});
      selector.setPredicates(new Predicate[] {adGroupIdPredicate});

      // Get all ad group criteria.
      AdGroupCriterionPage page = adGroupCriterionService.get(selector);

      // Display ad group criteria.
      if (page.getEntries() != null && page.getEntries().length > 0) {
        for (AdGroupCriterion adGroupCriterion : page.getEntries()) {
          System.out.println("Ad group criterion with ad group id \""
              + adGroupCriterion.getAdGroupId() + "\", criterion id \""
              + adGroupCriterion.getCriterion().getId() + "\", and type \""
              + adGroupCriterion.getCriterion().getCriterionType() + "\" was found.");
        }
      } else {
        System.out.println("No ad group criteria were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
