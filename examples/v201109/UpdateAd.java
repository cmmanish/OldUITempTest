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
import com.google.api.adwords.v201109.cm.Ad;
import com.google.api.adwords.v201109.cm.AdGroupAd;
import com.google.api.adwords.v201109.cm.AdGroupAdOperation;
import com.google.api.adwords.v201109.cm.AdGroupAdReturnValue;
import com.google.api.adwords.v201109.cm.AdGroupAdServiceInterface;
import com.google.api.adwords.v201109.cm.AdGroupAdStatus;
import com.google.api.adwords.v201109.cm.Operator;

/**
 * This example updates an ad by setting the status to 'PAUSED'. To get ads,
 * run GetAllAds.java.
 *
 * Tags: AdGroupAdService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class UpdateAd {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupAdService.
      AdGroupAdServiceInterface adGroupAdService =
          user.getService(AdWordsService.V201109.ADGROUP_AD_SERVICE);

      long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");
      long adId = Long.parseLong("INSERT_AD_ID_HERE");

      // Create ad with updated status.
      Ad ad = new Ad();
      ad.setId(adId);

      AdGroupAd adGroupAd = new AdGroupAd();
      adGroupAd.setAdGroupId(adGroupId);
      adGroupAd.setAd(ad);
      adGroupAd.setStatus(AdGroupAdStatus.PAUSED);

      // Create operations.
      AdGroupAdOperation operation = new AdGroupAdOperation();
      operation.setOperand(adGroupAd);
      operation.setOperator(Operator.SET);

      AdGroupAdOperation[] operations = new AdGroupAdOperation[] {operation};

      // Update ad.
      AdGroupAdReturnValue result = adGroupAdService.mutate(operations);

      // Display ads.
      if (result != null && result.getValue() != null) {
        for (AdGroupAd adGroupAdResult : result.getValue()) {
          System.out.println("Ad with id \"" + adGroupAdResult.getAd().getId()
              + "\", type \"" + adGroupAdResult.getAd().getAdType()
              + "\", and status \"" + adGroupAdResult.getStatus() + "\" was updated.");
        }
      } else {
        System.out.println("No ads were updated.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
