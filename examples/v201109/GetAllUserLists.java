// Copyright 2011, Google Inc. All Rights Reserved.
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
import com.google.api.adwords.v201109.cm.OrderBy;
import com.google.api.adwords.v201109.cm.Selector;
import com.google.api.adwords.v201109.cm.SortOrder;
import com.google.api.adwords.v201109.cm.UserList;
import com.google.api.adwords.v201109.cm.UserListPage;
import com.google.api.adwords.v201109.cm.UserListServiceInterface;

/**
 * This example gets all users lists. To add a user list, run AddUserList.java.
 *
 * Tags: UserListService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllUserLists {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the UserListService.
      UserListServiceInterface userListService =
          user.getService(AdWordsService.V201109.USER_LIST_SERVICE);

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"Id", "Name", "Status", "Size"});
      selector.setOrdering(new OrderBy[] {new OrderBy("Name", SortOrder.ASCENDING)});

      // Get all user lists.
      UserListPage page = userListService.get(selector);

      // Display user lists.
      if (page.getEntries() != null) {
        for (UserList userList : page.getEntries()) {
          System.out.printf("User list with name '%s', id '%d', status '%s', and number of "
              + "users '%d' was found.\n", userList.getName(), userList.getId(),
              userList.getStatus(), userList.getSize());
        }
      } else {
        System.out.println("No user lists were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
