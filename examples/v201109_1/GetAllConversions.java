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
import com.google.api.adwords.v201109_1.cm.ConversionTracker;
import com.google.api.adwords.v201109_1.cm.ConversionTrackerPage;
import com.google.api.adwords.v201109_1.cm.ConversionTrackerServiceInterface;
import com.google.api.adwords.v201109_1.cm.OrderBy;
import com.google.api.adwords.v201109_1.cm.Selector;
import com.google.api.adwords.v201109_1.cm.SortOrder;

/**
 * This example gets all conversions in the account. To add a conversion, run
 * AddConversion.java.
 *
 * Tags: ConversionService.get
 *
 * @author api.kwinter@gmail.com (Kevin Winter)
 *
 */
public class GetAllConversions {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ConversionTrackerService.
      ConversionTrackerServiceInterface service =
          user.getService(AdWordsService.V201109_1.CONVERSION_TRACKER_SERVICE);

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"Name", "Status", "Category"});
      selector.setOrdering(new OrderBy[] {new OrderBy("Name", SortOrder.ASCENDING)});

      // Get all conversions.
      ConversionTrackerPage page = service.get(selector);

      // Display conversions.
      if (page != null && page.getEntries() != null) {
        for (ConversionTracker conversionTracker : page.getEntries()) {
          if (conversionTracker instanceof AdWordsConversionTracker) {
            AdWordsConversionTracker newAdWordsConversionTracker =
                (AdWordsConversionTracker) conversionTracker;
            System.out.printf("Conversion with id \"%d\", name \"%s\", status \"%s\", "
                + "category \"%s\" and snippet \"%s\" was found.\n",
                newAdWordsConversionTracker.getId(), newAdWordsConversionTracker.getName(),
                newAdWordsConversionTracker.getStatus(), newAdWordsConversionTracker.getCategory(),
                newAdWordsConversionTracker.getSnippet());
          } else {
            System.out.printf("Conversion with id \"%d\", name \"%s\", status \"%s\", "
                + "category \"%s\" was found.\n", conversionTracker.getId(),
                conversionTracker.getName(), conversionTracker.getStatus(),
                conversionTracker.getCategory());
          }
        }
      } else {
        System.out.println("No conversions were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
