/*
 * Copyright 2021 Dan Paulat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dpaulat.apps.owlet;

import net.dpaulat.apps.ayla.json.AylaApplication;
import net.dpaulat.apps.owlet.json.OwletApplication;
import net.dpaulat.apps.owlet.json.OwletApplicationV2;
import net.dpaulat.apps.owlet.json.OwletApplicationV2Europe;

public class OwletApiConfig {
    private final String urlMini;
    private final String urlUser;
    private final String urlAds;
    private final String apiKey;
    private final AylaApplication application;

    public static final OwletApiConfig LEGACY =
            new OwletApiConfig("",
                               "https://user-field.aylanetworks.com",
                               "https://user-field.aylanetworks.com",
                               "", new OwletApplication());
    public static final OwletApiConfig WORLD =
            new OwletApiConfig("https://ayla-sso.owletdata.com/mini",
                               "https://user-field-1a2039d9.aylanetworks.com",
                               "https://ads-field-1a2039d9.aylanetworks.com",
                               "AIzaSyCsDZ8kWxQuLJAMVnmEhEkayH1TSxKXfGA",
                               new OwletApplicationV2());
    public static final OwletApiConfig EUROPE =
            new OwletApiConfig("https://ayla-sso.eu.owletdata.com/mini",
                               "https://user-field-eu-1a2039d9.aylanetworks.com",
                               "https://ads-field-eu-1a2039d9.aylanetworks.com",
                               "AIzaSyDm6EhV70wudwN3iOSq3vTjtsdGjdFLuuM",
                               new OwletApplicationV2Europe());

    private OwletApiConfig(String urlMini, String urlUser, String urlAds, String apiKey,
                           AylaApplication application) {
        this.urlMini = urlMini;
        this.urlUser = urlUser;
        this.urlAds = urlAds;
        this.apiKey = apiKey;
        this.application = application;
    }

    public static OwletApiConfig getConfig(OwletRegion region) {
        switch (region) {
            case World:
                return WORLD;

            case Europe:
                return EUROPE;

            default:
                return LEGACY;
        }
    }

    public String getUrlMini() {
        return urlMini;
    }

    public String getUrlUser() {
        return urlUser;
    }

    public String getUrlAds() {
        return urlAds;
    }

    public String getApiKey() {
        return apiKey;
    }

    public AylaApplication getApplication() {
        return application;
    }
}
