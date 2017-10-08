package com.example.megapiespt.linknotealpha.util;

import android.util.Log;

/**
 * Created by MegapiesPT on 10/6/2560.
 */

public class LogWrapper {
    private static String tag = "Link-Note Debug";
    public static void d(String text){
        Log.d(tag, text);
    }
    public static void d(String text, Throwable tr){
        Log.d(tag, text, tr);
    }
}
