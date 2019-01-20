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
