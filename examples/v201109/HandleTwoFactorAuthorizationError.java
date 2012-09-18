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

import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AuthToken;
import com.google.api.adwords.lib.AuthTokenException;

/**
 * This example demonstrates how to handle two-factor authorization errors.
 *
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class HandleTwoFactorAuthorizationError {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      String loginEmail = "2steptester@gmail.com";
      String password = "testaccount";

      System.out.println(new AuthToken(loginEmail, password).getAuthToken());
    } catch (AuthTokenException e) {
      if (e.getErrorCode().contains("InvalidSecondFactor")) {
        System.out
            .println("The user has enabled two factor authentication in this account. Have the "
                + "user generate an application-specific password to make calls against the "
                + "AdWords API. See "
                + "http://adwordsapi.blogspot.com/2011/02/authentication-changes-with-2-step.html"
                + " for more details.");
      } else {
        e.printStackTrace();
      }
    }
  }
}
