package com.wzf.getdatalib.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wzf on 2017/5/17.
 * 用来存储数据到本地
 */

public class CatchUtils {

    public static final String FILE_NAME = "datastore_wzf_file_name";

    /**
     * sp存储
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveSPCatch(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * sp获取
     *
     * @param context
     * @param key
     * @return
     */
    public static String getSPCatch(Context context, String key) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(key, "");
    }

    
}
