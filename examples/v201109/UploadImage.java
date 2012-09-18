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
import com.google.api.adwords.lib.utils.ImageUtils;
import com.google.api.adwords.lib.utils.MapUtils;
import com.google.api.adwords.v201109.cm.Dimensions;
import com.google.api.adwords.v201109.cm.Image;
import com.google.api.adwords.v201109.cm.Media;
import com.google.api.adwords.v201109.cm.MediaMediaType;
import com.google.api.adwords.v201109.cm.MediaServiceInterface;
import com.google.api.adwords.v201109.cm.MediaSize;

import java.util.Map;

/**
 * This example uploads an image. To get images, run GetAllImages.java.
 *
 * Tags: MediaService.upload
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class UploadImage {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the MediaService.
      MediaServiceInterface mediaService =
          user.getService(AdWordsService.V201109.MEDIA_SERVICE);

      // Create image.
      Image image = new Image();
      image.setData(ImageUtils.getImageDataFromUrl("http://goo.gl/HJM3L"));
      image.setType(MediaMediaType.IMAGE);

      Media[] media = new Media[] {image};

      // Upload image.
      Media[] result = mediaService.upload(media);

      // Display images.
      if (result != null) {
        image = (Image) result[0];
        Map<MediaSize, Dimensions> dimensions = MapUtils.toMap(image.getDimensions());
        System.out.println("Image with id '" + image.getMediaId()
            + "', dimensions '" +  dimensions.get(MediaSize.FULL).getWidth() + "x"
            + dimensions.get(MediaSize.FULL).getHeight() + "', and MIME type '"
            + image.getMediaType() + "' was uploaded.");
      } else {
        System.out.println("No images were uploaded.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
