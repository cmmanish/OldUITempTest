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
import com.google.api.adwords.v201109.cm.CampaignCriterion;
import com.google.api.adwords.v201109.cm.CampaignCriterionOperation;
import com.google.api.adwords.v201109.cm.CampaignCriterionReturnValue;
import com.google.api.adwords.v201109.cm.CampaignCriterionServiceInterface;
import com.google.api.adwords.v201109.cm.Keyword;
import com.google.api.adwords.v201109.cm.KeywordMatchType;
import com.google.api.adwords.v201109.cm.NegativeCampaignCriterion;
import com.google.api.adwords.v201109.cm.Operator;

/**
 * This example adds negative criteria to a campaign. To get campaigns run
 * GetAllCampaigns.java.
 *
 * Tags: CampaignCriterionService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AddNegativeCampaignCriterion {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignCriterionService.
      CampaignCriterionServiceInterface campaignCriterionService =
          user.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);

      long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create keyword.
      Keyword keyword = new Keyword();
      keyword.setText("jupiter cruise");
      keyword.setMatchType(KeywordMatchType.BROAD);

      // Create negative campaign criterion.
      NegativeCampaignCriterion negativeCampaignCriterion = new NegativeCampaignCriterion();
      negativeCampaignCriterion.setCampaignId(campaignId);
      negativeCampaignCriterion.setCriterion(keyword);

      // Create operations.
      CampaignCriterionOperation operation = new CampaignCriterionOperation();
      operation.setOperand(negativeCampaignCriterion);
      operation.setOperator(Operator.ADD);

      // Add campaign criteria.
      CampaignCriterionReturnValue result =
          campaignCriterionService.mutate(new CampaignCriterionOperation[] {operation});

      // Display campaign criteria.
      if (result != null && result.getValue() != null) {
        for (CampaignCriterion campaignCriterionResult : result.getValue()) {
          System.out.println("Campaign criterion with campaign id \""
              + campaignCriterionResult.getCampaignId() + "\", criterion id \""
              + campaignCriterionResult.getCriterion().getId() + "\", and type \""
              + campaignCriterionResult.getCriterion().getCriterionType() + "\" was added.");
        }
      } else {
        System.out.println("No campaign criteria were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
