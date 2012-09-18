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
import com.google.api.adwords.v201109_1.cm.Paging;
import com.google.api.adwords.v201109_1.mcm.Alert;
import com.google.api.adwords.v201109_1.mcm.AlertPage;
import com.google.api.adwords.v201109_1.mcm.AlertQuery;
import com.google.api.adwords.v201109_1.mcm.AlertSelector;
import com.google.api.adwords.v201109_1.mcm.AlertServiceInterface;
import com.google.api.adwords.v201109_1.mcm.AlertSeverity;
import com.google.api.adwords.v201109_1.mcm.AlertType;
import com.google.api.adwords.v201109_1.mcm.ClientSpec;
import com.google.api.adwords.v201109_1.mcm.FilterSpec;
import com.google.api.adwords.v201109_1.mcm.TriggerTimeSpec;

/**
 * This example gets all alerts for all clients of an MCC account. The effective
 * user (clientCustomerId or authToken) must be an MCC user to
 * get results.
 *
 * Tags: AlertService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllAlerts {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AlertService.
      AlertServiceInterface alertService =
          user.getService(AdWordsService.V201109_1.ALERT_SERVICE);

      // Create alert query.
      AlertQuery alertQuery = new AlertQuery();
      alertQuery.setClientSpec(ClientSpec.ALL);
      alertQuery.setFilterSpec(FilterSpec.ALL);
      alertQuery.setTypes(new AlertType[] {AlertType.ACCOUNT_BUDGET_BURN_RATE,
          AlertType.ACCOUNT_BUDGET_ENDING, AlertType.ACCOUNT_ON_TARGET, AlertType.CAMPAIGN_ENDED,
          AlertType.CAMPAIGN_ENDING, AlertType.CREDIT_CARD_EXPIRING, AlertType.DECLINED_PAYMENT,
          AlertType.MANAGER_LINK_PENDING, AlertType.MISSING_BANK_REFERENCE_NUMBER,
          AlertType.PAYMENT_NOT_ENTERED, AlertType.TV_ACCOUNT_BUDGET_ENDING,
          AlertType.TV_ACCOUNT_ON_TARGET, AlertType.TV_ZERO_DAILY_SPENDING_LIMIT,
          AlertType.USER_INVITE_ACCEPTED, AlertType.USER_INVITE_PENDING,
          AlertType.ZERO_DAILY_SPENDING_LIMIT});
      alertQuery.setSeverities(new AlertSeverity[] {AlertSeverity.GREEN, AlertSeverity.YELLOW,
          AlertSeverity.RED});
      alertQuery.setTriggerTimeSpec(TriggerTimeSpec.ALL_TIME);

      // Create selector.
      AlertSelector selector = new AlertSelector();
      selector.setQuery(alertQuery);
      selector.setPaging(new Paging(0, 100));

      // Get all alerts.
      AlertPage page = alertService.get(selector);

      // Display alerts.
      if (page.getEntries() != null && page.getEntries().length > 0) {
        for (Alert alert : page.getEntries()) {
          System.out.printf(
              "Alert of type '%s' and severity '%s' for account '%d' was found.\n",
              alert.getAlertType(), alert.getAlertSeverity(),
              alert.getClientCustomerId());
        }
      } else {
        System.out.println("No alerts were found.\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
