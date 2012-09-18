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
import com.google.api.adwords.v201109_1.cm.Paging;
import com.google.api.adwords.v201109_1.cm.Placement;
import com.google.api.adwords.v201109_1.o.Attribute;
import com.google.api.adwords.v201109_1.o.AttributeType;
import com.google.api.adwords.v201109_1.o.CriterionAttribute;
import com.google.api.adwords.v201109_1.o.IdeaType;
import com.google.api.adwords.v201109_1.o.PlacementTypeAttribute;
import com.google.api.adwords.v201109_1.o.RelatedToUrlSearchParameter;
import com.google.api.adwords.v201109_1.o.RequestType;
import com.google.api.adwords.v201109_1.o.SearchParameter;
import com.google.api.adwords.v201109_1.o.SiteConstantsPlacementType;
import com.google.api.adwords.v201109_1.o.TargetingIdea;
import com.google.api.adwords.v201109_1.o.TargetingIdeaPage;
import com.google.api.adwords.v201109_1.o.TargetingIdeaSelector;
import com.google.api.adwords.v201109_1.o.TargetingIdeaServiceInterface;

import java.util.Map;

/**
 * This example gets related placements.
 *
 * Tags: TargetingIdeaService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetRelatedPlacements {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the TargetingIdeaService.
      TargetingIdeaServiceInterface targetingIdeaService =
          user.getService(AdWordsService.V201109_1.TARGETING_IDEA_SERVICE);

      // Create seed URL.
      String url = "mars.google.com";

      // Create selector.
      TargetingIdeaSelector selector = new TargetingIdeaSelector();
      selector.setRequestType(RequestType.IDEAS);
      selector.setIdeaType(IdeaType.PLACEMENT);
      selector.setRequestedAttributeTypes(new AttributeType[] {AttributeType.CRITERION,
          AttributeType.PLACEMENT_TYPE});

      // Set selector paging (required for targeting idea service).
      Paging paging = new Paging();
      paging.setStartIndex(0);
      paging.setNumberResults(10);
      selector.setPaging(paging);

      // Create related to URL search parameter.
      RelatedToUrlSearchParameter relatedToUrlSearchParameter = new RelatedToUrlSearchParameter();
      relatedToUrlSearchParameter.setUrls(new String[] {url});
      relatedToUrlSearchParameter.setIncludeSubUrls(false);
      selector.setSearchParameters(new SearchParameter[] {relatedToUrlSearchParameter});

      // Get related placements.
      TargetingIdeaPage page = targetingIdeaService.get(selector);

      // Display related placements.
      if (page.getEntries() != null && page.getEntries().length > 0) {
        for (TargetingIdea targetingIdea : page.getEntries()) {
          Map<AttributeType, Attribute> data = MapUtils.toMap(targetingIdea.getData());
          Placement placement =
              (Placement) ((CriterionAttribute) data.get(AttributeType.CRITERION)).getValue();
          SiteConstantsPlacementType placementType =
              ((PlacementTypeAttribute) data.get(AttributeType.PLACEMENT_TYPE)).getValue();
          System.out.println("Placement with url '" + placement.getUrl() + "' and type '"
              + placementType.toString() + "' was found.");
        }
      } else {
        System.out.println("No related placements were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
