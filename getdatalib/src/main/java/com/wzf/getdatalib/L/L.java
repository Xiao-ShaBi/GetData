package com.wzf.getdatalib.L;

import android.util.Log;

/**
 * Created by wzf on 2017/5/8.
 */

public class L {

    public static Boolean DEBUG = true;

    public static void e(String str) {
        if (DEBUG)
            Log.e("wzf", str);

    }
}
