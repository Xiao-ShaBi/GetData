package com.wzf.getdatalib.data;

import com.wzf.getdatalib.ReflashData;

/**
 * Created by wzf on 2017/5/27.
 * 用来与activity绑定
 * 并没有绑定到生命周期中，需要自己手动调用清除
 */

public class BindingLife {

    private Class clazz;
    private ReflashData requestData;

    public BindingLife(Class clazz, ReflashData requestData) {
        this.clazz = clazz;
        this.requestData = requestData;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public ReflashData getRequestData() {
        return requestData;
    }

    public void setRequestData(ReflashData requestData) {
        this.requestData = requestData;
    }
}
