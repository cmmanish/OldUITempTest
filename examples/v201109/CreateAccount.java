// Copyright 2011, Google Inc. All Rights Reserved.
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
import com.google.api.adwords.v201109.cm.Operator;
import com.google.api.adwords.v201109.mcm.Account;
import com.google.api.adwords.v201109.mcm.CreateAccountOperation;
import com.google.api.adwords.v201109.mcm.CreateAccountServiceInterface;

/**
 * This example creates a new account under an MCC account. Note: this example
 * must be run using the credentials of an MCC account, and by default the new
 * account will only be accessible via the parent MCC account.
 *
 * Tags: CreateAccountService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class CreateAccount {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignService.
      CreateAccountServiceInterface createAccountService =
          user.getService(AdWordsService.V201109.CREATE_ACCOUNT_SERVICE);

      // Create account.
      Account account = new Account();
      account.setCurrencyCode("EUR");
      account.setDateTimeZone("Europe/London");

      // Create operations.
      CreateAccountOperation operation = new CreateAccountOperation();
      operation.setOperand(account);
      operation.setDescriptiveName("Account created with CreateAccountService");
      operation.setOperator(Operator.ADD);

      CreateAccountOperation[] operations = new CreateAccountOperation[] {operation};

      // Add account.
      Account[] result = createAccountService.mutate(operations);

      // Display accounts.
      for (Account accountResult : result) {
        System.out.println("Account with customer ID \"" + accountResult.getCustomerId()
            + "\" was created.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
