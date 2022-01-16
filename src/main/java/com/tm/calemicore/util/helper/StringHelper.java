package com.tm.calemicore.util.helper;

/**
 * Use this class to help format strings.
 */
public class StringHelper {

    /**
     * Surrounds the string with brackets.
     * @param str The string to be boxed.
     * @return a boxed string (like [str])
     */
    public static String boxString(String str) {
        return "[" + str + "]";
    }
}
