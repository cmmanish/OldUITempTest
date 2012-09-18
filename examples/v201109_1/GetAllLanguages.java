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
import com.google.api.adwords.v201109_1.cm.Language;
import com.google.api.adwords.v201109_1.cm.ConstantDataServiceInterface;

/**
 * This example gets all LanguageCriterion.
 *
 * Tags: ConstantDataService.getLanguageCriterion
 *
 * @author api.kwinter@gmail.com (Kevin Winter)
 */
public class GetAllLanguages {
  public static void main(String[] args) {
    try {
      // Log SOAP XML request and response.
      AdWordsServiceLogger.log();

      // Get AdWordsUser from "~/adwords.properties".
      AdWordsUser user = new AdWordsUser();

      // Get the ConstantDataService.
      ConstantDataServiceInterface constantDataService =
          user.getService(AdWordsService.V201109_1.CONSTANT_DATA_SERVICE);

      // Get all languages.
      Language[] languages = constantDataService.getLanguageCriterion();

      // Display results.
      for (Language language : languages) {
        System.out.printf("Language with name '%s' and ID '%d' was found.\n", language.getName(),
            language.getId());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
