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
import com.google.api.adwords.v201109_1.cm.Budget;
import com.google.api.adwords.v201109_1.cm.BudgetBudgetDeliveryMethod;
import com.google.api.adwords.v201109_1.cm.BudgetBudgetPeriod;
import com.google.api.adwords.v201109_1.cm.Campaign;
import com.google.api.adwords.v201109_1.cm.CampaignOperation;
import com.google.api.adwords.v201109_1.cm.CampaignReturnValue;
import com.google.api.adwords.v201109_1.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109_1.cm.CampaignStatus;
import com.google.api.adwords.v201109_1.cm.GeoTargetTypeSetting;
import com.google.api.adwords.v201109_1.cm.GeoTargetTypeSettingPositiveGeoTargetType;
import com.google.api.adwords.v201109_1.cm.KeywordMatchSetting;
import com.google.api.adwords.v201109_1.cm.ManualCPC;
import com.google.api.adwords.v201109_1.cm.Money;
import com.google.api.adwords.v201109_1.cm.NetworkSetting;
import com.google.api.adwords.v201109_1.cm.Operator;
import com.google.api.adwords.v201109_1.cm.Setting;

/**
 * This example adds a campaign.
 *
 * Tags: CampaignService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AddCampaign {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignService.
      CampaignServiceInterface campaignService =
          user.getService(AdWordsService.V201109_1.CAMPAIGN_SERVICE);

      // Create campaign.
      Campaign campaign = new Campaign();
      campaign.setName("Interplanetary Cruise #" + System.currentTimeMillis());
      campaign.setStatus(CampaignStatus.PAUSED);
      campaign.setBiddingStrategy(new ManualCPC());

      // Create budget.
      Budget budget = new Budget();
      budget.setPeriod(BudgetBudgetPeriod.DAILY);
      budget.setAmount(new Money(null, 50000000L));
      budget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);
      campaign.setBudget(budget);

      // Set the campaign network options to Search and Search Network.
      NetworkSetting networkSetting = new NetworkSetting();
      networkSetting.setTargetGoogleSearch(true);
      networkSetting.setTargetSearchNetwork(true);
      networkSetting.setTargetContentNetwork(false);
      networkSetting.setTargetPartnerSearchNetwork(false);
      networkSetting.setTargetContentContextual(false);
      campaign.setNetworkSetting(networkSetting);

      // Set options that are not required.
      GeoTargetTypeSetting geoTarget = new GeoTargetTypeSetting();
      geoTarget.setPositiveGeoTargetType(GeoTargetTypeSettingPositiveGeoTargetType.DONT_CARE);
      KeywordMatchSetting keywordMatch = new KeywordMatchSetting();
      keywordMatch.setOptIn(Boolean.FALSE);
      campaign.setSettings(new Setting[] {geoTarget, keywordMatch});

      // Create operations.
      CampaignOperation operation = new CampaignOperation();
      operation.setOperand(campaign);
      operation.setOperator(Operator.ADD);

      CampaignOperation[] operations = new CampaignOperation[] {operation};

      // Add campaign.
      CampaignReturnValue result = campaignService.mutate(operations);

      // Display campaigns.
      if (result != null && result.getValue() != null) {
        for (Campaign campaignResult : result.getValue()) {
          System.out.println("Campaign with name \"" + campaignResult.getName() + "\" and id \""
              + campaignResult.getId() + "\" was added.");
        }
      } else {
        System.out.println("No campaigns were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
