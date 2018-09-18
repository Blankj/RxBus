package com.blankj.rxbus;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/09/17
 *     desc  :
 * </pre>
 */
public interface Call<T> {
    void call(T t);
}
