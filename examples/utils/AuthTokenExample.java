// Copyright 2010 Google Inc. All Rights Reserved.
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

package utils;

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.lib.AuthToken;
import com.google.api.adwords.lib.AuthTokenException;
import com.google.api.adwords.v201109.cm.ApiError;
import com.google.api.adwords.v201109.cm.ApiException;
import com.google.api.adwords.v201109.cm.AuthenticationError;
import com.google.api.adwords.v201109.cm.AuthenticationErrorReason;
import com.google.api.adwords.v201109.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109.cm.Selector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

/**
 * This example shows how to recover from client login CAPTCHAs and token
 * expiration.
 * <p>
 * See <a href="http://code.google.com/apis/adwords/v2009/docs/#authtoken">
 * http://code.google.com/apis/adwords/v2009/docs/#authtoken</a>
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AuthTokenExample {
  public static void main(String[] args) throws IOException, AuthTokenException, ServiceException {
    String email = "INSERT_EMAIL_ADDRESS_HERE";
    String password = "INSERT_PASSWORD_HERE";

    // Create AuthToken class from AdWordsUser object.
    AuthToken authToken = new AuthToken(email, password);

    // Get AdWordsUser from "~/adwords.properties". In the properties file,
    // you will only need to set the developerToken, useragent, or clientId (if
    // needed).
    AdWordsUser user = new AdWordsUser();

    // Uncomment to cause a CAPTCHA required failure.
    // causeCaptchaError(authToken);

    String authTokenString = "";

    // Handle CAPTCHA error.
    try {
      // Get auth token string.
      authTokenString = authToken.getAuthToken();
    } catch (AuthTokenException e) {
      if (e.getErrorCode() != null && e.getErrorCode().equals("CaptchaRequired")) {
        // Try getting the auth token again but with a captcha.
        System.out.println("Go here and read the captcha: " + e.getCaptchaInfo().getCaptchaUrl());
        System.out.print("Answer is: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String answer = in.readLine().trim();
        authTokenString =
            new AuthToken(authToken, e.getCaptchaInfo().getCaptchaToken(), answer).getAuthToken();
      } else {
        throw e;
      }
    }


    // Set the auth token in the user so that it will not be generated when
    // user.getService() is called.
    user.setAuthToken(authTokenString);

    // Handle possible token expiration. You can cause an expiration by
    // pausing this code and changing the password of the account before the
    // first makeApiRequest() is called. If you do so, change your password back
    // before you regenerate the auth token again.
    try {
      makeApiRequest(user);
    } catch (ApiException e) {
      for (ApiError error : e.getErrors()) {
        if (error instanceof AuthenticationError) {
          AuthenticationError authError = (AuthenticationError) error;
          if (authError.getReason() == AuthenticationErrorReason.GOOGLE_ACCOUNT_COOKIE_INVALID) {
            // Try to regenerate auth token here again in case it expired.
            user.setAuthToken(authToken.getAuthToken());
            makeApiRequest(user);
          } else {
            System.err.println("Service call failed for authentication reason: "
                + authError.getReason());
          }
        }
      }
    }
  }

  /**
   * Makes an API request.
   *
   * @param user the {@code AdWordsUser} to make the request against
   * @throws ServiceException if the service could not be generated
   * @throws RemoteException if the request could not be completed
   * @throws ApiException if there was an {@code ApiException} during the
   *     request
   */
  private static void makeApiRequest(AdWordsUser user) throws ServiceException, RemoteException,
      ApiException {
    // Service will use the auth token which was set by user.setAuthToken().
    CampaignServiceInterface campaignService =
        user.getService(AdWordsService.V201109.CAMPAIGN_SERVICE);
    campaignService.get(new Selector());
    System.out.println("Service call was successful.");
  }

  /**
   * Causes a CaptchaRequired error. For this to occur, the email and password
   * must be correct; CAPTCHAS are thrown when too many successful requests
   * occur.
   *
   * @param authToken the auth token
   */
  private static void causeCaptchaError(AuthToken authToken) {
    boolean captchaReceived = false;
    while (!captchaReceived) {
      try {
        authToken.getAuthToken();
      } catch (AuthTokenException e) {
        System.err.println(e.getMessage());
        captchaReceived = e.getCaptchaInfo() != null;
      }
    }
  }
}
