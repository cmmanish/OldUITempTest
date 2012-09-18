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
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionReturnValue;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionServiceInterface;
import com.google.api.adwords.v201109_1.cm.Bid;
import com.google.api.adwords.v201109_1.cm.BiddableAdGroupCriterion;
import com.google.api.adwords.v201109_1.cm.Criterion;
import com.google.api.adwords.v201109_1.cm.ManualCPCAdGroupCriterionBids;
import com.google.api.adwords.v201109_1.cm.Money;
import com.google.api.adwords.v201109_1.cm.Operator;

/**
 * This example updates the bid of an ad group criterion. To get ad group
 * criteria, run GetAllAdGroupCriteria.java.
 *
 * Tags: AdGroupCriterionService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class UpdateAdGroupCriterion {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupCriterionService.
      AdGroupCriterionServiceInterface adGroupCriterionService =
          user.getService(AdWordsService.V201109_1.ADGROUP_CRITERION_SERVICE);

      long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");
      long criterionId = Long.parseLong("INSERT_CRITERION_ID_HERE");

      // Create ad group criterion with updated bid.
      Criterion criterion = new Criterion();
      criterion.setId(criterionId);

      BiddableAdGroupCriterion biddableAdGroupCriterion = new BiddableAdGroupCriterion();
      biddableAdGroupCriterion.setAdGroupId(adGroupId);
      biddableAdGroupCriterion.setCriterion(criterion);

      // Create bids.
      ManualCPCAdGroupCriterionBids bids = new ManualCPCAdGroupCriterionBids();
      bids.setMaxCpc(new Bid(new Money(null, 1000000L)));
      biddableAdGroupCriterion.setBids(bids);

      // Create operations.
      AdGroupCriterionOperation operation = new AdGroupCriterionOperation();
      operation.setOperand(biddableAdGroupCriterion);
      operation.setOperator(Operator.SET);

      AdGroupCriterionOperation[] operations = new AdGroupCriterionOperation[] {operation};

      // Update ad group criteria.
      AdGroupCriterionReturnValue result = adGroupCriterionService.mutate(operations);

      // Display ad group criteria.
      if (result != null && result.getValue() != null) {
        for (AdGroupCriterion adGroupCriterionResult : result.getValue()) {
          if (adGroupCriterionResult instanceof BiddableAdGroupCriterion) {
            biddableAdGroupCriterion = (BiddableAdGroupCriterion) adGroupCriterionResult;
            System.out.println("Ad group criterion with ad group id \""
                + biddableAdGroupCriterion.getAdGroupId() + "\", criterion id \""
                + biddableAdGroupCriterion.getCriterion().getId() + "\", type \""
                + biddableAdGroupCriterion.getCriterion().getCriterionType() + "\", and bid \""
                + ((ManualCPCAdGroupCriterionBids) biddableAdGroupCriterion.getBids())
                    .getMaxCpc().getAmount().getMicroAmount() + "\" was updated.");
          }
        }
      } else {
        System.out.println("No ad group criteria were updated.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
