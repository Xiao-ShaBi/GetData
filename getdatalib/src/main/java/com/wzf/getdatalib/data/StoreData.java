package com.wzf.getdatalib.data;

import com.wzf.getdatalib.ReflashData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wzf on 2017/5/8.
 * 用来存储网上请求到的数据
 */

public class StoreData {

    private static volatile StoreData store = null;

    /**
     * 用来存储网上请求到的数据
     */
    HashMap<Class, RequestData> datas = new HashMap<>();

    /**
     * 存储设置了tag的请求数据的对象
     */
    HashMap<Object, ArrayList<BindingLife>> tags = new HashMap<>();

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
     * 联网加载数据
     *
     * @param clazz 加载数据的bean
     * @param obj   加载的数据对象
     */
    public void setDatas(Class clazz, Object obj) {
        setDatas(clazz, obj, false);
    }

    /**
     * 存储数据
     */
    public void setDatas(Class clazz, Object obj, boolean is_local) {

        if (obj == null || clazz == null)
            return;


        /**
         * 存储数据，存储的同时回调需要刷新的对象
         */
        if (datas.containsKey(clazz)) {
            /**
             * 之前有数据，本地数据加载取消，直接返回
             */
            if (is_local) {
                return;
            }

            /**
             * 这里说明之前有对象请求过数据或者之前存储过数据
             * 需要刷新
             */
            RequestData requestData = datas.get(clazz);
            requestData.setData(obj);

            int size = requestData.list.size();
            for (int i = size - 1; i >= 0; i--) {
                ReflashData reflashData = requestData.list.get(i);
                if (reflashData == null)
                    continue;

                reflashData.refalsh(clazz);
                /**
                 * 不是true意味着数据更新只需要更新一次即可
                 */
                if (reflashData.refalshOrNot() != true)
                    requestData.list.remove(reflashData);

            }
        } else {
            /**
             * 说明没有人请求过，直接放置数据就好了
             */
            synchronized (StoreData.class) {
                if (!datas.containsKey(clazz)) {
                    RequestData requestData = new RequestData(obj);
                    requestData.is_local = is_local;
                    datas.put(clazz, requestData);
                } else {
                    setDatas(clazz, obj, is_local);
                }

            }
        }
    }

    /**
     * 這裡獲取數據需要添加一個fragment或者context對象，在destory中調用{@link #remove(java.lang.Object)}
     * 与activity绑定
     * 根据tag绑定 数据组，销毁时根据tag销毁
     *
     * @param obj
     * @param clazz
     * @param reflashData
     * @param <T>
     * @return
     */
    public <T> T getDatas(Object obj, Class<T> clazz, ReflashData reflashData) {
        T datas = getDatas(clazz, reflashData);
        if (datas != null && reflashData != null) {
            if (reflashData.refalshOrNot()) {
                if (obj != null) {
                    if (tags.containsKey(obj)) {
                        ArrayList<BindingLife> bindingLifes = tags.get(obj);
                        for (int i = 0; i < bindingLifes.size(); i++) {
                            if (bindingLifes.get(i).getRequestData() == reflashData) {
                                return datas;
                            }
                        }
                        bindingLifes.add(new BindingLife(clazz, reflashData));
                    }
                }
            }
            return datas;
        }

        return null;
    }

    /**
     * 根据传入的fragment或者activity注销
     * 根据tag清空请求的数据
     *
     * @param obj
     */
    public void remove(Object obj) {
        if (obj == null || !tags.containsKey(obj)) {
            return;
        }
        ArrayList<BindingLife> bindingLifes = tags.get(obj);
        tags.remove(obj);
        if (bindingLifes == null || bindingLifes.size() <= 0) {
            return;
        }
        int size = bindingLifes.size();
        for (int i = 0; i < size; i++) {
            BindingLife bindingLife = bindingLifes.get(i);
            if (bindingLife == null) {
                continue;
            }
            remove(bindingLife.getClazz(), bindingLife.getRequestData());
        }
    }

    /**
     * 取数据
     * <p>
     * return null 意味着没有取到数据，等待刷新返回数据即可
     */
    public <T> T getDatas(Class<T> clazz, ReflashData reflashData) {
        if (clazz == null) {
            throw new RuntimeException("要取得数据的类型不能为空");
        }
        if (datas.containsKey(clazz)) {
            RequestData requestData = datas.get(clazz);
            if (reflashData != null && reflashData.refalshOrNot())
                requestData.setRequestList(reflashData);

            return (T) requestData.obj;
        }

        datas.put(clazz, new RequestData(reflashData));
        return null;
    }

    /**
     * todo
     * !!!注意了，每当请求了数据的时候，就需要注意是否还有对象存在其中防止内存泄露
     * 每次请求数据之后请在不需要的时候或者即将销毁的时候清除list中的对象
     *
     * @param clazz
     * @param reflashData
     */
    public void remove(Class clazz, ReflashData reflashData) {
        if (clazz == null || reflashData == null) {
            return;
        }
        RequestData requestData = datas.get(clazz);
        if (reflashData != null) {
            requestData.remove(reflashData);
        }
    }
}
