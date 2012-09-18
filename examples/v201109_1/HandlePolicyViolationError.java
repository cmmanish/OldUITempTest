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
import com.google.api.adwords.v201109_1.cm.AdGroupAd;
import com.google.api.adwords.v201109_1.cm.AdGroupAdOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupAdReturnValue;
import com.google.api.adwords.v201109_1.cm.AdGroupAdServiceInterface;
import com.google.api.adwords.v201109_1.cm.ApiError;
import com.google.api.adwords.v201109_1.cm.ApiException;
import com.google.api.adwords.v201109_1.cm.ExemptionRequest;
import com.google.api.adwords.v201109_1.cm.Operator;
import com.google.api.adwords.v201109_1.cm.PolicyViolationError;
import com.google.api.adwords.v201109_1.cm.TextAd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This example demonstrates how to handle policy violation errors.
 *
 * Tags: AdGroupAdService.mutate
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class HandlePolicyViolationError {
  private static Pattern operationIndexPattern = Pattern.compile("^.*operations\\[(\\d+)\\].*$");

  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupAdService.
      AdGroupAdServiceInterface adGroupAdService =
          user.getService(AdWordsService.V201109_1.ADGROUP_AD_SERVICE);

      // Get validateOnly version of the AdGroupAdService.
      AdGroupAdServiceInterface adGroupAdValidationService =
          user.getValidationService(AdWordsService.V201109_1.ADGROUP_AD_SERVICE);

      long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");

      // Create text ad that violates an exemptable policy. This ad will only
      // trigger an error in the production environment.
      TextAd exemptableTextAd = new TextAd();
      exemptableTextAd.setHeadline("Mars " + System.currentTimeMillis() + "!!!");
      exemptableTextAd.setDescription1("Visit the Red Planet in style.");
      exemptableTextAd.setDescription2("Low-gravity fun for everyone!");
      exemptableTextAd.setDisplayUrl("www.example.com");
      exemptableTextAd.setUrl("http://www.example.com/");

      // Create ad group ad.
      AdGroupAd exemptableAdGroupAd = new AdGroupAd();
      exemptableAdGroupAd.setAdGroupId(adGroupId);
      exemptableAdGroupAd.setAd(exemptableTextAd);

      // Create operations.
      AdGroupAdOperation exemptableOperation = new AdGroupAdOperation();
      exemptableOperation.setOperand(exemptableAdGroupAd);
      exemptableOperation.setOperator(Operator.ADD);

      // Create text ad that violates an non-exemptable policy.
      TextAd nonExemptableTextAd = new TextAd();
      nonExemptableTextAd.setHeadline("Mars Cruise with too long of a headline.");
      nonExemptableTextAd.setDescription1("Visit the Red Planet in style.");
      nonExemptableTextAd.setDescription2("Low-gravity fun for everyone.");
      nonExemptableTextAd.setDisplayUrl("www.example.com");
      nonExemptableTextAd.setUrl("http://www.example.com/");

      // Create ad group ad.
      AdGroupAd nonExemptableAdGroupAd = new AdGroupAd();
      nonExemptableAdGroupAd.setAdGroupId(adGroupId);
      nonExemptableAdGroupAd.setAd(nonExemptableTextAd);

      // Create operations.
      AdGroupAdOperation nonExemptableOperation = new AdGroupAdOperation();
      nonExemptableOperation.setOperand(nonExemptableAdGroupAd);
      nonExemptableOperation.setOperator(Operator.ADD);

      AdGroupAdOperation[] operations =
          new AdGroupAdOperation[] {exemptableOperation, nonExemptableOperation};

      try {
        // Validate the ads.
        AdGroupAdReturnValue result = adGroupAdValidationService.mutate(operations);
      } catch (ApiException e) {
        Set<Integer> indicesToRemove = new HashSet<Integer>();
        for (ApiError error : e.getErrors()) {
          if (error instanceof PolicyViolationError) {
            PolicyViolationError policyVioloationError = (PolicyViolationError) error;
            Matcher matcher = operationIndexPattern.matcher(error.getFieldPath());
            if (matcher.matches()) {
              int operationIndex = Integer.parseInt(matcher.group(1));
              AdGroupAdOperation operation = operations[operationIndex];
              System.out.printf("Ad with headline \"%s\" violated %s policy \"%s\".\n",
                  ((TextAd) operation.getOperand().getAd()).getHeadline(), policyVioloationError
                      .getIsExemptable() ? "exemptable" : "non-exemptable", policyVioloationError
                      .getExternalPolicyName());
              if (policyVioloationError.getIsExemptable()) {
                // Add exemption request to the operation.
                System.out.printf(
                    "Adding exemption request for policy name \"%s\" on text \"%s\".\n",
                    policyVioloationError.getKey().getPolicyName(), policyVioloationError.getKey()
                        .getViolatingText());
                List<ExemptionRequest> exemptionRequests =
                    new ArrayList<ExemptionRequest>(Arrays
                        .asList(operation.getExemptionRequests() == null
                            ? new ExemptionRequest[] {} : operation.getExemptionRequests()));
                exemptionRequests.add(new ExemptionRequest(policyVioloationError.getKey()));
                operation
                    .setExemptionRequests(exemptionRequests.toArray(new ExemptionRequest[] {}));
              } else {
                // Remove non-exemptable operation.
                System.out.println("Removing non-exemptable operation at index " + operationIndex
                    + ".");
                indicesToRemove.add(operationIndex);
              }
            }
          } else {
            // Non-policy error returned.
            Matcher matcher = operationIndexPattern.matcher(error.getFieldPath());
            if (matcher.matches()) {
              int operationIndex = Integer.parseInt(matcher.group(1));
              AdGroupAdOperation operation = operations[operationIndex];
              System.out.printf("Ad with headline \"%s\" created non-policy error \"%s\".\n",
                  ((TextAd) operation.getOperand().getAd()).getHeadline(), error.getErrorString());
              System.out.println("Removing non-exemptable operation at index " + operationIndex
                  + ".");
              indicesToRemove.add(operationIndex);
            }
          }
        }

        // Remove operations that cannot be exempted.
        List<AdGroupAdOperation> remainingOperations = new ArrayList<AdGroupAdOperation>();
        for (int i = 0; i < operations.length; i++) {
          if (!indicesToRemove.contains(i)) {
            remainingOperations.add(operations[i]);
          }
        }
        operations = remainingOperations.toArray(new AdGroupAdOperation[]{});
      }

      if (operations.length > 0) {
        // Add ads with exemptions.
        AdGroupAdReturnValue result = adGroupAdService.mutate(operations);

        // Display ads.
        if (result != null && result.getValue() != null) {
          for (AdGroupAd adGroupAdResult : result.getValue()) {
            System.out.printf("Ad with id \"%s\" and headline \"%s\" was added.\n", adGroupAdResult
                .getAd().getId(), ((TextAd) adGroupAdResult.getAd()).getHeadline());
          }
        }
      } else {
        System.out.println("No ads were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
