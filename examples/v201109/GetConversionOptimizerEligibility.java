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
import com.google.api.adwords.v201109.cm.Campaign;
import com.google.api.adwords.v201109.cm.CampaignPage;
import com.google.api.adwords.v201109.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109.cm.Predicate;
import com.google.api.adwords.v201109.cm.PredicateOperator;
import com.google.api.adwords.v201109.cm.Selector;

import org.apache.commons.lang.StringUtils;

/**
 * This example shows how to check for conversion optimizer eligibility by
 * examining the conversionOptimizerEligibility field of the Campaign.
 *
 * Tags: CampaignService.mutate
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetConversionOptimizerEligibility {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the validation CampaignService.
      CampaignServiceInterface campaignService =
          user.getService(AdWordsService.V201109.CAMPAIGN_SERVICE);

      Long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"Id", "Name", "Eligible", "RejectionReasons"});

      // Create predicates.
      Predicate campaignIdPredicate =
          new Predicate("CampaignId", PredicateOperator.IN, new String[] {campaignId.toString()});
      selector.setPredicates(new Predicate[] {campaignIdPredicate});

      // Get campaigns.
      CampaignPage page = campaignService.get(selector);

      // Display campaigns.
      if (page.getEntries() != null) {
        for (Campaign campaign : page.getEntries()) {
          if (campaign.getConversionOptimizerEligibility().getEligible()) {
            System.out.printf("Campaign with name '%s' and id '%d' is eligible to use "
                + "conversion optimizer.\n", campaign.getName(), campaign.getId());
          } else {
            System.out.printf("Campaign with name '%s' and id '%d' is not eligible to use "
                + "conversion optimizer for the reasons: %s.\n", campaign.getName(),
                campaign.getId(), StringUtils.join(
                    campaign.getConversionOptimizerEligibility().getRejectionReasons(), ", "));
          }
        }
      } else {
        System.out.println("No campaigns were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
