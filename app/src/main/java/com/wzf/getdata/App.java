package com.wzf.getdata;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by wzf on 2017/5/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 初始化okhttputils
         */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
    // 这是个例子，这里做更新
}
