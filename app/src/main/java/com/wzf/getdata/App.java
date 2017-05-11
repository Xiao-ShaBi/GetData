package com.wzf.getdata;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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


        /**
         * 使用反射将自定义的Instrumentation加入其中
         */
        try {
            MyInstrumentation ins = new MyInstrumentation();

            Class cls = Class.forName("android.app.ActivityThread"); // ActivityThread被隐藏了，所以通过这种方式获得class对象

            Method mthd = cls.getDeclaredMethod("currentActivityThread", (Class[]) null); // 获取当前ActivityThread对象引用

            Object currentAT = mthd.invoke(null, (Object[]) null);

            Field mInstrumentation = currentAT.getClass().getDeclaredField("mInstrumentation");

            mInstrumentation.setAccessible(true);

            mInstrumentation.set(currentAT, ins); // 修改ActivityThread.mInstrumentation值
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    class MyInstrumentation extends Instrumentation {
        public MyInstrumentation() {
            Log.e("TAG", "这里走了的");
        }

        @Override
        public void callActivityOnCreate(Activity activity, Bundle icicle) {
            super.callActivityOnCreate(activity, icicle);
            Log.e("TAG", "在这里做点什么坏事呢");
            if (activity instanceof MainActivity) {
                Log.e("TAG", "在这里做点什么坏事呢");
            }
        }

        @Override
        public void callActivityOnCreate(Activity activity, Bundle icicle, PersistableBundle persistentState) {
            super.callActivityOnCreate(activity, icicle, persistentState);
            Log.e("TAG", "callActivityOnCreate1");
        }
    }


}
