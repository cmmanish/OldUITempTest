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
import com.google.api.adwords.lib.utils.ChoiceUtils;
import com.google.api.adwords.v201109_1.cm.BasicJobStatus;
import com.google.api.adwords.v201109_1.cm.BulkMutateJob;
import com.google.api.adwords.v201109_1.cm.BulkMutateJobSelector;
import com.google.api.adwords.v201109_1.cm.BulkMutateJobServiceInterface;
import com.google.api.adwords.v201109_1.cm.BulkMutateJobStats;

/**
 * This example get a bulk mutate job and displays its status. To add a bulk
 * mutate job, run PerformBulkMutateJob.java.
 *
 * Tags: BulkMutateJobService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetBulkMutateJob {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the BulkMutateJobService
      BulkMutateJobServiceInterface bulkMutateJobService =
          user.getService(AdWordsService.V201109_1.BULK_MUTATE_JOB_SERVICE);

      long bulkMutateJobId = Long.parseLong("INSERT_BULK_MUTATE_JOB_HERE");

      // Create selector.
      BulkMutateJobSelector selector = new BulkMutateJobSelector();
      selector.setJobIds(new long[] {bulkMutateJobId});
      selector.setIncludeStats(true);

      // Get bulk mutate job.
      BulkMutateJob[] bulkMutateJobs = bulkMutateJobService.get(selector);

      // Display bulk mutate job.
      if (bulkMutateJobs != null) {
        for (BulkMutateJob bulkMutateJob : bulkMutateJobs) {
          System.out.printf("Bulk mutate job with id '%d' and status '%s' was found.\n",
              bulkMutateJob.getId(), bulkMutateJob.getStatus());
          if (bulkMutateJob.getStatus() == BasicJobStatus.PENDING) {
            System.out.printf("  Total parts: %d, parts received: %d.\n",
                bulkMutateJob.getNumRequestParts(),
                bulkMutateJob.getNumRequestPartsReceived());
          } else if (bulkMutateJob.getStatus() == BasicJobStatus.PROCESSING) {
            System.out.printf("  Percent complete: %d.\n",
                bulkMutateJob.getStats().getProgressPercent());
          } else if (bulkMutateJob.getStatus() == BasicJobStatus.COMPLETED) {
            BulkMutateJobStats jobStats = (BulkMutateJobStats) bulkMutateJob.getStats();
            System.out.printf("  Total operations: %d, failed: %d, unprocessed: %d.\n",
                jobStats.getNumOperations(), jobStats.getNumFailedOperations(),
                jobStats.getNumUnprocessedOperations());
          } else if (bulkMutateJob.getStatus() == BasicJobStatus.FAILED) {
            System.out.printf("  Failure reason: %s.\n",
                ChoiceUtils.getValue(bulkMutateJob.getFailureReason()));
          }
        }
      } else {
        System.out.println("No bulk mutate jobs were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
