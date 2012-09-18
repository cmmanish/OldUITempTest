// Copyright 2011, Google Inc. All Rights Reserved.
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

package v201109;

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.v201109.cm.Budget;
import com.google.api.adwords.v201109.cm.BudgetBudgetDeliveryMethod;
import com.google.api.adwords.v201109.cm.Campaign;
import com.google.api.adwords.v201109.cm.CampaignOperation;
import com.google.api.adwords.v201109.cm.CampaignReturnValue;
import com.google.api.adwords.v201109.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109.cm.Operator;

/**
 * This example updates a campaign by setting the budget delivery method to
 * accelerated. To get campaigns, run GetAllCampaigns.java.
 *
 * Tags: CampaignService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class UpdateCampaign {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignService.
      CampaignServiceInterface campaignService =
          user.getService(AdWordsService.V201109.CAMPAIGN_SERVICE);

      long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create campaign with updated budget.
      Campaign campaign = new Campaign();
      campaign.setId(campaignId);

      // Create budget.
      Budget budget = new Budget();
      budget.setDeliveryMethod(BudgetBudgetDeliveryMethod.ACCELERATED);
      campaign.setBudget(budget);

      // Create operations.
      CampaignOperation operation = new CampaignOperation();
      operation.setOperand(campaign);
      operation.setOperator(Operator.SET);

      CampaignOperation[] operations = new CampaignOperation[] {operation};

      // Update campaign.
      CampaignReturnValue result = campaignService.mutate(operations);

      // Display campaigns.
      if (result != null && result.getValue() != null) {
        for (Campaign campaignResult : result.getValue()) {
          System.out.println("Campaign with name \"" + campaignResult.getName() + "\", id \""
              + campaignResult.getId() + "\", and budget delivery method \""
              + campaignResult.getBudget().getDeliveryMethod() + "\" was updated.");
        }
      } else {
        System.out.println("No campaigns were updated.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
