package com.wzf.getdatalib.data;

import com.wzf.getdatalib.ReflashData;

import java.util.ArrayList;

/**
 * Created by wzf on 2017/5/8.
 * 存放data数据以及请求过数据的对象
 */

public class RequestData {


    /**
     * 请求过数据的对象
     */
    public ArrayList<ReflashData> list;

    /**
     * 请求的数据
     */
    public Object obj;

    public synchronized void setRequestList(ReflashData refalsh) {
        if (list == null)
            list = new ArrayList<>();


        if (!list.contains(refalsh))
            list.add(refalsh);

    }

    public RequestData(Object obj) {
        this.obj = obj;
    }

    public RequestData(ReflashData reflashData) {
        setRequestList(reflashData);
    }

    public void setData(Object obj) {
        if (obj != null)
            this.obj = obj;

    }

    public void remove(ReflashData refalsh) {
        if (list != null)
            list.remove(refalsh);

    }

    public void removeAll() {
        if (list != null)
            list.clear();
    }
}
