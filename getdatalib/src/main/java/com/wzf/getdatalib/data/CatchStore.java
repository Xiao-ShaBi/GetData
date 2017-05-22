package com.wzf.getdatalib.data;

import android.content.Context;
import android.text.TextUtils;

import com.wzf.getdatalib.ReflashData;
import com.wzf.getdatalib.getfromnew.JsonUtls;
import com.wzf.getdatalib.local.CatchUtils;

/**
 * Created by wzf on 2017/5/22.
 * 给存储的数据添加缓存
 */

public class CatchStore {

    /**
     * 这里使用的context最好为app的
     * 这里设置存储有缓存
     */
    public static void setDatas(final Context context, final Class clazz, final Object obj) {
        if (obj == null || clazz == null)
            return;

        StoreData.getStore().setDatas(clazz, obj);

        //已经添加进内存中，下边需要添加入缓存中
        new Thread(new Runnable() {
            @Override
            public void run() {
                CatchUtils.saveSPCatch(context, clazz.getSimpleName(), JsonUtls.toJson(obj));
            }
        }).start();
    }

    public static <T> T getStoreData(final Context context, final Class<T> clazz, ReflashData reflashData) {

        T datas = StoreData.getStore().getDatas(clazz, reflashData);
        if (datas != null) {
            return datas;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String spCatch = CatchUtils.getSPCatch(context, clazz.getSimpleName());
                if (!TextUtils.isEmpty(spCatch)) {
                    T t = JsonUtls.fromJson(spCatch, clazz);
                    StoreData.getStore().setDatas(clazz, t, true);
                }
            }
        }).start();
        return null;
    }
}
