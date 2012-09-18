// Copyright 2011 Google Inc. All Rights Reserved.
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
import com.google.api.adwords.v201109.cm.AdWordsConversionTracker;
import com.google.api.adwords.v201109.cm.ConversionTracker;
import com.google.api.adwords.v201109.cm.ConversionTrackerOperation;
import com.google.api.adwords.v201109.cm.ConversionTrackerReturnValue;
import com.google.api.adwords.v201109.cm.ConversionTrackerServiceInterface;
import com.google.api.adwords.v201109.cm.ConversionTrackerStatus;
import com.google.api.adwords.v201109.cm.Operator;

/**
 * This example updates a conversion by setting its status to 'DISABLED'. To get
 * conversions, run GetAllConversions.java.
 *
 * Tags: ConversionService.mutate
 *
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class UpdateConversion {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ConversionTrackerService.
      ConversionTrackerServiceInterface service =
          user.getService(AdWordsService.V201109.CONVERSION_TRACKER_SERVICE);

      // Replace with a valid value from your account.
      long conversionId = Long.parseLong("INSERT_CONVERSION_ID_HERE");

      // Create conversion with updated status and name.
      AdWordsConversionTracker adWordsConversionTracker = new AdWordsConversionTracker();
      adWordsConversionTracker.setId(conversionId);
      adWordsConversionTracker.setStatus(ConversionTrackerStatus.DISABLED);

      // Create operations.
      ConversionTrackerOperation operation = new ConversionTrackerOperation();
      operation.setOperand(adWordsConversionTracker);
      operation.setOperator(Operator.SET);

      ConversionTrackerOperation[] operations = new ConversionTrackerOperation[] {operation};

      // Update conversion.
      ConversionTrackerReturnValue result = service.mutate(operations);

      // Display conversion.
      if (result != null && result.getValue() != null) {
        for (ConversionTracker conversionTracker : result.getValue()) {
          if (conversionTracker instanceof AdWordsConversionTracker) {
            AdWordsConversionTracker newAdWordsConversionTracker =
                (AdWordsConversionTracker) conversionTracker;
            System.out.printf("Conversion with id \"%d\", name \"%s\", status \"%s\", "
                + "category \"%s\" and snippet \"%s\" was updated.\n",
                newAdWordsConversionTracker.getId(), newAdWordsConversionTracker.getName(),
                newAdWordsConversionTracker.getStatus(), newAdWordsConversionTracker.getCategory(),
                newAdWordsConversionTracker.getSnippet());
          } else {
            System.out.printf("Conversion with id \"%d\", name \"%s\", status \"%s\", "
                + "category \"%s\" was updated.\n", conversionTracker.getId(),
                conversionTracker.getName(), conversionTracker.getStatus(),
                conversionTracker.getCategory());
          }
        }
      } else {
        System.out.println("No conversions were disabled.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
