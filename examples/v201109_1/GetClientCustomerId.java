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
import com.google.api.adwords.v201109_1.cm.DateRange;
import com.google.api.adwords.v201109_1.info.ApiUsageInfo;
import com.google.api.adwords.v201109_1.info.ApiUsageRecord;
import com.google.api.adwords.v201109_1.info.ApiUsageType;
import com.google.api.adwords.v201109_1.info.InfoSelector;
import com.google.api.adwords.v201109_1.info.InfoServiceInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This example illustrates how to find a client customer ID for a client email.
 * We recommend to use this script as a one off to convert your identifiers to
 * IDs and store them for future use.
 *
 * Tags: InfoService.get
 *
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class GetClientCustomerId {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser().generateClientAdWordsUser(null);

      // Get the InfoService.
      InfoServiceInterface infoService = user.getService(AdWordsService.V201109_1.INFO_SERVICE);

      // ClientEmails to look up clientCustomerId for.
      String[] clientEmails = new String[] {"INSERT_EMAIL_ADDRESS_HERE"};

      // Create selector.
      InfoSelector selector = new InfoSelector();
      selector.setApiUsageType(ApiUsageType.UNIT_COUNT_FOR_CLIENTS);
      String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
      selector.setDateRange(new DateRange(today, today));
      selector.setClientEmails(clientEmails);
      selector.setIncludeSubAccounts(true);

      // Get api usage info.
      ApiUsageInfo apiUsageInfo = infoService.get(selector);
      for (ApiUsageRecord record : apiUsageInfo.getApiUsageRecords()) {
        System.out.printf("Client with email '%s' has ID '%d'.\n", record.getClientEmail(),
            record.getClientCustomerId());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
