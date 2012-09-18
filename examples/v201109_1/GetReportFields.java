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
import com.google.api.adwords.v201109_1.cm.ReportDefinitionField;
import com.google.api.adwords.v201109_1.cm.ReportDefinitionReportType;
import com.google.api.adwords.v201109_1.cm.ReportDefinitionServiceInterface;

/**
 * This example gets report fields.
 *
 * Tags: ReportDefinitionService.getReportFields
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetReportFields {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ReportDefinitionService.
      ReportDefinitionServiceInterface reportDefinitionService =
          user.getService(AdWordsService.V201109_1.REPORT_DEFINITION_SERVICE);

      // Get report fields.
      ReportDefinitionField[] reportDefinitionFields = reportDefinitionService.getReportFields(
          ReportDefinitionReportType.KEYWORDS_PERFORMANCE_REPORT);

      // Display report fields.
      System.out.println("Available fields for report:");

      for (ReportDefinitionField reportDefinitionField : reportDefinitionFields) {
        System.out.print("\t" + reportDefinitionField.getFieldName() + "("
            + reportDefinitionField.getFieldType() + ") := [");
        if (reportDefinitionField.getEnumValues() != null) {
          for (String enumValue : reportDefinitionField.getEnumValues()) {
            System.out.print(enumValue + ", ");
          }
        }
        System.out.println("]");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
