package com.wzf.getdatalib.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wzf on 2017/5/17.
 * 用来存储数据到本地
 */

public class CacheUtils {

    public static final String FILE_NAME = "datastore_wzf_file_name";

    /**
     * sp存储
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveSPCache(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * sp获取
     *
     * @param context
     * @param key
     * @param defult
     * @return
     */
    public static String getSPCache(Context context, String key, String defult) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(key, defult);
    }

    public static String getSPCache(Context context, String key) {
        return getSPCache(context, key, "");
    }
}
