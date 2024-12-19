package com.tabisketch.util;

public class StringUtils {
    public static boolean isNullAndEmpty(final String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNullAndNotEmpty(final String str) {
        return str != null && !str.isEmpty();
    }
}
