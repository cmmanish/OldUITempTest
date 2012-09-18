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
import com.google.api.adwords.v201109_1.cm.ReportDefinition;
import com.google.api.adwords.v201109_1.cm.ReportDefinitionPage;
import com.google.api.adwords.v201109_1.cm.ReportDefinitionSelector;
import com.google.api.adwords.v201109_1.cm.ReportDefinitionServiceInterface;

/**
 * This example gets all report definitions. To add a report definition, run
 * AddKeywordsPerformanceReportDefinition.java.
 *
 * Tags: ReportDefinitionService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllReportDefinitions {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ReportDefinitionService.
      ReportDefinitionServiceInterface reportDefinitionService =
          user.getService(AdWordsService.V201109_1.REPORT_DEFINITION_SERVICE);

      // Create selector.
      ReportDefinitionSelector selector = new ReportDefinitionSelector();

      // Get all report definitions.
      ReportDefinitionPage page = reportDefinitionService.get(selector);

      // Display report definitions.
      if (page.getEntries() != null) {
        for (ReportDefinition reportDefinition : page.getEntries()) {
          System.out.println("ReportDefinition with name \"" + reportDefinition.getReportName()
              + "\" and id \"" + reportDefinition.getId() + "\" was found.");
        }
      } else {
        System.out.println("No report definitions were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
