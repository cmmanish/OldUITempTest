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
import com.google.api.adwords.v201109.cm.AdGroupCriterion;
import com.google.api.adwords.v201109.cm.AdGroupCriterionPage;
import com.google.api.adwords.v201109.cm.AdGroupCriterionServiceInterface;
import com.google.api.adwords.v201109.cm.BiddableAdGroupCriterion;
import com.google.api.adwords.v201109.cm.OrderBy;
import com.google.api.adwords.v201109.cm.Predicate;
import com.google.api.adwords.v201109.cm.PredicateOperator;
import com.google.api.adwords.v201109.cm.Selector;
import com.google.api.adwords.v201109.cm.SortOrder;

/**
 * This example gets all active ad group criteria in an ad group. To add ad
 * group criteria, run AddAdGroupCriteria.java. To get ad groups, run
 * GetAllAdGroups.java.
 *
 * Tags: AdGroupCriterionService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllActiveAdGroupCriteria {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupCriterionService.
      AdGroupCriterionServiceInterface adGroupCriterionService =
          user.getService(AdWordsService.V201109.ADGROUP_CRITERION_SERVICE);

      Long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"Id", "AdGroupId", "Status"});
      selector.setOrdering(new OrderBy[] {new OrderBy("Id", SortOrder.ASCENDING)});

      // Create predicates.
      Predicate statusPredicate =
          new Predicate("Status", PredicateOperator.IN, new String[] {"ACTIVE"});
      Predicate adGroupIdPredicate =
          new Predicate("AdGroupId", PredicateOperator.IN, new String[] {adGroupId.toString()});
      selector.setPredicates(new Predicate[] {statusPredicate, adGroupIdPredicate});

      // Get all active ad group criteria.
      AdGroupCriterionPage page = adGroupCriterionService.get(selector);

      // Display ad group criteria.
      if (page.getEntries() != null && page.getEntries().length > 0) {
        for (AdGroupCriterion adGroupCriterion : page.getEntries()) {
          if (adGroupCriterion instanceof BiddableAdGroupCriterion) {
            BiddableAdGroupCriterion biddableAdGroupCriterion =
                (BiddableAdGroupCriterion) adGroupCriterion;
            System.out.println("Ad group criterion with ad group id \""
                + biddableAdGroupCriterion.getAdGroupId() + "\", criterion id \""
                + biddableAdGroupCriterion.getCriterion().getId() + "\", type \""
                + biddableAdGroupCriterion.getCriterion().getCriterionType()
                + "\", and user status \"" + biddableAdGroupCriterion.getUserStatus()
                + "\" was found.");
          }
        }
      } else {
        System.out.println("No ad group criteria were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
