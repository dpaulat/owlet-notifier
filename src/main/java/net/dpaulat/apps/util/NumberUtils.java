/*
 * Copyright 2019 Dan Paulat
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

package net.dpaulat.apps.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NumberUtils {

    private static final Logger log = LoggerFactory.getLogger(NumberUtils.class);

    public static Integer tryParseInt(String value) {
        Integer i = null;

        try {
            i = Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            log.debug("Cannot parse integer: {}", value);
        }

        return i;
    }

    public static Boolean tryParseBoolean(String value) {
        Boolean b = null;

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            b = Boolean.valueOf(value);
        } else {
            Integer i = tryParseInt(value);
            if (i != null) {
                b = (i > 0);
            }
        }

        return b;
    }
}
