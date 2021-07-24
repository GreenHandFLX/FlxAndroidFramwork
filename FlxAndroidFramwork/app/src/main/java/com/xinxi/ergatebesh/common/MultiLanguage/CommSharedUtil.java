package com.xinxi.ergatebesh.common.MultiLanguage;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * 2021.7.21
 * CommSharedUtils
 * 通用的SharedPreferences
 */
public class CommSharedUtil {

    private static final String SHARED_PATH = "app_info";
    private static CommSharedUtil helper;
    private SharedPreferences sharedPreferences;


    private CommSharedUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
    }

    public static CommSharedUtil getInstance(Context context) {
        if (helper == null) {
            helper = new CommSharedUtil(context);
        }
        return helper;
    }

    public  void putInt(String key, int value) {
        Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public  int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }


    public  void putString(String key, String value) {
        Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.apply();
    }


    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }


    public  void putBoolean(String key, boolean value) {
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }


    public  boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public  int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public  void remove(String key) {
        Editor edit = sharedPreferences.edit();
        edit.remove(key);
        edit.apply();
    }

    public static boolean readBoolean(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getBoolean(k, false);
    }

    public static boolean readBoolean(Context context, String fileName,
                                      String k, boolean defBool) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getBoolean(k, defBool);
    }

}
