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
import com.google.api.adwords.v201109_1.cm.Experiment;
import com.google.api.adwords.v201109_1.cm.ExperimentPage;
import com.google.api.adwords.v201109_1.cm.ExperimentServiceInterface;
import com.google.api.adwords.v201109_1.cm.OrderBy;
import com.google.api.adwords.v201109_1.cm.Predicate;
import com.google.api.adwords.v201109_1.cm.PredicateOperator;
import com.google.api.adwords.v201109_1.cm.Selector;
import com.google.api.adwords.v201109_1.cm.SortOrder;

/**
 * This example gets all experiments in a campaign. To add an experiment, run
 * AddExperiment.java. To get campaigns, run GetAllCampaigns.java.
 *
 * Tags: ExperimentService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllExperiments {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ExperimentService.
      ExperimentServiceInterface experimentService =
          user.getService(AdWordsService.V201109_1.EXPERIMENT_SERVICE);

      Long campaignId = Long.parseLong("INSET_CAMPAIGN_ID_HERE");

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"Name", "Id", "ControlId", "AdGroupsCount",
          "AdGroupCriteriaCount", "AdGroupAdsCount"});
      selector.setOrdering(new OrderBy[] {new OrderBy("Name", SortOrder.ASCENDING)});

      // Create predicates.
      Predicate campaignIdPredicate =
          new Predicate("CampaignId", PredicateOperator.IN, new String[] {campaignId.toString()});
      selector.setPredicates(new Predicate[] {campaignIdPredicate});

      // Get all experiments.
      ExperimentPage page = experimentService.get(selector);

      if (page.getEntries() != null) {
        for(Experiment experiment : page.getEntries()) {
          System.out.println("Experiment with name \"" + experiment.getName() + "\", id \""
              + experiment.getId() + "\", and control id \"" + experiment.getControlId()
              + "\" was found.\nIt includes "
              + experiment.getExperimentSummaryStats().getAdGroupsCount() + " ad groups, "
              + experiment.getExperimentSummaryStats().getAdGroupCriteriaCount()
              + " criteria, and " + experiment.getExperimentSummaryStats().getAdGroupAdsCount()
              + " ads.");
        }
      } else {
        System.out.println ("No experiments were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
