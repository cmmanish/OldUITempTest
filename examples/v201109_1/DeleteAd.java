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
import com.google.api.adwords.v201109_1.cm.Ad;
import com.google.api.adwords.v201109_1.cm.AdGroupAd;
import com.google.api.adwords.v201109_1.cm.AdGroupAdOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupAdReturnValue;
import com.google.api.adwords.v201109_1.cm.AdGroupAdServiceInterface;
import com.google.api.adwords.v201109_1.cm.Operator;

/**
 * This example deletes an ad using the 'REMOVE' operator. To get ads,
 * run GetAllAds.java.
 *
 * Tags: AdGroupAdService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class DeleteAd {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupAdService.
      AdGroupAdServiceInterface adGroupAdService =
          user.getService(AdWordsService.V201109_1.ADGROUP_AD_SERVICE);

      long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");
      long adId = Long.parseLong("INSERT_AD_ID_HERE");

      // Create base class ad to avoid setting type specific fields.
      Ad ad = new Ad();
      ad.setId(adId);

      // Create ad group ad.
      AdGroupAd adGroupAd = new AdGroupAd();
      adGroupAd.setAdGroupId(adGroupId);
      adGroupAd.setAd(ad);

      // Create operations.
      AdGroupAdOperation operation = new AdGroupAdOperation();
      operation.setOperand(adGroupAd);
      operation.setOperator(Operator.REMOVE);

      AdGroupAdOperation[] operations = new AdGroupAdOperation[] {operation};

      // Delete ad.
      AdGroupAdReturnValue result = adGroupAdService.mutate(operations);

      // Display ads.
      if (result != null && result.getValue() != null) {
        for (AdGroupAd adGroupAdResult : result.getValue()) {
          System.out.println("Ad with id \"" + adGroupAdResult.getAd().getId()
              + "\" and type \"" + adGroupAdResult.getAd().getAdType()
              + "\" was deleted.");
        }
      } else {
        System.out.println("No ads were deleted.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
