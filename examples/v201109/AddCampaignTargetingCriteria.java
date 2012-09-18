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
import com.google.api.adwords.v201109.cm.CampaignCriterion;
import com.google.api.adwords.v201109.cm.CampaignCriterionOperation;
import com.google.api.adwords.v201109.cm.CampaignCriterionReturnValue;
import com.google.api.adwords.v201109.cm.CampaignCriterionServiceInterface;
import com.google.api.adwords.v201109.cm.Criterion;
import com.google.api.adwords.v201109.cm.Language;
import com.google.api.adwords.v201109.cm.Location;
import com.google.api.adwords.v201109.cm.Operator;
import com.google.api.adwords.v201109.cm.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * This example adds various types of targeting criteria to a campaign. To get
 * campaigns, run GetAllCampaigns.java
 *
 * Tags: CampaignCriterionService.mutate
 *
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class AddCampaignTargetingCriteria {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignService.
      CampaignCriterionServiceInterface campaignCriterionService =
          user.getService(AdWordsService.V201109.CAMPAIGN_CRITERION_SERVICE);

      Long campaignId = Long.valueOf("INSERT_CAMPAIGN_ID_HERE");

      // Create locations. The IDs can be found in the documentation or
      // retrieved with the LocationCriterionService.
      Location california = new Location();
      california.setId(21137L);
      Location mexico = new Location();
      mexico.setId(2484L);

      // Create languages. The IDs can be found in the documentation or
      // retrieved with the ConstantDataService.
      Language english = new Language();
      english.setId(1000L);
      Language spanish = new Language();
      spanish.setId(1003L);

      // Create platforms. The IDs can be found in the documentation.
      Platform mobile = new Platform();
      mobile.setId(30001L);
      Platform tablets = new Platform();
      tablets.setId(30002L);

      List<CampaignCriterionOperation> operations = new ArrayList<CampaignCriterionOperation>();
      for (Criterion criterion : new Criterion[] {california, mexico, english, spanish, mobile,
          tablets}) {
        CampaignCriterionOperation operation = new CampaignCriterionOperation();
        CampaignCriterion campaignCriterion = new CampaignCriterion();
        campaignCriterion.setCampaignId(campaignId);
        campaignCriterion.setCriterion(criterion);
        operation.setOperand(campaignCriterion);
        operation.setOperator(Operator.ADD);
        operations.add(operation);
      }

      CampaignCriterionReturnValue result =
          campaignCriterionService.mutate(operations
              .toArray(new CampaignCriterionOperation[operations.size()]));

      // Display campaigns.
      for (CampaignCriterion campaignCriterion : result.getValue()) {
        System.out.printf("Campaign criterion with campaign id '%s', criterion id '%s', "
            + "and type '%s' was added.\n", campaignCriterion.getCampaignId(), campaignCriterion
            .getCriterion().getId(), campaignCriterion.getCriterion().getCriterionType());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
