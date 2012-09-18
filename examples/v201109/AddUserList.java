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
import com.google.api.adwords.v201109.cm.AdWordsConversionTracker;
import com.google.api.adwords.v201109.cm.ConversionTracker;
import com.google.api.adwords.v201109.cm.ConversionTrackerPage;
import com.google.api.adwords.v201109.cm.ConversionTrackerServiceInterface;
import com.google.api.adwords.v201109.cm.Operator;
import com.google.api.adwords.v201109.cm.Predicate;
import com.google.api.adwords.v201109.cm.PredicateOperator;
import com.google.api.adwords.v201109.cm.RemarketingUserList;
import com.google.api.adwords.v201109.cm.Selector;
import com.google.api.adwords.v201109.cm.UserList;
import com.google.api.adwords.v201109.cm.UserListConversionType;
import com.google.api.adwords.v201109.cm.UserListOperation;
import com.google.api.adwords.v201109.cm.UserListReturnValue;
import com.google.api.adwords.v201109.cm.UserListServiceInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This example adds a remarketing user list.
 *
 * Tags: UserListService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AddUserList {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the UserListService.
      UserListServiceInterface userListService =
          user.getService(AdWordsService.V201109.USER_LIST_SERVICE);

      // Get the ConversionTrackerService.
      ConversionTrackerServiceInterface conversionTrackerService =
          user.getService(AdWordsService.V201109.CONVERSION_TRACKER_SERVICE);

      // Create conversion type (tag).
      UserListConversionType conversionType = new UserListConversionType();
      conversionType.setName("Mars cruise customers #" + System.currentTimeMillis());

      // Create remarketing user list.
      RemarketingUserList userList = new RemarketingUserList();
      userList.setName("Mars cruise customers #" + System.currentTimeMillis());
      userList.setDescription("A list of mars cruise customers in the last year");
      userList.setMembershipLifeSpan(365L);
      userList.setConversionTypes(new UserListConversionType[] {conversionType});

      // Create operations.
      UserListOperation operation = new UserListOperation();
      operation.setOperand(userList);
      operation.setOperator(Operator.ADD);

      UserListOperation[] operations = new UserListOperation[] {operation};

      // Add user list.
      UserListReturnValue result = userListService.mutate(operations);

      // Display results.
      if (result != null && result.getValue() != null) {
        // Capture the ID(s) of the conversion.
        List<String> conversionIds = new ArrayList<String>();
        for (UserList userListResult : result.getValue()) {
          if (userListResult instanceof RemarketingUserList) {
            RemarketingUserList remarketingUserList = (RemarketingUserList) userListResult;
            for (UserListConversionType userListConversionType :
                remarketingUserList.getConversionTypes()) {
              conversionIds.add(userListConversionType.getId().toString());
            }
          }
        }

        // Create predicate and selector.
        Predicate predicate = new Predicate();
        predicate.setField("Id");
        predicate.setOperator(PredicateOperator.IN);
        predicate.setValues(conversionIds.toArray(new String[0]));
        Selector selector = new Selector();
        selector.setFields(new String[] {"Id"});
        selector.setPredicates(new Predicate[] {predicate});

        // Get all conversion trackers.
        Map<Long, AdWordsConversionTracker> conversionTrackers =
            new HashMap<Long, AdWordsConversionTracker>();
        ConversionTrackerPage page = conversionTrackerService.get(selector);
        if (page != null && page.getEntries() != null) {
          for (ConversionTracker conversionTracker : page.getEntries()) {
            conversionTrackers.put(conversionTracker.getId(),
                (AdWordsConversionTracker) conversionTracker);
          }
        }

        // Display user lists.
        for (UserList userListResult : result.getValue()) {
          System.out.printf("User list with name '%s' and id '%d' was added.\n",
              userListResult.getName(), userListResult.getId());

          // Display user list associated conversion code snippets.
          if (userListResult instanceof RemarketingUserList) {
            RemarketingUserList remarketingUserList = (RemarketingUserList) userListResult;
            for (UserListConversionType userListConversionType :
                remarketingUserList.getConversionTypes()) {
              System.out.printf("Conversion type code snippet associated to the list:\n%s\n",
                  conversionTrackers.get(userListConversionType.getId()).getSnippet());
            }
          }
        }
      } else {
        System.out.println("No user lists were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
