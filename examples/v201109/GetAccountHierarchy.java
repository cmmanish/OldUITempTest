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
import com.google.api.adwords.v201109.mcm.Account;
import com.google.api.adwords.v201109.mcm.Link;
import com.google.api.adwords.v201109.mcm.ServicedAccountGraph;
import com.google.api.adwords.v201109.mcm.ServicedAccountSelector;
import com.google.api.adwords.v201109.mcm.ServicedAccountServiceInterface;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* This example gets the account hierarchy under the current account.
 *
 * Tags: ServicedAccountService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAccountHierarchy {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser().generateClientAdWordsUser(null);

      // Get the ServicedAccountService.
      ServicedAccountServiceInterface servicedAccountService =
          user.getService(AdWordsService.V201109.SERVICED_ACCOUNT_SERVICE);

      // Create selector.
      ServicedAccountSelector selector = new ServicedAccountSelector();
      // To get the links paging must be disabled.
      selector.setEnablePaging(false);

      // Get serviced account graph.
      ServicedAccountGraph graph = servicedAccountService.get(selector);

      // Display serviced account graph.
      if (graph != null) {
        // Create map from customerId to account node.
        Map<Long, AccountTreeNode> customerIdToAccountNode = new HashMap<Long, AccountTreeNode>();

        // Create account tree nodes for each account.
        for (Account account : graph.getAccounts()) {
          AccountTreeNode node = new AccountTreeNode();
          node.account = account;
          customerIdToAccountNode.put(account.getCustomerId(), node);
        }

        // For each link, connect nodes in tree.
        if (graph.getLinks() != null) {
          for (Link link : graph.getLinks()) {
            AccountTreeNode managerNode = customerIdToAccountNode.get(link.getManagerId().getId());
            AccountTreeNode childNode = customerIdToAccountNode.get(link.getClientId().getId());
            childNode.parentLink = link;
            childNode.parentNode = managerNode;
            if (managerNode != null) managerNode.childAccounts.add(childNode);
          }
        }

        // Find the root account node in the tree.
        AccountTreeNode rootNode = null;
        for (Account account : graph.getAccounts()) {
          if (customerIdToAccountNode.get(account.getCustomerId()).parentNode == null) {
            rootNode = customerIdToAccountNode.get(account.getCustomerId());
            break;
          }
        }

        // Display account tree.
        System.out.println("Login, CustomerId (Status)");
        System.out.println(rootNode.toTreeString(0, new StringBuffer()));
      } else {
        System.out.println("No serviced accounts were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Example implementation of a node that would exist in an account tree.
   */
  private static class AccountTreeNode {
    protected AccountTreeNode parentNode;
    protected Link parentLink;
    protected Account account;
    protected List<AccountTreeNode> childAccounts = new ArrayList<AccountTreeNode>();

    /**
     * Default constructor.
     */
    public AccountTreeNode() {}

    @Override
    public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(String.format("%s, %s", account.getLogin(), account.getCustomerId()));
      if (parentLink != null) {
        sb.append(String.format(" (%s)", parentLink.getTypeOfLink()));
      }
      return sb.toString();
    }

    /**
     * Returns a string representation of the current level of the tree and
     * recursively returns the string representation of the levels below it.
     *
     * @param depth the depth of the node
     * @param sb the string buffer containing the tree representation
     * @return the tree string representation
     */
    public StringBuffer toTreeString(int depth, StringBuffer sb) {
      sb.append(StringUtils.repeat("-", depth * 2)).append(this).append("\n");
      for (AccountTreeNode childAccount : childAccounts) {
        childAccount.toTreeString(depth + 1, sb);
      }
      return sb;
    }
  }
}
