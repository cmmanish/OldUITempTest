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
import com.google.api.adwords.v201109_1.cm.AdExtension;
import com.google.api.adwords.v201109_1.cm.AdExtensionOverride;
import com.google.api.adwords.v201109_1.cm.AdExtensionOverrideOperation;
import com.google.api.adwords.v201109_1.cm.AdExtensionOverrideReturnValue;
import com.google.api.adwords.v201109_1.cm.AdExtensionOverrideServiceInterface;
import com.google.api.adwords.v201109_1.cm.Operator;

/**
 * This example ads an ad extension override to an ad using an existing
 * campaign ad extension. To get ads, run GetAllAds.java. To get campaign
 * ad extensions, run GetAllCampaignAdExtensions.java.
 *
 * Tags: AdGroupCriterionService.mutate
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AddAdExtensionOverride {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdExtensionOverrideService.
      AdExtensionOverrideServiceInterface adExtensionOverrideService =
          user.getService(AdWordsService.V201109_1.AD_EXTENSION_OVERRIDE_SERVICE);

      long adId = Long.parseLong("INSERT_AD_ID_HERE");
      long campaignAdExtensionId = Long.parseLong("INSERT_CAMPAIGN_AD_EXTENSION_ID_HERE");

      // Create ad extension override.
      AdExtensionOverride adExtensionOverride = new AdExtensionOverride();
      adExtensionOverride.setAdId(adId);

      // Create ad extension using existing id.
      AdExtension adExtension = new AdExtension();
      adExtension.setId(campaignAdExtensionId);
      adExtensionOverride.setAdExtension(adExtension);

      // Create operations.
      AdExtensionOverrideOperation operation = new AdExtensionOverrideOperation();
      operation.setOperand(adExtensionOverride);
      operation.setOperator(Operator.ADD);

      AdExtensionOverrideOperation[] operations = new AdExtensionOverrideOperation[] {operation};

      // Add ad extension override.
      AdExtensionOverrideReturnValue result = adExtensionOverrideService.mutate(operations);

      // Display ad extension overrides.
      if (result != null && result.getValue() != null) {
        for (AdExtensionOverride adExtensionOverrideResult : result.getValue()) {
          System.out.println("Ad extension override with ad id \""
              + adExtensionOverrideResult.getAdId() + "\" and ad extension id \""
              + adExtensionOverrideResult.getAdExtension().getId() + "\" was added.");
        }
      } else {
        System.out.println("No ad extension overrides were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
