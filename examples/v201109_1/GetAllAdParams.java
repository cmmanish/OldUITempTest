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
import com.google.api.adwords.v201109_1.cm.AdParam;
import com.google.api.adwords.v201109_1.cm.AdParamPage;
import com.google.api.adwords.v201109_1.cm.AdParamServiceInterface;
import com.google.api.adwords.v201109_1.cm.Predicate;
import com.google.api.adwords.v201109_1.cm.PredicateOperator;
import com.google.api.adwords.v201109_1.cm.Selector;

/**
 * This example gets all ad params in an ad group. To set ad params, run
 * SetAdParams.java. To get ad groups, run GetAllAdGroups.java.
 *
 * Tags: AdParamService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllAdParams {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdParamService.
      AdParamServiceInterface adParamService =
          user.getService(AdWordsService.V201109_1.AD_PARAM_SERVICE);

      String adGroupId = "INSERT_AD_GROUP_ID_HERE";

      // Create selector.
      Selector selector = new Selector();
      Predicate predicate = new Predicate();
      predicate.setField("AdGroupId");
      predicate.setOperator(PredicateOperator.EQUALS);
      predicate.setValues(new String[] {adGroupId});
      selector.setPredicates(new Predicate[] {predicate});
      selector.setFields(new String[]{"AdGroupId", "CriterionId", "InsertionText", "ParamIndex"});

      // Get all ad parameters.
      AdParamPage page = adParamService.get(selector);

      // Display ad parameters.
      if (page.getEntries() != null && page.getEntries().length > 0) {
        for (AdParam adParam : page.getEntries()) {
          System.out.println("Ad parameter with ad group id \"" + adParam.getAdGroupId()
              + "\", criterion id \"" + adParam.getCriterionId()
              + "\", insertion text \"" + adParam.getInsertionText()
              + "\", and parameter index \"" + adParam.getParamIndex()
              + "\" was found.");
        }
      } else {
        System.out.println("No ad parameters were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
