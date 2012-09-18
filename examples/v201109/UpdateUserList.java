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
import com.google.api.adwords.v201109.cm.Operator;
import com.google.api.adwords.v201109.cm.RemarketingUserList;
import com.google.api.adwords.v201109.cm.UserList;
import com.google.api.adwords.v201109.cm.UserListOperation;
import com.google.api.adwords.v201109.cm.UserListReturnValue;
import com.google.api.adwords.v201109.cm.UserListServiceInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This example updates a user list by setting its description. To get user
 * lists, run GetAllUserLists.java.
 *
 * Tags: UserListService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class UpdateUserList {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the UserListService.
      UserListServiceInterface userListService =
          user.getService(AdWordsService.V201109.USER_LIST_SERVICE);

      Long userListId = Long.parseLong("INSERT_USER_LIST_ID_HERE");

      // Create remarketing user list with updated description.
      RemarketingUserList userList = new RemarketingUserList();
      userList.setId(userListId);
      userList.setDescription("Last updated at " + new SimpleDateFormat().format(new Date()));

      // Create operations.
      UserListOperation operation = new UserListOperation();
      operation.setOperand(userList);
      operation.setOperator(Operator.SET);

      UserListOperation[] operations = new UserListOperation[] {operation};

      // Update user list.
      UserListReturnValue result = userListService.mutate(operations);

      // Display user lists.
      if (result != null && result.getValue() != null) {
        for (UserList userListResult : result.getValue()) {
          System.out.printf(
              "User list with name '%s', id '%d', and description '%s' was updated.\n",
              userListResult.getName(), userListResult.getId(), userListResult.getDescription());
        }
      } else {
        System.out.println("No user lists were updated.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
