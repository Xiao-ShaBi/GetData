package com.wzf.getdatalib.getfromnew;

import com.alibaba.fastjson.JSON;

/**
 * Created by wzf on 2017/5/8.
 */

public class JsonUtls {

    public static <T> T fromJson(String str, Class<T> clazz) throws Exception {
        return (T) JSON.parseObject(str, clazz);
    }
}
