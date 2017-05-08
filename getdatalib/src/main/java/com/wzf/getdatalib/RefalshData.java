package com.wzf.getdatalib;

/**
 * Created by wzf on 2017/5/8.
 * 用来修饰请求数据的对象，当数据得到后回调出去
 */

public interface RefalshData {

    /**
     * 记录当数据更新后是否刷新数据
     * 如果为true，继续保存当前对象
     * 否则，取到数据后直接从需要更新的队列中清除
     * <p>
     * 默认为true即请求的数据更新后，需要刷新
     */
    public boolean refalshOrNot();

    /**
     * 请求的数据有数据后回调出去,通知数据已经有了
     *
     * @param clazz 请求的数据的class对象
     */
    public void refalsh(Class clazz);
}
