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
import com.google.api.adwords.v201109_1.cm.AdGroup;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionServiceInterface;
import com.google.api.adwords.v201109_1.cm.AdGroupExperimentData;
import com.google.api.adwords.v201109_1.cm.AdGroupOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupServiceInterface;
import com.google.api.adwords.v201109_1.cm.BidMultiplier;
import com.google.api.adwords.v201109_1.cm.BiddableAdGroupCriterion;
import com.google.api.adwords.v201109_1.cm.BiddableAdGroupCriterionExperimentData;
import com.google.api.adwords.v201109_1.cm.Criterion;
import com.google.api.adwords.v201109_1.cm.Experiment;
import com.google.api.adwords.v201109_1.cm.ExperimentDeltaStatus;
import com.google.api.adwords.v201109_1.cm.ExperimentOperation;
import com.google.api.adwords.v201109_1.cm.ExperimentReturnValue;
import com.google.api.adwords.v201109_1.cm.ExperimentServiceInterface;
import com.google.api.adwords.v201109_1.cm.ManualCPCAdGroupCriterionExperimentBidMultiplier;
import com.google.api.adwords.v201109_1.cm.ManualCPCAdGroupExperimentBidMultipliers;
import com.google.api.adwords.v201109_1.cm.Operator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This example creates an experiment using a query percentage of 10, which
 * defines what fraction of auctions should go to the control split (90%) vs.
 * the experiment split (10%), then adds experimental bid changes for criteria
 * and ad groups. To get campaigns, run GetAllCampaigns.java. To get ad groups,
 * run GetAllAdGroups.java. To get criteria, run GetAllAdGroupCriteria.java.
 *
 * Tags: ExperimentService.mutate
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AddExperiment {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ExperimentService.
      ExperimentServiceInterface experimentService =
          user.getService(AdWordsService.V201109_1.EXPERIMENT_SERVICE);

      // Get the AdGroupService.
      AdGroupServiceInterface adGroupService =
          user.getService(AdWordsService.V201109_1.ADGROUP_SERVICE);

      // Get the AdGroupCriterionService.
      AdGroupCriterionServiceInterface adGroupCriterionService =
          user.getService(AdWordsService.V201109_1.ADGROUP_CRITERION_SERVICE);

      // Replace with valid values of your account.
      long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");
      long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");
      long criterionId = Long.parseLong("INSERT_CRITERION_ID_HERE");

      // Create experiment.
      Experiment experiment = new Experiment();
      experiment.setCampaignId(campaignId);
      experiment.setName("Interplanetary Experiment #" + System.currentTimeMillis());
      experiment.setQueryPercentage(10);
      experiment.setStartDateTime(new SimpleDateFormat("yyyyMMdd HHmmss Z").format(new Date()));

      // Create operation.
      ExperimentOperation experimentOperation = new ExperimentOperation();
      experimentOperation.setOperand(experiment);
      experimentOperation.setOperator(Operator.ADD);

      // Add experiment.
      ExperimentReturnValue result =
          experimentService.mutate(new ExperimentOperation[]{experimentOperation});

      if (result.getValue() != null) {
        for(Experiment experimentResult : result.getValue()) {
          System.out.println("Experiment with name \"" + experimentResult.getName()
              + "\" and id \"" + experimentResult.getId() + "\" was added.");
        }
      } else {
        System.out.println ("No experiments were added.");
        return;
      }

      Long experimentId = result.getValue()[0].getId();

      // Set ad group for the experiment.
      AdGroup adGroup = new AdGroup();
      adGroup.setId(adGroupId);

      // Create experiment bid multiplier rule that will modify ad group bid for
      // the experiment.
      ManualCPCAdGroupExperimentBidMultipliers adGroupExperimentBidMultipliers =
          new ManualCPCAdGroupExperimentBidMultipliers();
      adGroupExperimentBidMultipliers.setMaxCpcMultiplier(new BidMultiplier(1.5, null));

      // Set experiment data to the ad group.
      AdGroupExperimentData adGroupExperimentData = new AdGroupExperimentData();
      adGroupExperimentData.setExperimentId(experimentId);
      adGroupExperimentData.setExperimentDeltaStatus(ExperimentDeltaStatus.MODIFIED);
      adGroupExperimentData.setExperimentBidMultipliers(adGroupExperimentBidMultipliers);
      adGroup.setExperimentData(adGroupExperimentData);

      // Create operation.
      AdGroupOperation adGroupOperation = new AdGroupOperation();
      adGroupOperation.setOperand(adGroup);
      adGroupOperation.setOperator(Operator.SET);

      // Update ad group.
      adGroup = adGroupService.mutate(new AdGroupOperation[]{adGroupOperation}).getValue()[0];

      System.out.println("Ad group with name \"" + adGroup.getName() + "\" and id \""
          + adGroup.getId() + "\" was updated for the experiment.");

      // Set ad group criterion for the experiment.
      BiddableAdGroupCriterion adGroupCriterion = new BiddableAdGroupCriterion();
      adGroupCriterion.setCriterion(new Criterion(criterionId, null, null));
      adGroupCriterion.setAdGroupId(adGroupId);

      // Create experiment bid multiplier rule that will modify ad group bid for
      // the experiment.
      ManualCPCAdGroupCriterionExperimentBidMultiplier adGroupCriterionExperimentBidMultiplier =
          new ManualCPCAdGroupCriterionExperimentBidMultiplier();
      adGroupCriterionExperimentBidMultiplier.setMaxCpcMultiplier(new BidMultiplier(1.5, null));

      // Set experiment data to the ad group.
      BiddableAdGroupCriterionExperimentData adGroupCriterionExperimentData =
          new BiddableAdGroupCriterionExperimentData();
      adGroupCriterionExperimentData.setExperimentId(experimentId);
      adGroupCriterionExperimentData.setExperimentDeltaStatus(ExperimentDeltaStatus.MODIFIED);
      adGroupCriterionExperimentData
          .setExperimentBidMultiplier(adGroupCriterionExperimentBidMultiplier);
      adGroupCriterion.setExperimentData(adGroupCriterionExperimentData);

      // Create operation.
      AdGroupCriterionOperation adGroupCriterionOperation = new AdGroupCriterionOperation();
      adGroupCriterionOperation.setOperand(adGroupCriterion);
      adGroupCriterionOperation.setOperator(Operator.SET);

      // Update ad group criterion.
      adGroupCriterion =
          (BiddableAdGroupCriterion) adGroupCriterionService.mutate(
              new AdGroupCriterionOperation[] {adGroupCriterionOperation}).getValue()[0];

      System.out.println("Ad group criterion with ad group id \"" + adGroupCriterion.getAdGroupId()
          + "\" and criterion id \"" + adGroupCriterion.getCriterion().getId()
          + "\" was updated for the experiment.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
