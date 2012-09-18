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
import com.google.api.adwords.v201109_1.cm.DateRange;
import com.google.api.adwords.v201109_1.cm.Operator;
import com.google.api.adwords.v201109_1.info.ApiUsageInfo;
import com.google.api.adwords.v201109_1.info.ApiUsageType;
import com.google.api.adwords.v201109_1.info.InfoSelector;
import com.google.api.adwords.v201109_1.info.InfoServiceInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This example retrieves the cost, in API units per operation, of the given
 * method at today's rate for a given developer token. This example must be
 * run as the MCC user that owns the developer token.
 *
 * Tags: InfoService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetMethodCost {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the InfoService.
      InfoServiceInterface infoService =
          user.getService(AdWordsService.V201109_1.INFO_SERVICE);

      // Create selector.
      InfoSelector selector = new InfoSelector();
      selector.setApiUsageType(ApiUsageType.METHOD_COST);
      selector.setServiceName("AdGroupService");
      selector.setMethodName("mutate");
      selector.setOperator(Operator.SET);
      // For today's date.
      selector.setDateRange(new DateRange(new SimpleDateFormat("yyyyMMdd").format(new Date()),
          new SimpleDateFormat("yyyyMMdd").format(new Date())));

      // Get ap usage info.
      ApiUsageInfo apiUsageInfo = infoService.get(selector);

      // Display api usage info.
      if (apiUsageInfo != null) {
        System.out.println("The cost of the method in API units is \"" + apiUsageInfo.getCost()
            + "\".");
      } else {
        System.out.println("No api usage information was found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
