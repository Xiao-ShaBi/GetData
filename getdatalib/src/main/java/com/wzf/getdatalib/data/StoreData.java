package com.wzf.getdatalib.data;

import com.wzf.getdatalib.RefalshData;

import java.util.HashMap;

/**
 * Created by wzf on 2017/5/8.
 * 用来存储网上请求到的数据
 */

public class StoreData {

    private static volatile StoreData store;

    /**
     * 用来存储网上请求到的数据
     */
    HashMap<Class, RequestData> datas = new HashMap<>();

    private StoreData() {

    }

    public static StoreData getStore() {
        if (store == null) {
            synchronized (StoreData.class) {
                if (store == null) {
                    store = new StoreData();
                }
            }
        }
        return store;
    }

    /**
     * 存储数据
     */
    public void setDatas(Class clazz, Object obj) {

        if (obj == null || clazz == null)
            return;


        /**
         * 存储数据，存储的同时回调需要刷新的对象
         */
        if (datas.containsKey(clazz)) {
            /**
             * 这里说明之前有对象请求过数据或者之前存储过数据
             * 需要刷新
             */
            RequestData requestData = datas.get(clazz);
            requestData.setData(obj);

            int size = requestData.list.size();
            for (int i = size - 1; i >= 0; i--) {
                RefalshData refalshData = requestData.list.get(i);
                refalshData.refalsh(clazz);
                /**
                 * 不是true意味着数据更新时不通知他
                 */
                if (refalshData.refalshOrNot() != true)
                    requestData.list.remove(refalshData);

            }
        } else {
            /**
             * 说明没有人请求过，直接放置数据就好了
             */
            datas.put(clazz, new RequestData(obj));
        }
    }

    /**
     * 取数据
     * <p>
     * return null 意味着没有取到数据，等待刷新返回数据即可
     */
    public <T> T getDatas(Class<T> clazz, RefalshData refalshData) {
        if (datas.containsKey(clazz)) {
            RequestData requestData = datas.get(clazz);
            if (refalshData != null && refalshData.refalshOrNot())
                requestData.setRequestList(refalshData);

            return (T) requestData.obj;
        }

        datas.put(clazz, new RequestData(refalshData));
        return null;
    }

}
