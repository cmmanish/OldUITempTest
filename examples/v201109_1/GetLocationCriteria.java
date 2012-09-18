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
import com.google.api.adwords.v201109_1.cm.Location;
import com.google.api.adwords.v201109_1.cm.LocationCriterion;
import com.google.api.adwords.v201109_1.cm.LocationCriterionServiceInterface;
import com.google.api.adwords.v201109_1.cm.Predicate;
import com.google.api.adwords.v201109_1.cm.PredicateOperator;
import com.google.api.adwords.v201109_1.cm.Selector;

/**
 * This example gets location criteria by name.
 *
 * Tags: LocationCriterionSerivce.get
 *
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class GetLocationCriteria {

  /**
   * Helper function to format a string for parent locations.
   *
   * @param parents
   * @return
   */
  public static String getParentLocationString(Location[] parents) {
    StringBuilder sb = new StringBuilder();
    if (parents != null) {
      for (Location parent : parents) {
        if (sb.length() > 0) {
          sb.append(", ");
        }
        sb.append(String.format("%s (%s)", parent.getLocationName(), parent.getDisplayType()));
      }
    } else {
      sb.append("N/A");
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the LocationCriterionService.
      LocationCriterionServiceInterface locationCriterionService =
          user.getService(AdWordsService.V201109_1.LOCATION_CRITERION_SERVICE);

      String[] locationNames = new String[] {"Paris", "Quebec", "Spain", "Deutschland"};
      Selector selector = new Selector();
      selector.setFields(new String[] {"Id", "LocationName", "CanonicalName", "DisplayType",
          "ParentLocations", "Reach"});

      selector.setPredicates(new Predicate[] {
          // Location names must match exactly, only EQUALS and IN are
          // supported.
          new Predicate("LocationName", PredicateOperator.IN, locationNames),
          // Set the locale of the returned location names.
          new Predicate("Locale", PredicateOperator.EQUALS, new String[] {"en"})});

      // Make the get request.
      LocationCriterion[] locationCriteria = locationCriterionService.get(selector);

      // Display the resulting location criteria.
      for (LocationCriterion locationCriterion : locationCriteria) {
        String parentString =
            getParentLocationString(locationCriterion.getLocation().getParentLocations());
        System.out.printf("The search term '%s' returned the location '%s (%d)' of type '%s' "
            + "with parent locations '%s' and reach '%d'.\n", locationCriterion.getSearchTerm(),
            locationCriterion.getLocation().getLocationName(), locationCriterion.getLocation()
                .getId(), locationCriterion.getLocation().getDisplayType(), parentString,
            locationCriterion.getReach());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
