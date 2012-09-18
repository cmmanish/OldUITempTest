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
import com.google.api.adwords.v201109_1.info.ApiUsageInfo;
import com.google.api.adwords.v201109_1.info.ApiUsageType;
import com.google.api.adwords.v201109_1.info.InfoSelector;
import com.google.api.adwords.v201109_1.info.InfoServiceInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This example gets the number of API operations that have been performed this
 * month for a given developer token. This example must be run as the MCC user
 * that owns the developer token.
 *
 * Tags: InfoService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetOperationCount {
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
      selector.setApiUsageType(ApiUsageType.OPERATION_COUNT);
      // From the start of the month until today.
      selector.setDateRange(new DateRange(new SimpleDateFormat("yyyyMM01").format(new Date()),
          new SimpleDateFormat("yyyyMMdd").format(new Date())));

      // Get api usage info.
      ApiUsageInfo apiUsageInfo = infoService.get(selector);

      // Display api usage info.
      if (apiUsageInfo != null) {
        System.out.println("The total number of API operations performed this month is \""
            + apiUsageInfo.getCost() + "\".");
      } else {
        System.out.println("No api usage information was found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
