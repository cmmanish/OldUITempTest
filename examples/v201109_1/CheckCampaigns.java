// Copyright 2012 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
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
import com.google.api.adwords.v201109_1.cm.ApiException;
import com.google.api.adwords.v201109_1.cm.Budget;
import com.google.api.adwords.v201109_1.cm.BudgetBudgetDeliveryMethod;
import com.google.api.adwords.v201109_1.cm.BudgetBudgetPeriod;
import com.google.api.adwords.v201109_1.cm.Campaign;
import com.google.api.adwords.v201109_1.cm.CampaignOperation;
import com.google.api.adwords.v201109_1.cm.CampaignReturnValue;
import com.google.api.adwords.v201109_1.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109_1.cm.CampaignStatus;
import com.google.api.adwords.v201109_1.cm.ManualCPC;
import com.google.api.adwords.v201109_1.cm.Money;
import com.google.api.adwords.v201109_1.cm.Operator;

/**
 * This example shows how to use the validate only header through the
 * {@link AdWordsUser#getValidationService(AdWordsService)} method. No objects
 * will be created, but exceptions will still be thrown.
 *
 * Tags: CampaignService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class CheckCampaigns {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the validation CampaignService.
      CampaignServiceInterface campaignValidationService =
          user.getValidationService(AdWordsService.V201109_1.CAMPAIGN_SERVICE);

      // Create campaign.
      Campaign goodCampaign = new Campaign();
      goodCampaign.setName("Campaign #" + System.currentTimeMillis());
      goodCampaign.setStatus(CampaignStatus.PAUSED);
      goodCampaign.setBiddingStrategy(new ManualCPC());

      // Create budget.
      Budget budget = new Budget();
      budget.setPeriod(BudgetBudgetPeriod.DAILY);
      budget.setAmount(new Money(null, 50000000L));
      budget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);
      goodCampaign.setBudget(budget);

      // Create operations.
      CampaignOperation operation = new CampaignOperation();
      operation.setOperand(goodCampaign);
      operation.setOperator(Operator.ADD);

      CampaignOperation[] operations = new CampaignOperation[] {operation};

      // Validate campaign add operation.
      CampaignReturnValue result = campaignValidationService.mutate(operations);

      // Display new campaigns, which should be none if the service was a
      // validation service.
      if (result != null) {
        for (Campaign campaignResult : result.getValue()) {
          System.out.println("New campaign with name \"" + campaignResult.getName()
              + "\" and id \"" + campaignResult.getId() + "\" was created.");
        }
      } else {
        System.out.println("No campaigns created.");
      }

      // Provide an invalid bidding strategy that will cause an exception
      // during validation.
      Campaign badCampaign = new Campaign();
      badCampaign.setName("Campaign #" + System.currentTimeMillis());
      badCampaign.setStatus(CampaignStatus.PAUSED);
      badCampaign.setBudget(budget);

      // Throws RequiredError.REQUIRED @ operations[0].operand.biddingStrategy.
      badCampaign.setBiddingStrategy(null);

      // Create operations.
      operation = new CampaignOperation();
      operation.setOperand(badCampaign);
      operation.setOperator(Operator.ADD);

      operations = new CampaignOperation[] {operation};

      try {
        // Validate campaign add operation.
        result = campaignValidationService.mutate(operations);
      } catch (ApiException e) {
        System.err.println("Validation failed for reason \"" + e.getMessage1() + "\".");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
