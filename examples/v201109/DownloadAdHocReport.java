// Copyright 2011, Google Inc. All Rights Reserved.
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

import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.lib.utils.v201109.ReportDownloadResponse;
import com.google.api.adwords.lib.utils.v201109.ReportUtils;
import com.google.api.adwords.v201109.cm.DownloadFormat;
import com.google.api.adwords.v201109.cm.ReportDefinition;
import com.google.api.adwords.v201109.cm.ReportDefinitionDateRangeType;
import com.google.api.adwords.v201109.cm.ReportDefinitionReportType;
import com.google.api.adwords.v201109.cm.Selector;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;

/**
 * This example adds a keywords performance report. To get ad groups, run
 * GetAllAdGroups.java. To get report columns, run GetReportColumns.java.
 *
 * Tags: ReportDefinitionService.mutate
 *
 * @category adx-exclude
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class DownloadAdHocReport {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Location to download report to.
      String reportFile = "/tmp/report.csv";

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"AdGroupId", "Id", "KeywordText", "KeywordMatchType",
          "Impressions", "Clicks", "Cost"});

      // Create report definition.
      ReportDefinition reportDefinition = new ReportDefinition();
      reportDefinition.setReportName("Keywords performance report #" + System.currentTimeMillis());
      reportDefinition.setDateRangeType(ReportDefinitionDateRangeType.YESTERDAY);
      reportDefinition.setReportType(ReportDefinitionReportType.KEYWORDS_PERFORMANCE_REPORT);
      reportDefinition.setDownloadFormat(DownloadFormat.CSV);
      reportDefinition.setIncludeZeroImpressions(false);
      reportDefinition.setSelector(selector);

      FileOutputStream fos = new FileOutputStream(new File(reportFile));
      ReportDownloadResponse response = ReportUtils.downloadReport(user, reportDefinition, fos);
      if (response.getHttpStatus() == HttpURLConnection.HTTP_OK) {
        System.out.println("Report successfully downloaded: " + reportFile);
      } else {
        System.out.println("Report was not downloaded. " + response.getHttpStatus() + ": "
            + response.getHttpResponseMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
