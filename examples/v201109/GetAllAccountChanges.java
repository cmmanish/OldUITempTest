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
import com.google.api.adwords.v201109.ch.AdGroupChangeData;
import com.google.api.adwords.v201109.ch.CampaignChangeData;
import com.google.api.adwords.v201109.ch.ChangeStatus;
import com.google.api.adwords.v201109.ch.CustomerChangeData;
import com.google.api.adwords.v201109.ch.CustomerSyncSelector;
import com.google.api.adwords.v201109.ch.CustomerSyncServiceInterface;
import com.google.api.adwords.v201109.cm.Campaign;
import com.google.api.adwords.v201109.cm.CampaignPage;
import com.google.api.adwords.v201109.cm.CampaignServiceInterface;
import com.google.api.adwords.v201109.cm.DateTimeRange;
import com.google.api.adwords.v201109.cm.Selector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This example gets all account changes between the two dates specified, for
 * all campaigns.
 *
 * Tags: CustomerSyncService.get
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class GetAllAccountChanges {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the CampaignService.
      CampaignServiceInterface campaignService =
          user.getService(AdWordsService.V201109.CAMPAIGN_SERVICE);

      // Get the CustomerSyncService.
      CustomerSyncServiceInterface customerSyncService =
          user.getService(AdWordsService.V201109.CUSTOMER_SYNC_SERVICE);

      // Get a list of all campaign IDs.
      List<Long> campaignIds = new ArrayList<Long>();
      Selector selector = new Selector();
      selector.setFields(new String[] {"Id"});
      CampaignPage campaigns = campaignService.get(selector);
      if (campaigns.getEntries() != null) {
        for (Campaign campaign : campaigns.getEntries()) {
          campaignIds.add(campaign.getId());
        }
      }

      // Create date time range for the past 24 hours.
      DateTimeRange dateTimeRange = new DateTimeRange();
      dateTimeRange.setMin(new SimpleDateFormat("yyyyMMdd hhmmss").format(new Date(System
          .currentTimeMillis() - 1000L * 60 * 60 * 24)));
      dateTimeRange.setMax(new SimpleDateFormat("yyyyMMdd hhmmss").format(new Date()));

      // Create selector.
      CustomerSyncSelector customerSyncSelector = new CustomerSyncSelector();
      customerSyncSelector.setDateTimeRange(dateTimeRange);
      customerSyncSelector
          .setCampaignIds(ArrayUtils.toPrimitive(campaignIds.toArray(new Long[] {})));

      // Get all account changes for campaign.
      CustomerChangeData accountChanges = customerSyncService.get(customerSyncSelector);

      // Display changes.
      if (accountChanges != null && accountChanges.getChangedCampaigns() != null) {
        System.out.println("Most recent change: "
            + accountChanges.getLastChangeTimestamp() + "\n");
        for (CampaignChangeData campaignChanges : accountChanges.getChangedCampaigns()) {
          System.out.println("Campaign with id \"" + campaignChanges.getCampaignId()
              + "\" was changed: ");
          System.out.println("\tCampaign changed status: "
              + campaignChanges.getCampaignChangeStatus());
          if (campaignChanges.getCampaignChangeStatus() != ChangeStatus.NEW) {
            System.out.println("\tAdded ad extensions: "
                + getFormattedList(campaignChanges.getAddedAdExtensions()));
            System.out.println("\tAdded campaign criteria: "
                + getFormattedList(campaignChanges.getAddedCampaignCriteria()));
            System.out.println("\tAdded campaign targeting: "
                + campaignChanges.getCampaignTargetingChanged());
            System.out.println("\tDeleted ad extensions: "
                + getFormattedList(campaignChanges.getDeletedAdExtensions()));
            System.out.println("\tDeleted campaign criteria: "
                + getFormattedList(campaignChanges.getDeletedCampaignCriteria()));

            if (campaignChanges.getChangedAdGroups() != null) {
              for (AdGroupChangeData adGroupChanges : campaignChanges.getChangedAdGroups()) {
                System.out.println("\tAd goup with id \"" + adGroupChanges.getAdGroupId()
                    + "\" was changed: ");
                System.out.println("\t\tAd goup changed status: "
                    + adGroupChanges.getAdGroupChangeStatus());
                if (adGroupChanges.getAdGroupChangeStatus() != ChangeStatus.NEW) {
                  System.out.println("\t\tAds changed: "
                      + getFormattedList(adGroupChanges.getChangedAds()));
                  System.out.println("\t\tCriteria changed: "
                      + getFormattedList(adGroupChanges.getChangedCriteria()));
                  System.out.println("\t\tCriteria deleted: "
                      + getFormattedList(adGroupChanges.getDeletedCriteria()));
                }
              }
            }
          }
          System.out.println("");
        }
      } else {
        System.out.println("No account changes were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets a formatted list of a long array in the form {1,2,3}.
   * @param idList the long array
   * @return the formatted list
   */
  private static String getFormattedList(long[] idList) {
    if (idList == null) {
      idList = new long[]{};
    }
    return new StringBuilder().append("{")
        .append(StringUtils.join(ArrayUtils.toObject(idList), ','))
        .append("}").toString();
  }
}
