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
import com.google.api.adwords.v201109_1.cm.CampaignAdExtension;
import com.google.api.adwords.v201109_1.cm.CampaignAdExtensionPage;
import com.google.api.adwords.v201109_1.cm.CampaignAdExtensionServiceInterface;
import com.google.api.adwords.v201109_1.cm.OrderBy;
import com.google.api.adwords.v201109_1.cm.Predicate;
import com.google.api.adwords.v201109_1.cm.PredicateOperator;
import com.google.api.adwords.v201109_1.cm.Selector;
import com.google.api.adwords.v201109_1.cm.SortOrder;

/**
 * This example gets all campaign ad extension for a campaign. To add a campaign
 * ad extension, run AddCampaignAdExtension.java. To get campaigns, run
 * GetAllCampaigns.java.
 *
 * Tags: CampaignAdExtensionService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllCampaignAdExtensions {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignAdExtensionService.
      CampaignAdExtensionServiceInterface campaignAdExtensionService =
          user.getService(AdWordsService.V201109_1.CAMPAIGN_AD_EXTENSION_SERVICE);

      Long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"AdExtensionId", "CampaignId"});
      selector.setOrdering(new OrderBy[] {new OrderBy("AdExtensionId", SortOrder.ASCENDING)});

      // Create predicates.
      Predicate campaignIdPredicate =
          new Predicate("CampaignId", PredicateOperator.IN, new String[] {campaignId.toString()});
      selector.setPredicates(new Predicate[] {campaignIdPredicate});

      // Get all campaign ad extensions.
      CampaignAdExtensionPage page = campaignAdExtensionService.get(selector);

      // Display campaign ad extensions.
      if (page.getEntries() != null && page.getEntries().length > 0) {
        for (CampaignAdExtension campaignAdExtension : page.getEntries()) {
          System.out.println("Campaign ad extension with campaign id \""
              + campaignAdExtension.getCampaignId() + "\", ad extension id \""
              + campaignAdExtension.getAdExtension().getId() + "\", and type \""
              + campaignAdExtension.getAdExtension().getAdExtensionType()
              + "\" was found.");
        }
      } else {
        System.out.println("No campaign ad extensions were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
