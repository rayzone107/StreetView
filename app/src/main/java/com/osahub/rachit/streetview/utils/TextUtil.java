package com.osahub.rachit.streetview.utils;

/**
 * Created by Rachit Goyal on 01/05/18
 */

public class TextUtil {
    public static boolean isEmpty(String text) {
        return !(text != null && !text.isEmpty() && !text.equals("null"));
    }
}
