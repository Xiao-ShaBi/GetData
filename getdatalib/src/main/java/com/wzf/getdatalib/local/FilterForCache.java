package com.wzf.getdatalib.local;

/**
 * Created by wzf on 2017/6/2.
 * 设置适配器
 */

public interface FilterForCache {
    void setCache(String key, String cache);

    String getCache(String key);
}
