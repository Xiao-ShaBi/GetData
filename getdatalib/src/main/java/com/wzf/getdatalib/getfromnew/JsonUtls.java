package com.wzf.getdatalib.getfromnew;

import com.alibaba.fastjson.JSON;

/**
 * Created by wzf on 2017/5/8.
 */

public class JsonUtls {

    /**
     * 将json字符串转化为json对象
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String str, Class<T> clazz) {
        return (T) JSON.parseObject(str, clazz);
    }

    /**
     * 将对象转化为json字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
