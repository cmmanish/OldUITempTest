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
import com.google.api.adwords.v201109.cm.AdGroupCriterionOperation;
import com.google.api.adwords.v201109.cm.AdGroupCriterionReturnValue;
import com.google.api.adwords.v201109.cm.AdGroupCriterionServiceInterface;
import com.google.api.adwords.v201109.cm.BiddableAdGroupCriterion;
import com.google.api.adwords.v201109.cm.Keyword;
import com.google.api.adwords.v201109.cm.KeywordMatchType;
import com.google.api.adwords.v201109.cm.Operator;
import com.google.api.adwords.v201109.cm.Placement;

/**
 * This example adds a keyword and a placement to an ad group. To get ad groups,
 * run AddAdGroup.java.
 *
 * Tags: AdGroupCriterionService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AddAdGroupCriteria {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupCriterionService.
      AdGroupCriterionServiceInterface adGroupCriterionService =
          user.getService(AdWordsService.V201109.ADGROUP_CRITERION_SERVICE);

      long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");

      // Create keyword.
      Keyword keyword = new Keyword();
      keyword.setText("mars cruise");
      keyword.setMatchType(KeywordMatchType.BROAD);

      // Create biddable ad group criterion.
      BiddableAdGroupCriterion keywordBiddableAdGroupCriterion = new BiddableAdGroupCriterion();
      keywordBiddableAdGroupCriterion.setAdGroupId(adGroupId);
      keywordBiddableAdGroupCriterion.setCriterion(keyword);

      // Create placement.
      Placement placement = new Placement();
      placement.setUrl("http://mars.google.com");

      // Create biddable ad group criterion for placement.
      BiddableAdGroupCriterion placementBiddableAdGroupCriterion = new BiddableAdGroupCriterion();
      placementBiddableAdGroupCriterion.setAdGroupId(adGroupId);
      placementBiddableAdGroupCriterion.setCriterion(placement);

      // Create operations.
      AdGroupCriterionOperation keywordAdGroupCriterionOperation = new AdGroupCriterionOperation();
      keywordAdGroupCriterionOperation.setOperand(keywordBiddableAdGroupCriterion);
      keywordAdGroupCriterionOperation.setOperator(Operator.ADD);

      AdGroupCriterionOperation placementAdGroupCriterionOperation =
          new AdGroupCriterionOperation();
      placementAdGroupCriterionOperation.setOperand(placementBiddableAdGroupCriterion);
      placementAdGroupCriterionOperation.setOperator(Operator.ADD);

      AdGroupCriterionOperation[] operations =
          new AdGroupCriterionOperation[] {keywordAdGroupCriterionOperation,
              placementAdGroupCriterionOperation};

      // Add ad group criteria.
      AdGroupCriterionReturnValue result = adGroupCriterionService.mutate(operations);

      // Display ad group criteria.
      if (result != null && result.getValue() != null) {
        for (AdGroupCriterion adGroupCriterionResult : result.getValue()) {
          System.out.println("Ad group criterion with ad group id \""
              + adGroupCriterionResult.getAdGroupId() + "\", criterion id \""
              + adGroupCriterionResult.getCriterion().getId() + "\", and type \""
              + adGroupCriterionResult.getCriterion().getCriterionType() + "\" was added.");
        }
      } else {
        System.out.println("No ad group criteria were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
