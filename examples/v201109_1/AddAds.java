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
import com.google.api.adwords.lib.utils.ImageUtils;
import com.google.api.adwords.v201109_1.cm.AdGroupAd;
import com.google.api.adwords.v201109_1.cm.AdGroupAdOperation;
import com.google.api.adwords.v201109_1.cm.AdGroupAdReturnValue;
import com.google.api.adwords.v201109_1.cm.AdGroupAdServiceInterface;
import com.google.api.adwords.v201109_1.cm.Dimensions;
import com.google.api.adwords.v201109_1.cm.Image;
import com.google.api.adwords.v201109_1.cm.ImageAd;
import com.google.api.adwords.v201109_1.cm.MediaMediaType;
import com.google.api.adwords.v201109_1.cm.Operator;
import com.google.api.adwords.v201109_1.cm.TemplateAd;
import com.google.api.adwords.v201109_1.cm.TemplateElement;
import com.google.api.adwords.v201109_1.cm.TemplateElementField;
import com.google.api.adwords.v201109_1.cm.TemplateElementFieldType;
import com.google.api.adwords.v201109_1.cm.TextAd;
import com.google.api.adwords.v201109_1.cm.Video;

/**
 * This example adds a text ad, an image ad, and a template ad to an ad group.
 * To get ad groups, run GetAllAdGroups.java. To get all videos, run
 * GetAllVideos.php. To upload videos, see
 * http://adwords.google.com/support/aw/bin/answer.py?hl=en&answer=39454.
 *
 * Tags: AdGroupAdService.mutate
 *
 * @author api.arogal@gmail.com (Adam Rogal)
 */
public class AddAds {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the AdGroupAdService.
      AdGroupAdServiceInterface adGroupAdService =
          user.getService(AdWordsService.V201109_1.ADGROUP_AD_SERVICE);

      long adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");
      long videoMediaId = Long.parseLong("INSERT_VIDEO_MEDIA_ID_HERE");

      // Create text ad.
      TextAd textAd = new TextAd();
      textAd.setHeadline("Luxury Cruise to Mars");
      textAd.setDescription1("Visit the Red Planet in style.");
      textAd.setDescription2("Low-gravity fun for everyone!");
      textAd.setDisplayUrl("www.example.com");
      textAd.setUrl("http://www.example.com");

      // Create ad group ad.
      AdGroupAd textAdGroupAd = new AdGroupAd();
      textAdGroupAd.setAdGroupId(adGroupId);
      textAdGroupAd.setAd(textAd);

      // Create image ad.
      ImageAd imageAd = new ImageAd();
      imageAd.setName("Cruise to mars image ad #" + System.currentTimeMillis());
      imageAd.setDisplayUrl("www.example.com");
      imageAd.setUrl("http://example.com");

      // Create image.
      Image image = new Image();
      image.setData(ImageUtils.getImageDataFromUrl("http://goo.gl/HJM3L"));
      imageAd.setImage(image);

      // Create ad group ad.
      AdGroupAd imageAdGroupAd = new AdGroupAd();
      imageAdGroupAd.setAdGroupId(adGroupId);
      imageAdGroupAd.setAd(imageAd);

      // Create template ad, using the Click to Play Video template (id 9).
      TemplateAd templateAd = new TemplateAd();
      templateAd.setTemplateId(9L);
      templateAd.setDimensions(new Dimensions(300, 250));
      templateAd.setName("Mars Cruise video ad #" + System.currentTimeMillis());
      templateAd.setDisplayUrl("www.example.com");
      templateAd.setUrl("http://www.example.com");

      // Create template ad data.
      Image startImage = new Image();
      startImage.setData(ImageUtils.getImageDataFromUrl("http://goo.gl/HJM3L"));
      startImage.setType(MediaMediaType.IMAGE);
      Video video = new Video();
      video.setMediaId(videoMediaId);
      video.setType(MediaMediaType.VIDEO);

      templateAd.setTemplateElements(new TemplateElement[] {
          new TemplateElement("adData", new TemplateElementField[] {
              new TemplateElementField("startImage", TemplateElementFieldType.IMAGE,
                  null, startImage),
              new TemplateElementField("displayUrlColor", TemplateElementFieldType.ENUM,
                  "#ffffff", null),
              new TemplateElementField("video", TemplateElementFieldType.VIDEO, null, video)})
      });

      // Create ad group ad.
      AdGroupAd templateAdGroupAd = new AdGroupAd();
      templateAdGroupAd.setAdGroupId(adGroupId);
      templateAdGroupAd.setAd(templateAd);

      // Create operations.
      AdGroupAdOperation textAdGroupAdOperation = new AdGroupAdOperation();
      textAdGroupAdOperation.setOperand(textAdGroupAd);
      textAdGroupAdOperation.setOperator(Operator.ADD);

      AdGroupAdOperation imageAdGroupAdOperation = new AdGroupAdOperation();
      imageAdGroupAdOperation.setOperand(imageAdGroupAd);
      imageAdGroupAdOperation.setOperator(Operator.ADD);

      AdGroupAdOperation templateAdGroupAdOperation = new AdGroupAdOperation();
      templateAdGroupAdOperation.setOperand(templateAdGroupAd);
      templateAdGroupAdOperation.setOperator(Operator.ADD);

      AdGroupAdOperation[] operations = new AdGroupAdOperation[] {textAdGroupAdOperation,
          imageAdGroupAdOperation, templateAdGroupAdOperation};

      // Add ads.
      AdGroupAdReturnValue result = adGroupAdService.mutate(operations);

      // Display ads.
      if (result != null && result.getValue() != null) {
        for (AdGroupAd adGroupAdResult : result.getValue()) {
          System.out.println("Ad with id  \"" + adGroupAdResult.getAd().getId() + "\""
              + " and type \"" + adGroupAdResult.getAd().getAdType() + "\" was added.");
        }
      } else {
        System.out.println("No ads were added.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
