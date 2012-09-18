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

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.v201109.cm.BulkMutateJob;
import com.google.api.adwords.v201109.cm.BulkMutateJobServiceInterface;
import com.google.api.adwords.v201109.cm.JobOperation;
import com.google.api.adwords.v201109.cm.Operator;

/**
 * This example deletes a bulk mutate job using the 'REMOVE' operator. Jobs may
 * only deleted if they are in the 'PENDING' state and have not yet receieved
 * all of their request parts. To get bulk mutate jobs, run
 * GetAllBulkMutateJobs.java.
 *
 * Tags: BulkMutateJobService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class DeleteBulkMutateJob {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the BulkMutateJobService
      BulkMutateJobServiceInterface bulkMutateJobService =
          user.getService(AdWordsService.V201109.BULK_MUTATE_JOB_SERVICE);

      long bulkMutateJobId = Long.parseLong("INSERT_BULK_MUTATE_JOB_ID_HERE");

      // Create BulkMutateJob.
      BulkMutateJob bulkMutateJob = new BulkMutateJob();
      bulkMutateJob.setId(bulkMutateJobId);

      // Create operation.
      JobOperation operation = new JobOperation();
      operation.setOperand(bulkMutateJob);
      operation.setOperator(Operator.REMOVE);

      // Delete bulk mutate job.
      bulkMutateJob = bulkMutateJobService.mutate(operation);

      // Display bulk mutate jobs.
      if (bulkMutateJob != null) {
        System.out.printf("Bulk mutate job with id '%d' was deleted.\n", bulkMutateJob.getId());
      } else {
        System.out.println("No bulk mutate jobs were deleted.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
