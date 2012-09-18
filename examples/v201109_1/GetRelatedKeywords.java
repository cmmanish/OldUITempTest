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
import com.google.api.adwords.lib.utils.MapUtils;
import com.google.api.adwords.v201109_1.cm.Keyword;
import com.google.api.adwords.v201109_1.cm.KeywordMatchType;
import com.google.api.adwords.v201109_1.cm.Paging;
import com.google.api.adwords.v201109_1.o.Attribute;
import com.google.api.adwords.v201109_1.o.AttributeType;
import com.google.api.adwords.v201109_1.o.CriterionAttribute;
import com.google.api.adwords.v201109_1.o.IdeaType;
import com.google.api.adwords.v201109_1.o.IntegerSetAttribute;
import com.google.api.adwords.v201109_1.o.KeywordMatchTypeSearchParameter;
import com.google.api.adwords.v201109_1.o.LongAttribute;
import com.google.api.adwords.v201109_1.o.RelatedToKeywordSearchParameter;
import com.google.api.adwords.v201109_1.o.RequestType;
import com.google.api.adwords.v201109_1.o.SearchParameter;
import com.google.api.adwords.v201109_1.o.TargetingIdea;
import com.google.api.adwords.v201109_1.o.TargetingIdeaPage;
import com.google.api.adwords.v201109_1.o.TargetingIdeaSelector;
import com.google.api.adwords.v201109_1.o.TargetingIdeaServiceInterface;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * This example gets keywords related to a seed keyword.
 *
 * Tags: TargetingIdeaService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetRelatedKeywords {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the TargetingIdeaService.
      TargetingIdeaServiceInterface targetingIdeaService =
          user.getService(AdWordsService.V201109_1.TARGETING_IDEA_SERVICE);

      // Create seed keyword.
      Keyword keyword = new Keyword();
      keyword.setText("mars cruise");
      keyword.setMatchType(KeywordMatchType.BROAD);

      // Create selector.
      TargetingIdeaSelector selector = new TargetingIdeaSelector();
      selector.setRequestType(RequestType.IDEAS);
      selector.setIdeaType(IdeaType.KEYWORD);
      selector.setRequestedAttributeTypes(new AttributeType[] {
          AttributeType.CRITERION, AttributeType.AVERAGE_TARGETED_MONTHLY_SEARCHES,
          AttributeType.CATEGORY_PRODUCTS_AND_SERVICES});

      // Set selector paging (required for targeting idea service).
      Paging paging = new Paging();
      paging.setStartIndex(0);
      paging.setNumberResults(10);
      selector.setPaging(paging);

      // Create related to keyword search parameter.
      RelatedToKeywordSearchParameter relatedToKeywordSearchParameter =
          new RelatedToKeywordSearchParameter();
      relatedToKeywordSearchParameter.setKeywords(new Keyword[] {keyword});

      // Create keyword match type search parameter to ensure unique results.
      KeywordMatchTypeSearchParameter keywordMatchTypeSearchParameter =
          new KeywordMatchTypeSearchParameter();
      keywordMatchTypeSearchParameter
          .setKeywordMatchTypes(new KeywordMatchType[] {KeywordMatchType.BROAD});

      selector.setSearchParameters(new SearchParameter[] {relatedToKeywordSearchParameter,
          keywordMatchTypeSearchParameter});

      // Get related keywords.
      TargetingIdeaPage page = targetingIdeaService.get(selector);

      // Display related keywords.
      if (page.getEntries() != null && page.getEntries().length > 0) {
        for (TargetingIdea targetingIdea : page.getEntries()) {
          Map<AttributeType, Attribute> data = MapUtils.toMap(targetingIdea.getData());
          keyword = (Keyword) ((CriterionAttribute) data.get(AttributeType.CRITERION)).getValue();
          IntegerSetAttribute categories =
              (IntegerSetAttribute) data.get(AttributeType.CATEGORY_PRODUCTS_AND_SERVICES);
          String categoriesString = "(none)";
          if (categories != null && categories.getValue() != null) {
            categoriesString = StringUtils.join(ArrayUtils.toObject(categories.getValue()), ", ");
          }
          Long averageMonthlySearches =
              ((LongAttribute) data.get(AttributeType.AVERAGE_TARGETED_MONTHLY_SEARCHES))
                  .getValue();
          System.out.println("Keyword with text '" + keyword.getText() + "', match type '"
              + keyword.getMatchType() + "' and average monthly search volume '"
              + averageMonthlySearches + "' was found with categories: " + categoriesString);
        }
      } else {
        System.out.println("No related keywords were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
