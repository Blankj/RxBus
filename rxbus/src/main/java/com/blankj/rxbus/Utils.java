package com.blankj.rxbus;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/24
 *     desc  :
 * </pre>
 */
final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断对象是否相等
     *
     * @param o1 对象1
     * @param o2 对象2
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(Object o1, Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    /**
     * 检查对象非空
     *
     * @param object  对象
     * @param message 报错
     * @param <T>     范型
     * @return 非空对象
     */
    public static <T> T requireNonNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public static <T> Class<T> getClassFromCallback(final RxBus.Callback<T> callback) {
        if (callback == null) return null;
        Type mySuperClass = callback.getClass().getGenericInterfaces()[0];
        Type type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
        while (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        }
        String className = type.toString();
        if (className.startsWith("class ")) {
            className = className.substring(6);
        }
        try {
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void logW(String msg) {
        Log.w("RxBus", msg);
    }

    public static void logE(String msg) {
        Log.e("RxBus", msg);
    }
}
