package org.fomky.ratpack.core.utils;


import java.util.UUID;

/**
 * @author Created by chenxx on 2016/6/21.
 */
public class StringUtil {
    public final static String reg_number = "^-?[0-9]+(.[0-9]+)?$";
    public final static String reg_int = "^-?[0-9]+";

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty() && !value.trim().isEmpty();
    }

    public static int asint(String value, int def) {
        if (isNotEmpty(value) && value.matches(reg_int)) {
            return Integer.valueOf(value);
        }
        return def;
    }

    public static Integer asint(String value, Integer def) {
        if (isNotEmpty(value) && value.matches(reg_int)) {
            return Integer.valueOf(value);
        }
        return def;
    }

    public static double asDouble(String value, double def) {
        if (isNotEmpty(value) && value.matches(reg_number)) {
            return Double.valueOf(value);
        }
        return def;
    }

    public static float asFloat(String value, float def) {
        if (isNotEmpty(value) && value.matches(reg_number)) {
            return Float.valueOf(value);
        }
        return def;
    }

    public static long asLong(String value, long def) {
        if (isNotEmpty(value) && value.matches(reg_number)) {
            return Long.valueOf(value);
        }
        return def;
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
