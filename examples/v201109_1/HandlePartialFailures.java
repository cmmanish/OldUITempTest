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
import com.google.api.adwords.v201109_1.cm.AdGroupCriterion;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionReturnValue;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionServiceInterface;
import com.google.api.adwords.v201109_1.cm.ApiError;
import com.google.api.adwords.v201109_1.cm.BiddableAdGroupCriterion;
import com.google.api.adwords.v201109_1.cm.Keyword;
import com.google.api.adwords.v201109_1.cm.KeywordMatchType;
import com.google.api.adwords.v201109_1.cm.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This example demonstrates how to handle partial failures.
 *
 * Tags: AdGroupCriterionService.mutate
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class HandlePartialFailures {
  private static Pattern operationIndexPattern = Pattern.compile("^.*operations\\[(\\d+)\\].*$");

  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Set partial failure flag.
      user.setUsePartialFailure(true);

      // Get the AdGroupCriterionService.
      AdGroupCriterionServiceInterface adGroupCriterionService =
          user.getService(AdWordsService.V201109_1.ADGROUP_CRITERION_SERVICE);

      long adGroupId = Long.parseLong("INSERT_ADGROUP_ID_HERE");

      List<AdGroupCriterionOperation> operations = new ArrayList<AdGroupCriterionOperation>();

      // Create keywords.
      String[] keywords =
          new String[] {"mars cruise", "inv@lid cruise", "venus cruise", "b(a)d keyword cruise"};
      for (String keywordText : keywords) {
        // Create keyword
        Keyword keyword = new Keyword();
        keyword.setText(keywordText);
        keyword.setMatchType(KeywordMatchType.BROAD);

        // Create biddable ad group criterion.
        BiddableAdGroupCriterion keywordBiddableAdGroupCriterion = new BiddableAdGroupCriterion();
        keywordBiddableAdGroupCriterion.setAdGroupId(adGroupId);
        keywordBiddableAdGroupCriterion.setCriterion(keyword);

        // Create operation.
        AdGroupCriterionOperation keywordAdGroupCriterionOperation =
            new AdGroupCriterionOperation();
        keywordAdGroupCriterionOperation.setOperand(keywordBiddableAdGroupCriterion);
        keywordAdGroupCriterionOperation.setOperator(Operator.ADD);
        operations.add(keywordAdGroupCriterionOperation);
      }

      // Add ad group criteria.
      AdGroupCriterionReturnValue result =
          adGroupCriterionService.mutate(operations.toArray(new AdGroupCriterionOperation[] {}));

      // Display results.
      if ((result != null) && (result.getValue() != null)) {
        for (AdGroupCriterion adGroupCriterionResult : result.getValue()) {
          if (adGroupCriterionResult.getCriterion() != null) {
            System.out.printf("Ad group criterion with ad group id '%d', and criterion id '%d', "
                + "and keyword '%s' was added.\n", adGroupCriterionResult.getAdGroupId(),
                adGroupCriterionResult.getCriterion().getId(),
                ((Keyword) adGroupCriterionResult.getCriterion()).getText());
          }
        }
      } else {
        System.out.println("No ad group criteria were added.");
      }

      if ((result != null) && (result.getPartialFailureErrors() != null)) {
        for (ApiError apiError : result.getPartialFailureErrors()) {
          Matcher matcher = operationIndexPattern.matcher(apiError.getFieldPath());
          if (matcher.matches()) {
            int operationIndex = Integer.parseInt(matcher.group(1));
            AdGroupCriterion adGroupCriterion = operations.get(operationIndex).getOperand();
            System.out.printf("Ad group criterion with ad group id '%d' and keyword '%s' "
                + "triggered a failure for the following reason: '%s'.\n",
                adGroupCriterion.getAdGroupId(),
                ((Keyword) adGroupCriterion.getCriterion()).getText(), apiError.getErrorString());
          } else {
            System.out.printf("A failure for the following reason: '%s' has occurred.\n",
                apiError.getErrorString());
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
