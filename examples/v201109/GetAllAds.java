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
import com.google.api.adwords.v201109.cm.AdGroupAd;
import com.google.api.adwords.v201109.cm.AdGroupAdPage;
import com.google.api.adwords.v201109.cm.AdGroupAdServiceInterface;
import com.google.api.adwords.v201109.cm.OrderBy;
import com.google.api.adwords.v201109.cm.Predicate;
import com.google.api.adwords.v201109.cm.PredicateOperator;
import com.google.api.adwords.v201109.cm.Selector;
import com.google.api.adwords.v201109.cm.SortOrder;

/**
 * This example gets all ads in an ad group. To add ads, run AddAds.java. To
 * get ad groups, run GetAllAdGroups.java.
 *
 * Tags: AdGroupAdService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllAds {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupAdService.
      AdGroupAdServiceInterface adGroupAdService =
          user.getService(AdWordsService.V201109.ADGROUP_AD_SERVICE);

      Long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"Id", "AdGroupId", "Status"});
      selector.setOrdering(new OrderBy[] {new OrderBy("Id", SortOrder.ASCENDING)});

      // Create predicates.
      Predicate adGroupIdPredicate =
          new Predicate("AdGroupId", PredicateOperator.IN, new String[] {adGroupId.toString()});
      // By default disabled ads aren't returned by the selector. To return them
      // include the DISABLED status in a predicate.
      Predicate statusPredicate =
          new Predicate("Status", PredicateOperator.IN, new String[] {"ENABLED", "PAUSED",
              "DISABLED"});
      selector.setPredicates(new Predicate[] {adGroupIdPredicate, statusPredicate});

      // Get all ads.
      AdGroupAdPage page = adGroupAdService.get(selector);

      // Display ads.
      if (page.getEntries() != null && page.getEntries().length > 0) {
        for (AdGroupAd adGroupAd : page.getEntries()) {
          System.out.println("Ad with id  \"" + adGroupAd.getAd().getId() + "\""
              + " and type \"" + adGroupAd.getAd().getAdType() + "\" was found.");
        }
      } else {
        System.out.println("No ads were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
