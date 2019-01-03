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
}
