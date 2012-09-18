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
import com.google.api.adwords.lib.utils.MapUtils;
import com.google.api.adwords.v201109_1.cm.Dimensions;
import com.google.api.adwords.v201109_1.cm.Media;
import com.google.api.adwords.v201109_1.cm.MediaPage;
import com.google.api.adwords.v201109_1.cm.MediaServiceInterface;
import com.google.api.adwords.v201109_1.cm.MediaSize;
import com.google.api.adwords.v201109_1.cm.OrderBy;
import com.google.api.adwords.v201109_1.cm.Predicate;
import com.google.api.adwords.v201109_1.cm.PredicateOperator;
import com.google.api.adwords.v201109_1.cm.Selector;
import com.google.api.adwords.v201109_1.cm.SortOrder;

import java.util.Map;

/**
 * This example gets all images. To upload an image, run UploadImage.java.
 *
 * Tags: MediaService.get
 *
 * @author api.arogal@gmail (Adam Rogal)
 */
public class GetAllImages {
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
      selector.setFields(new String[] {"MediaId", "Width", "Height", "MimeType"});
      selector.setOrdering(new OrderBy[] {new OrderBy("MediaId", SortOrder.ASCENDING)});

      // Create predicates.
      Predicate typePredicate =
          new Predicate("Type", PredicateOperator.IN, new String[] {"IMAGE"});
      selector.setPredicates(new Predicate[] {typePredicate});

      // Get all images.
      MediaPage page = mediaService.get(selector);

      // Display images.
      if (page != null && page.getEntries() != null) {
        for (Media image : page.getEntries()) {
          Map<MediaSize, Dimensions> dimensions = MapUtils.toMap(image.getDimensions());
          System.out.println("Image with id '" + image.getMediaId()
              + "', dimensions '" +  dimensions.get(MediaSize.FULL).getWidth() + "x"
              + dimensions.get(MediaSize.FULL).getHeight() + "', and MIME type '"
              + image.getMediaType() + "' was found.");
        }
      } else {
        System.out.println("No images were found.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
