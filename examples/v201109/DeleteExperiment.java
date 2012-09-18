// Copyright 2011 Google Inc. All Rights Reserved.
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

package v201109;

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.v201109.cm.Experiment;
import com.google.api.adwords.v201109.cm.ExperimentOperation;
import com.google.api.adwords.v201109.cm.ExperimentReturnValue;
import com.google.api.adwords.v201109.cm.ExperimentServiceInterface;
import com.google.api.adwords.v201109.cm.ExperimentStatus;
import com.google.api.adwords.v201109.cm.Operator;

/**
 * This example deletes an experiment. To get experiments, run
 * GetAllExperiments.java. To add an experiment, run AddExperiment.java.
 *
 * Tags: ExperimentService.mutate
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class DeleteExperiment {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ExperimentService.
      ExperimentServiceInterface experimentService =
          user.getService(AdWordsService.V201109.EXPERIMENT_SERVICE);

      long experimentId = Long.parseLong("INSERT_EXPERIMENT_ID_HERE");

      // Create experiment with DELETED status.
      Experiment experiment = new Experiment();
      experiment.setId(experimentId);
      experiment.setStatus(ExperimentStatus.DELETED);

      // Create operation.
      ExperimentOperation operation = new ExperimentOperation();
      operation.setOperand(experiment);
      operation.setOperator(Operator.SET);

      ExperimentOperation[] operations = new ExperimentOperation[]{operation};

      // Delete experiment.
      ExperimentReturnValue result = experimentService.mutate(operations);

      // Display experiments.
      if (result.getValue() != null) {
        for(Experiment experimentResult : result.getValue()) {
          System.out.println("Experiment with name \"" + experimentResult.getName()
              + "\" and id \"" + experimentResult.getId() + "\" was deleted.");
        }
      } else {
        System.out.println ("No experiments were deleted.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
