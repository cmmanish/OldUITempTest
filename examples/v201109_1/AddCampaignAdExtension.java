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
import com.google.api.adwords.v201109_1.cm.Address;
import com.google.api.adwords.v201109_1.cm.CampaignAdExtension;
import com.google.api.adwords.v201109_1.cm.CampaignAdExtensionOperation;
import com.google.api.adwords.v201109_1.cm.CampaignAdExtensionReturnValue;
import com.google.api.adwords.v201109_1.cm.CampaignAdExtensionServiceInterface;
import com.google.api.adwords.v201109_1.cm.GeoLocation;
import com.google.api.adwords.v201109_1.cm.GeoLocationSelector;
import com.google.api.adwords.v201109_1.cm.GeoLocationServiceInterface;
import com.google.api.adwords.v201109_1.cm.LocationExtension;
import com.google.api.adwords.v201109_1.cm.LocationExtensionSource;
import com.google.api.adwords.v201109_1.cm.Operator;

/**
 * This example adds a location ad extension to a campaign for a location
 * obtained from the GeoLocationService. To get campaigns, run
 * GetAllCampaigns.java.
 *
 * Tags: GeoLocationService.get, CampaignAdExtensionService.mutate
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AddCampaignAdExtension {
  public static void main(String[] args) {
    try {
   // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignAdExtensionService.
      CampaignAdExtensionServiceInterface campaignAdExtensionService =
          user.getService(AdWordsService.V201109_1.CAMPAIGN_AD_EXTENSION_SERVICE);

      // Get the GeoLocationService.
      GeoLocationServiceInterface geoLocationService =
          user.getService(AdWordsService.V201109_1.GEO_LOCATION_SERVICE);

      long campaignId = Long.parseLong("INSERT_CAMPAIGN_ID_HERE");

      // Create address.
      Address address = new Address();
      address.setStreetAddress("1600 Amphitheatre Parkway");
      address.setCityName("Mountain View");
      address.setProvinceCode("US-CA");
      address.setPostalCode("94043");
      address.setCountryCode("US");

      // Create geo location selector.
      GeoLocationSelector selector = new GeoLocationSelector();
      selector.setAddresses(new Address[] {address});

      // Get geo location.
      GeoLocation[] geoLocationResult = geoLocationService.get(selector);
      GeoLocation geoLocation = geoLocationResult[0];

      // Create location extension.
      LocationExtension locationExtension = new LocationExtension();
      locationExtension.setAddress(geoLocation.getAddress());
      locationExtension.setGeoPoint(geoLocation.getGeoPoint());
      locationExtension.setEncodedLocation(geoLocation.getEncodedLocation());
      locationExtension.setCompanyName("Google");
      locationExtension.setPhoneNumber("650-253-0000");
      locationExtension.setSource(LocationExtensionSource.ADWORDS_FRONTEND);

      // Create campaign ad extension.
      CampaignAdExtension campaignAdExtension = new CampaignAdExtension();
      campaignAdExtension.setCampaignId(campaignId);
      campaignAdExtension.setAdExtension(locationExtension);

      // Create operations.
      CampaignAdExtensionOperation operation = new CampaignAdExtensionOperation();
      operation.setOperand(campaignAdExtension);
      operation.setOperator(Operator.ADD);

      CampaignAdExtensionOperation[] operations = new CampaignAdExtensionOperation[] {operation};

      // Add campaign ad extension.
      CampaignAdExtensionReturnValue result = campaignAdExtensionService.mutate(operations);

      // Display campaign ad extensions.
      if (result != null && result.getValue() != null) {
        for (CampaignAdExtension campaignAdExtensionResult : result.getValue()) {
          System.out.println("Campaign ad extension with campaign id \""
              + campaignAdExtensionResult.getCampaignId() + "\", ad extension id \""
              + campaignAdExtensionResult.getAdExtension().getId() + "\", and type \""
              + campaignAdExtensionResult.getAdExtension().getAdExtensionType()
              + "\" was added.");
        }
      } else {
        System.out.println("No campaign ad extensions were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
