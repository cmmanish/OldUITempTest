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
import com.google.api.adwords.v201109_1.cm.AdGroup;
import com.google.api.adwords.v201109_1.cm.AdGroupOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupReturnValue;
import com.google.api.adwords.v201109_1.cm.AdGroupServiceInterface;
import com.google.api.adwords.v201109_1.cm.AdGroupStatus;
import com.google.api.adwords.v201109_1.cm.Operator;

/**
 * This example deletes an ad group by setting the status to 'DELETED'.
 * To get ad groups, run GetAllAdGroups.java.
 *
 * Tags: AdGroupService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class DeleteAdGroup {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupService.
      AdGroupServiceInterface adGroupService =
          user.getService(AdWordsService.V201109_1.ADGROUP_SERVICE);

      long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");

      // Create ad group with DELETED status.
      AdGroup adGroup = new AdGroup();
      adGroup.setId(adGroupId);
      adGroup.setStatus(AdGroupStatus.DELETED);

      // Create operations.
      AdGroupOperation operation = new AdGroupOperation();
      operation.setOperand(adGroup);
      operation.setOperator(Operator.SET);

      AdGroupOperation[] operations = new AdGroupOperation[]{operation};

      // Delete ad group.
      AdGroupReturnValue result = adGroupService.mutate(operations);

      // Display ad groups.
      if (result != null && result.getValue() != null) {
        for (AdGroup adGroupResult : result.getValue()) {
          System.out.println("Ad group with name \"" + adGroupResult.getName() + "\" and id \""
              + adGroupResult.getId() + "\" was deleted.");
        }
      } else {
        System.out.println("No ad groups were deleted.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
