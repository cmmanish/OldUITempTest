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
import com.google.api.adwords.v201109_1.cm.Media;
import com.google.api.adwords.v201109_1.cm.MediaPage;
import com.google.api.adwords.v201109_1.cm.MediaServiceInterface;
import com.google.api.adwords.v201109_1.cm.OrderBy;
import com.google.api.adwords.v201109_1.cm.Predicate;
import com.google.api.adwords.v201109_1.cm.PredicateOperator;
import com.google.api.adwords.v201109_1.cm.Selector;
import com.google.api.adwords.v201109_1.cm.SortOrder;

/**
 * This example gets all videos. To upload a video, see
 * http://adwords.google.com/support/aw/bin/answer.py?hl=en&answer=39454.
 *
 * Tags: MediaService.get
 *
 * @category adx-exclude
 * @author api.arogal@gmail (Adam Rogal)
 */
public class GetAllVideos {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the MediaService.
      MediaServiceInterface mediaService =
          user.getService(AdWordsService.V201109_1.MEDIA_SERVICE);

      // Create selector.
      Selector selector = new Selector();
      selector.setFields(new String[] {"MediaId", "Name"});
      selector.setOrdering(new OrderBy[] {new OrderBy("MediaId", SortOrder.ASCENDING)});

      // Create predicates.
      Predicate typePredicate =
          new Predicate("Type", PredicateOperator.IN, new String[] {"VIDEO"});
      selector.setPredicates(new Predicate[] {typePredicate});

      // Get all videos.
      MediaPage page = mediaService.get(selector);

      // Display videos.
      if (page.getEntries() != null) {
        for (Media video : page.getEntries()) {
          System.out.println("Video with id '" + video.getMediaId()
              + "' and name '" + video.getName() + "' was found.");
        }
      } else {
        System.out.println("No videos were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
