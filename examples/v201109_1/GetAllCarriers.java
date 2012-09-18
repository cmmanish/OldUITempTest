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
import com.google.api.adwords.v201109_1.cm.Carrier;
import com.google.api.adwords.v201109_1.cm.ConstantDataServiceInterface;

/**
 * This example gets all CarrierCriterion.
 *
 * Tags: ConstantDataService.getCarrierCriterion
 *
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class GetAllCarriers {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ConstantDataService.
      ConstantDataServiceInterface constantDataService =
          user.getService(AdWordsService.V201109_1.CONSTANT_DATA_SERVICE);

      // Get all carriers.
      Carrier[] carriers = constantDataService.getCarrierCriterion();

      // Display results.
      for (Carrier carrier : carriers) {
        System.out.printf("Carrier with name '%s', ID '%d', and country code '%s' was found.\n",
            carrier.getName(), carrier.getId(), carrier.getCountryCode());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
