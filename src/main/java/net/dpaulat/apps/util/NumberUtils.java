package net.dpaulat.apps.util;

public abstract class NumberUtils {
    public static Integer tryParseInt(String value) {
        Integer i = null;

        try {
            i = Integer.valueOf(value);
        } catch (NumberFormatException ex) {
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
