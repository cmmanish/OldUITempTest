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

import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.lib.AuthToken;
import com.google.api.adwords.lib.utils.v201109_1.ReportUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * This example gets and downloads a report from a report definition.
 * To get a report definition, run AddKeywordsPerformanceReportDefinition.java.
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class DownloadReport {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();
      user.setAuthToken(new AuthToken(user.getEmail(), user.getPassword()).getAuthToken());

      // File to download to.
      String fileName = File.createTempFile("reportdownload", ".report").getAbsolutePath();
      long reportDefinitionId = Long.parseLong("INSERT_REPORT_DEFINITION_ID_HERE");

      ReportUtils.downloadReport(user, reportDefinitionId, new FileOutputStream(
          new File(fileName)));

      System.out.println("Report downloaded to: " + fileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
