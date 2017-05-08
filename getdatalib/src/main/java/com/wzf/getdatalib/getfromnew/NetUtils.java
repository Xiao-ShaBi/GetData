package com.wzf.getdatalib.getfromnew;

import com.alibaba.fastjson.JSONObject;
import com.wzf.getdatalib.L.L;
import com.wzf.getdatalib.data.StoreData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by wzf on 2017/5/8.
 * 联网并且存储数据
 * todo 这里使用的是okhttputils框架，有时间还是自己封装下
 */

public class NetUtils {

    /**
     * get请求并存储
     *
     * @param url       请求基础地质
     * @param map       请求的参数 即 http://xx.xx.xx？xx=xx 问好后边的，等号左边为key右边为value
     * @param clazz     请求的数据的bean的class
     * @param jiexijson 数据的解析过程，在这里面调用{@link StoreData#setDatas(Class, Object)}来设置解析好的数据
     */
    public static void get(String url, Map<String, String> map, final Class clazz, final JiexiJson jiexijson) {
        OkHttpUtils
                .get()
                .url(url)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.e("get请求 -- " + clazz.getSimpleName() + " -- 错误，错误原因：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        jiexijson.jiexi(response, clazz);
                    }
                });
    }

    /**
     * post请求使用
     *
     * @param url
     * @param map
     * @param clazz
     * @param jiexijson
     */
    public static void post(String url, Map<String, String> map, final Class clazz, final JiexiJson jiexijson) {
        OkHttpUtils
                .post()
                .url(url)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.e("post请求 -- " + clazz.getSimpleName() + " -- 错误，错误原因：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        jiexijson.jiexi(response, clazz);
                    }
                });
    }


    /**
     * 发送json请求时调用
     *
     * @param url
     * @param map
     * @param clazz
     * @param jiexijson
     * @param obj       请求对象
     */
    public static void postJson(String url, Map<String, String> map, final Class clazz, final JiexiJson jiexijson, Object obj) {
        OkHttpUtils
                .postString()
                .url(url)
                /**
                 * 这里使用fastjson将对象转化为json字符串
                 */
                .content(JSONObject.toJSONString(obj))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.e("postjson请求 -- " + clazz.getSimpleName() + " -- 错误，错误原因：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        jiexijson.jiexi(response, clazz);
                    }
                });
    }

    //    将文件作为请求体，发送到服务器。
    public static void postFile(String url, Map<String, String> map, final Class clazz, final JiexiJson jiexijson, File file) {
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.e("postfile请求 -- " + clazz.getSimpleName() + " -- 错误，错误原因：" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        jiexijson.jiexi(response, clazz);
                    }
                });
    }

    /**
     * 解析json数据调用的接口
     */
    public interface JiexiJson {
        public void jiexi(String response, Class clazz);
    }

    /**
     * 简单的解析json
     * 没有做任何的适配
     * 最好还是自己做解析
     */
    public static class SimpleJson implements JiexiJson {

        @Override
        public void jiexi(String response, Class clazz) {
            /**
             * 这里解析json数据使用的是fastjson，需要的可以自己更换
             *
             * 这里需要做详细的配置，请求的数据有没有成功，需要自己做封装
             */
            try {
                Object o = JsonUtls.fromJson(response, clazz);
                StoreData.getStore().setDatas(clazz, o);
            } catch (Exception e) {
                L.e("请求到的数据有差");
                e.printStackTrace();
            }
        }
    }

}
