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
import com.google.api.adwords.v201109_1.cm.Campaign;
import com.google.api.adwords.v201109_1.cm.CampaignOperation;
import com.google.api.adwords.v201109_1.cm.CampaignReturnValue;
import com.google.api.adwords.v201109_1.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109_1.cm.CampaignStatus;
import com.google.api.adwords.v201109_1.cm.Operator;

/**
 * This example deletes a campaign by setting the status to 'DELETED'. To get
 * campaigns, run GetAllCampaigns.java.
 *
 * Tags: CampaignService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class DeleteCampaign {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignService.
      CampaignServiceInterface campaignService =
          user.getService(AdWordsService.V201109_1.CAMPAIGN_SERVICE);

      long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create campaign with DELETED status.
      Campaign campaign = new Campaign();
      campaign.setId(campaignId);
      campaign.setStatus(CampaignStatus.DELETED);

      // Create operations.
      CampaignOperation operation = new CampaignOperation();
      operation.setOperand(campaign);
      operation.setOperator(Operator.SET);

      CampaignOperation[] operations = new CampaignOperation[] {operation};

      // Delete campaign.
      CampaignReturnValue result = campaignService.mutate(operations);

      // Display campaigns.
      if (result != null && result.getValue() != null) {
        for (Campaign campaignResult : result.getValue()) {
          System.out.println("Campaign with name \"" + campaignResult.getName() + "\" and id \""
              + campaignResult.getId() + "\" was deleted.");
        }
      } else {
        System.out.println("No campaigns were deleted.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
