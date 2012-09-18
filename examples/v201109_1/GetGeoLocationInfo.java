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
import com.google.api.adwords.v201109_1.cm.Address;
import com.google.api.adwords.v201109_1.cm.GeoLocation;
import com.google.api.adwords.v201109_1.cm.GeoLocationSelector;
import com.google.api.adwords.v201109_1.cm.GeoLocationServiceInterface;
import com.google.api.adwords.v201109_1.cm.InvalidGeoLocation;

/**
 * This example gets the geo location information for addresses.
 *
 * Tags: GeoLocationService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetGeoLocationInfo {
  public static void main(String[] args) {
    try {
      //Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the GeoLocationService.
      GeoLocationServiceInterface geoLocationService =
          user.getService(AdWordsService.V201109_1.GEO_LOCATION_SERVICE);

      // Create addresses.
      Address address1 = new Address();
      address1.setStreetAddress("1600 Amphitheatre Pkwy");
      address1.setCityName("Mountain View");
      address1.setProvinceCode("US-CA");
      address1.setPostalCode("94043");
      address1.setCountryCode("US");

      Address address2 = new Address();
      address2.setStreetAddress("76 9th Ave");
      address2.setCityName("New York");
      address2.setProvinceCode("US-NY");
      address2.setPostalCode("10011");
      address2.setCountryCode("US");

      Address address3 = new Address();
      address3.setStreetAddress("五四大街1号, Beijing东城区");
      address3.setCountryCode("CN");

      // Create selector.
      GeoLocationSelector selector = new GeoLocationSelector();
      selector.setAddresses(new Address[] {address1, address2, address3});

      // Get geo locations.
      GeoLocation[] locations = geoLocationService.get(selector);

      // Display geo locations.
      if (locations != null) {
        for (GeoLocation location : locations) {
          if (location instanceof InvalidGeoLocation) {
            System.out.println("Invalid geo location was found.");
          } else {
            System.out.println("Geo location with street address \""
                + location.getAddress().getStreetAddress() + "\", latitude \""
                + location.getGeoPoint().getLatitudeInMicroDegrees() + "\", and longitude \""
                + location.getGeoPoint().getLongitudeInMicroDegrees() + "\" was found.");
          }
        }
      } else {
        System.out.println("No geo locations were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
