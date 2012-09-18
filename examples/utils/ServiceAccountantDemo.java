// Copyright 2010 Google Inc. All Rights Reserved.
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

package utils;

import com.google.api.adwords.lib.AdWordsService;
import com.google.api.adwords.lib.AdWordsServiceLogger;
import com.google.api.adwords.lib.AdWordsUser;
import com.google.api.adwords.lib.ServiceAccountant;
import com.google.api.adwords.lib.ServiceAccountantManager;
import com.google.api.adwords.v201109.cm.AdGroupAdServiceInterface;
import com.google.api.adwords.v201109.cm.AdGroupCriterionServiceInterface;
import com.google.api.adwords.v201109.cm.AdGroupServiceInterface;
import com.google.api.adwords.v201109.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109.cm.Selector;

import org.apache.axis.client.Stub;

import java.rmi.RemoteException;

/**
 * This demo shows how to use the features of the
 * {@link ServiceAccountantManager} and the {@link ServiceAccountant} class.
 */
public class ServiceAccountantDemo {
  public static void main(String args[]) throws Exception {
    // Log SOAP XML request and response.
    AdWordsServiceLogger.log();

    // Get AdWordsUser from "~/adwords.properties".
    AdWordsUser user = new AdWordsUser();

    // Create the service accountant manager to automatically create
    // service accountants on first call and to not retain services.
    ServiceAccountantManager serviceAccountantManager = ServiceAccountantManager.getInstance();

    // Get CampaignService.
    CampaignServiceInterface campaignService =
        user.getService(AdWordsService.V201109.CAMPAIGN_SERVICE);

    // Get AdGroupService.
    AdGroupServiceInterface adGroupService =
        user.getService(AdWordsService.V201109.ADGROUP_SERVICE);

    // Get AdGroupAdService.
    AdGroupAdServiceInterface adGroupAdService =
        user.getService(AdWordsService.V201109.ADGROUP_AD_SERVICE);

    // Turn on retain service here to just retain the criterion service.
    serviceAccountantManager.setRetainServices(true);

    // Get AdGroupCriterionService.
    AdGroupCriterionServiceInterface adGroupCriterionService =
        user.getService(AdWordsService.V201109.ADGROUP_CRITERION_SERVICE);

    // Since autoCreateAccountant is set to true in the service accountant
    // manager, all service accountants will be created during this method call.
    performOperations(campaignService, adGroupService, adGroupAdService, adGroupCriterionService);

    // Get the CampaginService ServiceAccountant.
    ServiceAccountant campaignServiceAccountant =
        serviceAccountantManager.getServiceAccountant(campaignService);

    // Get the AdGroupService ServiceAccountant.
    ServiceAccountant adGroupServiceAccountant =
        serviceAccountantManager.getServiceAccountant(adGroupService);

    // Get the AdGroupAdService ServiceAccountant.
    ServiceAccountant adGroupAdServiceAccountant =
        serviceAccountantManager.getServiceAccountant(adGroupAdService);

    // Get the AdGroupCriterionService ServiceAccountant.
    ServiceAccountant adGroupCriterionServiceAccountant =
        serviceAccountantManager.getServiceAccountant(adGroupCriterionService);

    // Retrieve the total unit counts for each service object we used.
    System.out.println("CampaignService used " + campaignServiceAccountant.getTotalUnitCount()
        + " unit(s).");
    System.out.println("AdGroupService used " + adGroupServiceAccountant.getTotalUnitCount()
        + " unit(s).");
    System.out.println("AdService used " + adGroupAdServiceAccountant.getTotalUnitCount()
        + " unit(s).");
    System.out.println("CriterionService used "
        + adGroupCriterionServiceAccountant.getTotalUnitCount() + " unit(s).");

    // Retrieve the total amounts using the ServiceAccountantManager.
    System.out.println("There were " + ServiceAccountantManager.getInstance().getTotalUnitCount()
        + " unit(s) used all together.");
    System.out.println("There were "
        + ServiceAccountantManager.getInstance().getTotalOperationCount()
        + " operations performed all together.");
    System.out.println("There were "
        + ServiceAccountantManager.getInstance().getTotalResponseTime()
        + " milliseconds spent on the server.");

    // Get all services that were retained.
    Stub[] retainedServices = serviceAccountantManager.getRetainedServicesForUser(user);

    // Recall retained service to use again if needed.
    adGroupCriterionService = (AdGroupCriterionServiceInterface) retainedServices[0];

    // Retrieve the last response time for the criterionService object.
    System.out.println("The last call to CriterionService took "
        + serviceAccountantManager.getServiceAccountant(
            adGroupCriterionService).getLastResponseTime() + " milliseconds.");

    // Remove retained service so that it can be garbage collected.
    serviceAccountantManager.removeService(adGroupCriterionService);

    if (serviceAccountantManager.getRetainedServicesForUser(user).length == 0) {
      System.out.println("All services were cleared for garabage collection.");
    }

    // Remove all remaining retained services for the user, which should be
    // none at this point.
    serviceAccountantManager.removeAllServicesForUser(user);
  }

  /**
   * Performs a set of operations using the supplied services that should be
   * considered a black box for the purpose of this demo.
   *
   * @param campaignService the CampaignService object
   * @param adGroupService the AdGroupService object
   * @param adGroupAdService the AdGroupAdService object
   * @param adGroupCriterionService the AdGroupCriterionService object
   * @throws RemoteException thrown if one of the API operations fails
   * @throws ApiException thrown if one of the API operations fails
   */
  private static void performOperations(CampaignServiceInterface campaignService,
      AdGroupServiceInterface adGroupService, AdGroupAdServiceInterface adGroupAdService,
      AdGroupCriterionServiceInterface adGroupCriterionService)
      throws RemoteException {
    campaignService.get(new Selector());
    adGroupService.get(new Selector());
    adGroupAdService.get(new Selector());
    adGroupCriterionService.get(new Selector());
  }
}
