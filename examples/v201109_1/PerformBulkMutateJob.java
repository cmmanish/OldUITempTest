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
import com.google.api.adwords.lib.utils.ChoiceUtils;
import com.google.api.adwords.lib.utils.ImageUtils;
import com.google.api.adwords.v201109_1.cm.AdGroupAd;
import com.google.api.adwords.v201109_1.cm.AdGroupAdOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterion;
import com.google.api.adwords.v201109_1.cm.AdGroupCriterionOperation;
import com.google.api.adwords.v201109_1.cm.ApiException;
import com.google.api.adwords.v201109_1.cm.BasicJobStatus;
import com.google.api.adwords.v201109_1.cm.BatchFailureResult;
import com.google.api.adwords.v201109_1.cm.BiddableAdGroupCriterion;
import com.google.api.adwords.v201109_1.cm.BulkMutateJob;
import com.google.api.adwords.v201109_1.cm.BulkMutateJobSelector;
import com.google.api.adwords.v201109_1.cm.BulkMutateJobServiceInterface;
import com.google.api.adwords.v201109_1.cm.BulkMutateRequest;
import com.google.api.adwords.v201109_1.cm.EntityId;
import com.google.api.adwords.v201109_1.cm.EntityIdType;
import com.google.api.adwords.v201109_1.cm.FailureResult;
import com.google.api.adwords.v201109_1.cm.Image;
import com.google.api.adwords.v201109_1.cm.ImageAd;
import com.google.api.adwords.v201109_1.cm.JobOperation;
import com.google.api.adwords.v201109_1.cm.Keyword;
import com.google.api.adwords.v201109_1.cm.KeywordMatchType;
import com.google.api.adwords.v201109_1.cm.LostResult;
import com.google.api.adwords.v201109_1.cm.Operation;
import com.google.api.adwords.v201109_1.cm.OperationResult;
import com.google.api.adwords.v201109_1.cm.OperationStream;
import com.google.api.adwords.v201109_1.cm.Operator;
import com.google.api.adwords.v201109_1.cm.Placement;
import com.google.api.adwords.v201109_1.cm.ReturnValueResult;
import com.google.api.adwords.v201109_1.cm.TextAd;
import com.google.api.adwords.v201109_1.cm.UnprocessedResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This example creates a multi-part bulk mutate job and polls for the results
 * of the request. This example is broken up into several snippets of logic
 * which will better demonstrate the best-practice usage of the
 * BulkMutateJobService. Though the bulk mutate job service is capable of more
 * complex operations, this example will show how you can easily upgrade from
 * normal mutate calls to using bulk mutate jobs.
 *
 * Tags: BulkMutateJobService.mutate, BulkMutateJobService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class PerformBulkMutateJob {
  public static void main(String[] args) {
    try {
      //Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the BulkMutateJobService.
      BulkMutateJobServiceInterface bulkMutateJobService =
          user.getService(AdWordsService.V201109_1.BULK_MUTATE_JOB_SERVICE);

      long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");
      long adGroupId1 = Long.parseLong("INSERT_AD_GROUP_ID_BELONGING_TO_CAMPAIGN_ABOVE_HERE");
      long adGroupId2 =
          Long.parseLong("INSERT_ANOTHER_AD_GROUP_ID_BELONGING_TO_CAMPAIGN_ABOVE_ID_HERE");

      // Set scope of jobs.
      EntityId scopingEntityId = new EntityId(EntityIdType.CAMPAIGN_ID, campaignId);

      // Create the operations.
      AdGroupAdOperation[] adGroupAdOperations = createAdGroupAdOperations(adGroupId1, adGroupId2);
      AdGroupCriterionOperation[] adGroupCriterionOperations =
          createAdGroupCriterionOperations(adGroupId1, adGroupId2);
      Operation[][] operationsByPart =
          new Operation[][] {adGroupAdOperations, adGroupCriterionOperations};

      // Create and begin job.
      long jobId =
          createAndBeginJob(bulkMutateJobService, scopingEntityId, operationsByPart);

      // Monitor and retrieve results from job.
      OperationResult[][] operationResultsByPart =
          retrieveResultsFromJob(bulkMutateJobService, jobId);

      // Process results for part 1 that we know to be of type AdGroupAd.
      List<AdGroupAd> adGroupAds = processResults(operationResultsByPart[0]);

      // Process results for part 2 that we know to be of type AdGroupCriterion.
      List<AdGroupCriterion> adGroupCriteria = processResults(operationResultsByPart[1]);

      // Display results.
      for (AdGroupAd adGroupAd : adGroupAds) {
        System.out.println("Ad with id  \"" + adGroupAd.getAd().getId() + "\""
            + " and type \"" + adGroupAd.getAd().getAdType() + "\" was added.");
      }

      for (AdGroupCriterion adGroupCriterion : adGroupCriteria) {
        System.out.println("Ad group criterion with ad group id \""
            + adGroupCriterion.getAdGroupId() + "\", criterion id \""
            + adGroupCriterion.getCriterion().getId() + "\", and type \""
            + adGroupCriterion.getCriterion().getCriterionType() + "\" was added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates an array of ad group ad operations to be performed by the
   * BulkMutateJobService.
   *
   * @param adGroupId1 the ad group to which the text ad will be added
   * @param adGroupId2 the ad group to which the image ad will be added
   * @return an array of ad group ad operations to be performed by the
   *     BulkMutateJobService
   * @throws IOException if the image for the image ad could not be retrieved
   */
  private static AdGroupAdOperation[] createAdGroupAdOperations(long adGroupId1, long adGroupId2)
      throws IOException {
    // Create text ad.
    TextAd textAd = new TextAd();
    textAd.setHeadline("Luxury Cruise to Mars");
    textAd.setDescription1("Visit the Red Planet in style.");
    textAd.setDescription2("Low-gravity fun for everyone!");
    textAd.setDisplayUrl("www.example.com");
    textAd.setUrl("http://www.example.com");

    // Create ad group ad.
    AdGroupAd textAdGroupAd = new AdGroupAd();
    textAdGroupAd.setAdGroupId(adGroupId1);
    textAdGroupAd.setAd(textAd);

    // Create image ad.
    ImageAd imageAd = new ImageAd();
    imageAd.setName("Cruise to mars image ad #" + System.currentTimeMillis());
    imageAd.setDisplayUrl("www.example.com");
    imageAd.setUrl("http://example.com");

    // Create image.
    Image image = new Image();
    image.setData(ImageUtils.getImageDataFromUrl("http://goo.gl/HJM3L"));
    imageAd.setImage(image);

    // Create ad group ad.
    AdGroupAd imageAdGroupAd = new AdGroupAd();
    imageAdGroupAd.setAdGroupId(adGroupId2);
    imageAdGroupAd.setAd(imageAd);

    // Create operations.
    AdGroupAdOperation textAdGroupAdOperation = new AdGroupAdOperation();
    textAdGroupAdOperation.setOperand(textAdGroupAd);
    textAdGroupAdOperation.setOperator(Operator.ADD);

    AdGroupAdOperation imageAdGroupAdOperation = new AdGroupAdOperation();
    imageAdGroupAdOperation.setOperand(imageAdGroupAd);
    imageAdGroupAdOperation.setOperator(Operator.ADD);

    return new AdGroupAdOperation[] {textAdGroupAdOperation, imageAdGroupAdOperation};
  }

  /**
   * Creates an array of ad group criterion operations to be performed by the
   * BulkMutateJobService.
   *
   * @param adGroupId1 the ad group to which the keyword will be added
   * @param adGroupId2 the ad group to which the placement will be added
   * @return an array of ad group criterion operations to be performed by the
   *     BulkMutateJobService.
   */
  private static AdGroupCriterionOperation[] createAdGroupCriterionOperations(long adGroupId1,
      long adGroupId2) {
    List<AdGroupCriterionOperation> operations = new ArrayList<AdGroupCriterionOperation>();

    // Create 100 keywords.
    for (int i = 0; i < 100; i++) {
      Keyword keyword = new Keyword();
      keyword.setText("mars cruise " + i + "" + System.currentTimeMillis());
      keyword.setMatchType(KeywordMatchType.BROAD);

      // Create biddable ad group criterion.
      BiddableAdGroupCriterion keywordBiddableAdGroupCriterion = new BiddableAdGroupCriterion();
      keywordBiddableAdGroupCriterion.setAdGroupId(adGroupId1);
      keywordBiddableAdGroupCriterion.setCriterion(keyword);

      // Create operations.
      AdGroupCriterionOperation keywordAdGroupCriterionOperation = new AdGroupCriterionOperation();
      keywordAdGroupCriterionOperation.setOperator(Operator.ADD);
      keywordAdGroupCriterionOperation.setOperand(keywordBiddableAdGroupCriterion);

      operations.add(keywordAdGroupCriterionOperation);
    }

    // Create placement.
    Placement placement = new Placement();
    placement.setUrl("http://mars.google.com");

    // Create biddable ad group criterion for placement.
    BiddableAdGroupCriterion placementBiddableAdGroupCriterion = new BiddableAdGroupCriterion();
    placementBiddableAdGroupCriterion.setAdGroupId(adGroupId2);
    placementBiddableAdGroupCriterion.setCriterion(placement);

    // Create operation
    AdGroupCriterionOperation placementAdGroupCriterionOperation =
        new AdGroupCriterionOperation();
    placementAdGroupCriterionOperation.setOperator(Operator.ADD);
    placementAdGroupCriterionOperation.setOperand(placementBiddableAdGroupCriterion);

    operations.add(placementAdGroupCriterionOperation);

    return operations.toArray(new AdGroupCriterionOperation[] {});
  }

  /**
   * Creates and begins a bulk mutate job. Each array of operations from the
   * {@code operationsByPart} array corresponds to a single part of the request.
   *
   * @param bulkMutateJobService the service client to make the request against
   * @param scopingEntityId the scoping entity ID of the request
   * @param operationsByPart an array of operations separated by part
   * @return the id of the create bulk mutate job
   * @throws IOException if there is an exception while making a request
   */
  private static long createAndBeginJob(
      BulkMutateJobServiceInterface bulkMutateJobService, EntityId scopingEntityId,
      Operation[][] operationsByPart) throws IOException {
    // Initialize the bulk mutate job id.
    Long jobId = null;

    for (int partCounter = 0;  partCounter < operationsByPart.length; partCounter++) {
      // Create operation stream.
      OperationStream opStream = new OperationStream();
      opStream.setScopingEntityId(scopingEntityId);
      opStream.setOperations(operationsByPart[partCounter]);

      // Create bulk mutate request part.
      BulkMutateRequest part = new BulkMutateRequest();
      part.setPartIndex(partCounter);
      part.setOperationStreams(new OperationStream[] {opStream});

      // Create bulk mutate job.
      BulkMutateJob job = new BulkMutateJob();
      job.setId(jobId);
      job.setNumRequestParts(operationsByPart.length);

      // Set the part request in the job.
      job.setRequest(part);

      // Create the operation to contain the job.
      JobOperation operation = new JobOperation();
      operation.setOperand(job);

      // If this is our first part, then the job must be added, not set.
      if (partCounter == 0) {
        operation.setOperator(Operator.ADD);
      } else {
        operation.setOperator(Operator.SET);
      }

      // Add/set the job. The job will not start until all parts are added.
      job = bulkMutateJobService.mutate(operation);

      // Display notification.
      if (partCounter == 0) {
        System.out.println("Bulk mutate request with job id \"" + job.getId()
            + "\" and part number \"" + partCounter + "\" was added.");
      } else {
        System.out.println("Bulk mutate request with job id \"" + job.getId()
            + "\" and part number \"" + partCounter + "\" was set.");
      }

      // Store job id.
      jobId = job.getId();
    }

    return jobId;
  }

  /**
   * Retrieves the results from a job that has all parts requested. When the
   * job has completed, this will return an array of operation results separated
   * by part.
   *
   * @param bulkMutateJobService the service client to make the request against
   * @param jobId the id of a bulk mutate job after all parts have been
   *     requested
   * @return an array of operation results separated by part.
   * @throws InterruptedException if the sleep-thread is interrupted
   * @throws IOException if there is an exception while making a request or if
   *     the job fails
   */
  private static OperationResult[][] retrieveResultsFromJob(
      BulkMutateJobServiceInterface bulkMutateJobService, long jobId)
      throws InterruptedException, IOException {
    List<OperationResult[]> operationResultsByPart = new ArrayList<OperationResult[]>();
    BulkMutateJob job = null;

    // Create selector.
    BulkMutateJobSelector selector = new BulkMutateJobSelector();
    selector.setJobIds(new long[] {jobId});

    // Loop while waiting for the job to complete.
    do {
      BulkMutateJob[] jobs = bulkMutateJobService.get(selector);
      job = jobs[0];

      System.out.println("Bulk mutate job with id \"" + job.getId() + "\" has status \""
          + job.getStatus() + "\".");

      if (job.getStatus().equals(BasicJobStatus.PENDING)
        || job.getStatus().equals(BasicJobStatus.PROCESSING)) {
        Thread.sleep(10000);
      }
    } while(job.getStatus().equals(BasicJobStatus.PENDING)
        || job.getStatus().equals(BasicJobStatus.PROCESSING));

    if (job.getStatus() == BasicJobStatus.FAILED) {
      throw new ApiException("Job failed.", null, null);
    }

    for (int i = 0; i < job.getNumRequestParts(); i++) {
      // Set selector to retrieve results for part.
      selector.setResultPartIndex(i);
      BulkMutateJob jobWithResult = bulkMutateJobService.get(selector)[0];

      System.out.println("Bulk mutate result with job id \"" + job.getId()
          + "\" and part number \"" + i + "\" was retrieved.");

      // Get the operation results.
      operationResultsByPart.add(
          jobWithResult.getResult().getOperationStreamResults()[0].getOperationResults());
    }

    return operationResultsByPart.toArray(new OperationResult[][] {});
  }

  /**
   * Process the operation results to create a list of return values.
   *
   * @param operationResults the results retrieved from the operation stream
   *     result
   * @return a list of return values
   */
  private static <T> List<T> processResults(OperationResult[] operationResults)
      throws IllegalArgumentException, IllegalAccessException {
    List<T> results = new ArrayList<T>();
    for (OperationResult operationResult : operationResults) {
      if (operationResult instanceof FailureResult) {
        FailureResult failureResult = (FailureResult) operationResult;
        // You can alternatively throw failureResult.getCause().
        System.err.println(
            "Result was not successful for reason(s): " + failureResult.getCause().getMessage1());
      } else if (operationResult instanceof BatchFailureResult) {
        BatchFailureResult batchFailureResult = (BatchFailureResult) operationResult;
        System.err.println("Result was not successful in batch index of operation: "
            + batchFailureResult.getOperationIndexInBatch());
      } else if (operationResult instanceof LostResult) {
        System.err.println("Result was lost.");
      } else if (operationResult instanceof UnprocessedResult) {
        System.err.println("Result was unprocessed.");
      } else if (operationResult instanceof ReturnValueResult) {
        results.add(
            (T) ChoiceUtils.getValue(((ReturnValueResult) operationResult).getReturnValue()));
        System.out.println("Result was successful.");
      }
    }

    return results;
  }
}
