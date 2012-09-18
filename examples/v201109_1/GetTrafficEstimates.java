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
import com.google.api.adwords.v201109_1.cm.Criterion;
import com.google.api.adwords.v201109_1.cm.Keyword;
import com.google.api.adwords.v201109_1.cm.KeywordMatchType;
import com.google.api.adwords.v201109_1.cm.Language;
import com.google.api.adwords.v201109_1.cm.Location;
import com.google.api.adwords.v201109_1.cm.Money;
import com.google.api.adwords.v201109_1.o.AdGroupEstimateRequest;
import com.google.api.adwords.v201109_1.o.CampaignEstimateRequest;
import com.google.api.adwords.v201109_1.o.KeywordEstimate;
import com.google.api.adwords.v201109_1.o.KeywordEstimateRequest;
import com.google.api.adwords.v201109_1.o.TrafficEstimatorResult;
import com.google.api.adwords.v201109_1.o.TrafficEstimatorSelector;
import com.google.api.adwords.v201109_1.o.TrafficEstimatorServiceInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This example gets keyword traffic estimates.
 *
 * Tags: TrafficEstimatorService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetTrafficEstimates {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the TrafficEstimatorService.
      TrafficEstimatorServiceInterface trafficEstimatorService =
          user.getService(AdWordsService.V201109_1.TRAFFIC_ESTIMATOR_SERVICE);

      // Create keywords. Up to 2000 keywords can be passed in a single request.
      List<Keyword> keywords = new ArrayList<Keyword>();
      keywords.add(new Keyword(null, null, null, "mars cruise", KeywordMatchType.BROAD));
      keywords.add(new Keyword(null, null, null, "cheap cruise", KeywordMatchType.PHRASE));
      keywords.add(new Keyword(null, null, null, "cruise", KeywordMatchType.EXACT));

      // Create a keyword estimate request for each keyword.
      List<KeywordEstimateRequest> keywordEstimateRequests =
          new ArrayList<KeywordEstimateRequest>();
      for (Keyword keyword : keywords) {
        KeywordEstimateRequest keywordEstimateRequest = new KeywordEstimateRequest();
        keywordEstimateRequest.setKeyword(keyword);
        keywordEstimateRequests.add(keywordEstimateRequest);
      }

      // Add a negative keyword to the traffic estimate.
      KeywordEstimateRequest negativeKeywordEstimateRequest = new KeywordEstimateRequest();
      negativeKeywordEstimateRequest.setKeyword(new Keyword(null, null, null, "hiking tour",
          KeywordMatchType.BROAD));
      negativeKeywordEstimateRequest.setIsNegative(true);
      keywordEstimateRequests.add(negativeKeywordEstimateRequest);

      // Create ad group estimate requests.
      List<AdGroupEstimateRequest> adGroupEstimateRequests =
          new ArrayList<AdGroupEstimateRequest>();
      AdGroupEstimateRequest adGroupEstimateRequest = new AdGroupEstimateRequest();
      adGroupEstimateRequest.setKeywordEstimateRequests(
          keywordEstimateRequests.toArray(new KeywordEstimateRequest[]{}));
      adGroupEstimateRequest.setMaxCpc(new Money(null, 1000000L));
      adGroupEstimateRequests.add(adGroupEstimateRequest);

      // Create campaign estimate requests.
      List<CampaignEstimateRequest> campaignEstimateRequests =
        new ArrayList<CampaignEstimateRequest>();
      CampaignEstimateRequest campaignEstimateRequest = new CampaignEstimateRequest();
      campaignEstimateRequest.setAdGroupEstimateRequests(
          adGroupEstimateRequests.toArray(new AdGroupEstimateRequest[]{}));
      Location unitedStates = new Location();
      unitedStates.setId(2840L);
      Language english = new Language();
      english.setId(1000L);
      campaignEstimateRequest.setCriteria(new Criterion[]{unitedStates, english});
      campaignEstimateRequests.add(campaignEstimateRequest);

      // Create selector.
      TrafficEstimatorSelector selector = new TrafficEstimatorSelector();
      selector.setCampaignEstimateRequests(
          campaignEstimateRequests.toArray(new CampaignEstimateRequest[]{}));

      // Get traffic estimates.
      TrafficEstimatorResult result = trafficEstimatorService.get(selector);

      // Display traffic estimates.
      if (result != null && result.getCampaignEstimates() != null) {
        KeywordEstimate[] keywordEstimates =
            result.getCampaignEstimates()[0].getAdGroupEstimates()[0].getKeywordEstimates();
        for (int i = 0; i < keywordEstimates.length; i++) {
          Keyword keyword = keywordEstimateRequests.get(i).getKeyword();
          KeywordEstimate keywordEstimate = keywordEstimates[i];
          if (Boolean.TRUE.equals(keywordEstimateRequests.get(i).getIsNegative())) {
            continue;
          }

          // Find the mean of the min and max values.
          double meanAverageCpc = (keywordEstimate.getMin().getAverageCpc().getMicroAmount()
              + keywordEstimate.getMax().getAverageCpc().getMicroAmount()) / 2.0;
          double meanAveragePosition = (keywordEstimate.getMin().getAveragePosition()
              + keywordEstimate.getMax().getAveragePosition()) / 2.0;
          double meanClicks = (keywordEstimate.getMin().getClicksPerDay()
              + keywordEstimate.getMax().getClicksPerDay()) / 2.0;
          double meanTotalCost = (keywordEstimate.getMin().getTotalCost().getMicroAmount()
              + keywordEstimate.getMax().getTotalCost().getMicroAmount()) / 2.0;

          System.out.println(String.format(
              "Results for the keyword with text '%s' and match type '%s':",
              keyword.getText(), keyword.getMatchType()));
          System.out.printf("\tEstimated average CPC: %.2f\n", meanAverageCpc);
          System.out.printf("\tEstimated ad position: %.2f\n", meanAveragePosition);
          System.out.printf("\tEstimated daily clicks: %.2f\n", meanClicks);
          System.out.printf("\tEstimated daily cost: %.2f\n\n", meanTotalCost);
        }
      } else {
        System.out.println("No traffic estimates were returned.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
