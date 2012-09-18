// Copyright 2011, Google Inc. All Rights Reserved.
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

package v201109;

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.v201109.cm.AdParam;
import com.google.api.adwords.v201109.cm.AdParamOperation;
import com.google.api.adwords.v201109.cm.AdParamServiceInterface;
import com.google.api.adwords.v201109.cm.Operator;

/**
 * This example sets ad parameters for a keyword ad group criterion. To get ad
 * group criteria, run GetAllAdGroupCriteria.java.
 *
 * Tags: AdParamService.mutate
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class SetAdParams {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdParamService.
      AdParamServiceInterface adParamService =
          user.getService(AdWordsService.V201109.AD_PARAM_SERVICE);

      long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");
      long keywordId = Long.parseLong("INSERT_KEYWORD_ID_HERE");

      // Create ad params.
      AdParam adParam1 = new AdParam();
      adParam1.setAdGroupId(adGroupId);
      adParam1.setCriterionId(keywordId);
      adParam1.setInsertionText("100");
      adParam1.setParamIndex(1);

      AdParam adParam2 = new AdParam();
      adParam2.setAdGroupId(adGroupId);
      adParam2.setCriterionId(keywordId);
      adParam2.setInsertionText("$40");
      adParam2.setParamIndex(2);

      // Create operations.
      AdParamOperation adParamOperation1 = new AdParamOperation();
      adParamOperation1.setOperand(adParam1);
      adParamOperation1.setOperator(Operator.SET);

      AdParamOperation adParamOperation2 = new AdParamOperation();
      adParamOperation2.setOperand(adParam2);
      adParamOperation2.setOperator(Operator.SET);

      AdParamOperation[] operations = new AdParamOperation[] {adParamOperation1, adParamOperation2};

      // Set ad parameters.
      AdParam[] adParams = adParamService.mutate(operations);

      // Display ad parameters.
      if (adParams != null) {
        for (AdParam adParam : adParams) {
          System.out.println("Ad parameter with ad group id \"" + adParam.getAdGroupId()
              + "\", criterion id \"" + adParam.getCriterionId()
              + "\", insertion text \"" + adParam.getInsertionText()
              + "\", and parameter index \"" + adParam.getParamIndex()
              + "\" was set.");
        }
      } else {
        System.out.println("No ad parameters were set.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
