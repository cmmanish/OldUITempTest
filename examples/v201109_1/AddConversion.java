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
import com.google.api.adwords.v201109_1.cm.AdWordsConversionTracker;
import com.google.api.adwords.v201109_1.cm.AdWordsConversionTrackerHttpProtocol;
import com.google.api.adwords.v201109_1.cm.AdWordsConversionTrackerMarkupLanguage;
import com.google.api.adwords.v201109_1.cm.AdWordsConversionTrackerTextFormat;
import com.google.api.adwords.v201109_1.cm.ConversionTracker;
import com.google.api.adwords.v201109_1.cm.ConversionTrackerCategory;
import com.google.api.adwords.v201109_1.cm.ConversionTrackerOperation;
import com.google.api.adwords.v201109_1.cm.ConversionTrackerReturnValue;
import com.google.api.adwords.v201109_1.cm.ConversionTrackerServiceInterface;
import com.google.api.adwords.v201109_1.cm.Operator;

/**
 * This example adds an AdWords conversion.
 *
 * Tags: ConversionTrackerService.mutate
 *
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class AddConversion {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ConversionTrackerService.
      ConversionTrackerServiceInterface service =
          user.getService(AdWordsService.V201109_1.CONVERSION_TRACKER_SERVICE);

      // Create AdWords conversion.
      AdWordsConversionTracker adWordsConversionTracker = new AdWordsConversionTracker();
      adWordsConversionTracker.setName("Earth to Mars Cruises Conversion # "
          + System.currentTimeMillis());
      adWordsConversionTracker.setCategory(ConversionTrackerCategory.DEFAULT);
      adWordsConversionTracker.setMarkupLanguage(AdWordsConversionTrackerMarkupLanguage.HTML);
      adWordsConversionTracker.setHttpProtocol(AdWordsConversionTrackerHttpProtocol.HTTP);
      adWordsConversionTracker.setTextFormat(AdWordsConversionTrackerTextFormat.HIDDEN);

      // Create operations.
      ConversionTrackerOperation operation = new ConversionTrackerOperation();
      operation.setOperator(Operator.ADD);
      operation.setOperand(adWordsConversionTracker);

      ConversionTrackerOperation[] operations = new ConversionTrackerOperation[] {operation};

      // Add conversion.
      ConversionTrackerReturnValue result = service.mutate(operations);

      // Display conversion.
      if (result != null && result.getValue() != null) {
        for (ConversionTracker conversionTracker : result.getValue()) {
          if (conversionTracker instanceof AdWordsConversionTracker) {
            AdWordsConversionTracker newAdWordsConversionTracker =
                (AdWordsConversionTracker) conversionTracker;
            System.out.printf("Conversion with id \"%d\", name \"%s\", status \"%s\", "
                + "category \"%s\" and snippet \"%s\" was added.\n",
                newAdWordsConversionTracker.getId(), newAdWordsConversionTracker.getName(),
                newAdWordsConversionTracker.getStatus(), newAdWordsConversionTracker.getCategory(),
                newAdWordsConversionTracker.getSnippet());
          } else {
            System.out.printf("Conversion with id \"%d\", name \"%s\", status \"%s\", "
                + "category \"%s\" was added.\n", conversionTracker.getId(),
                conversionTracker.getName(), conversionTracker.getStatus(),
                conversionTracker.getCategory());
          }
        }
      } else {
        System.out.println("No conversions were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
