// Copyright 2011 Google Inc. All Rights Reserved.
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
import com.google.api.adwords.lib.utils.MapUtils;
import com.google.api.adwords.v201109.cm.Paging;
import com.google.api.adwords.v201109.o.Attribute;
import com.google.api.adwords.v201109.o.BulkOpportunityPage;
import com.google.api.adwords.v201109.o.BulkOpportunitySelector;
import com.google.api.adwords.v201109.o.BulkOpportunityServiceInterface;
import com.google.api.adwords.v201109.o.DoubleAttribute;
import com.google.api.adwords.v201109.o.IntegerAttribute;
import com.google.api.adwords.v201109.o.KeywordAttribute;
import com.google.api.adwords.v201109.o.LongAttribute;
import com.google.api.adwords.v201109.o.Opportunity;
import com.google.api.adwords.v201109.o.OpportunityAttributeType;
import com.google.api.adwords.v201109.o.OpportunityIdea;
import com.google.api.adwords.v201109.o.OpportunityIdeaType;
import com.google.api.adwords.v201109.o.OpportunityIdeaTypeAttribute;

import java.util.Map;

/**
 * This example gets all the keyword opportunities for the account.
 *
 * Tags: BulkOpportunityService.get
 *
 * @category adx-exclude
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class GetKeywordOpportunities {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the BulkOpportunityService.
      BulkOpportunityServiceInterface service =
          user.getService(AdWordsService.V201109.BULK_OPPORTUNITY_SERVICE);

      // Create selector.
      BulkOpportunitySelector selector = new BulkOpportunitySelector();
      selector.setRequestedAttributeTypes(new OpportunityAttributeType[] {
          OpportunityAttributeType.ADGROUP_ID, OpportunityAttributeType.AVERAGE_MONTHLY_SEARCHES,
          OpportunityAttributeType.CAMPAIGN_ID, OpportunityAttributeType.IDEA_TYPE,
          OpportunityAttributeType.KEYWORD});
      selector.setIdeaTypes(new OpportunityIdeaType[] {OpportunityIdeaType.KEYWORD});

      // Set selector paging.
      Paging paging = new Paging();
      paging.setStartIndex(0);
      paging.setNumberResults(10);
      selector.setPaging(paging);

      // Get keyword opportunities.
      BulkOpportunityPage page = service.get(selector);

      // Display related keyword opportunities.
      if (page != null && page.getEntries() != null) {
        for (Opportunity entry : page.getEntries()) {
          for (OpportunityIdea idea : entry.getOpportunityIdeas()) {
            Map<OpportunityAttributeType, Attribute> data = MapUtils.toMap(idea.getData());
            OpportunityIdeaType ideaType =
                ((OpportunityIdeaTypeAttribute) data.get(OpportunityAttributeType.IDEA_TYPE))
                    .getValue();
            String keywordText =
                ((KeywordAttribute) data.get(OpportunityAttributeType.KEYWORD)).getValue()
                    .getText();
            Long campaignId =
                ((LongAttribute) data.get(OpportunityAttributeType.CAMPAIGN_ID)).getValue();
            Long adGroupId =
                ((LongAttribute) data.get(OpportunityAttributeType.ADGROUP_ID)).getValue();
            Integer averageMonthlySearches =
                ((IntegerAttribute) data.get(OpportunityAttributeType.AVERAGE_MONTHLY_SEARCHES))
                    .getValue();
            System.out.printf("%s opportunity for Campaign %d and AdGroup %d:"
                + " %s with %d average monthly searches\n", ideaType, campaignId, adGroupId,
                keywordText, averageMonthlySearches);
          }
        }
      } else {
        System.out.println("No keyword opportunities were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
