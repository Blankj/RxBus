package com.blankj.rxbus;

import org.junit.Test;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/06/04
 *     desc  :
 * </pre>
 */
public class UtilsTest {

    @Test
    public void getClassFromCallback() {
        Class<String> className0 = Utils.getTypeClassFromCallback(new RxBus.Callback<String>() {
            @Override
            public void onEvent(String strings) {

            }
        });
        System.out.println(className0);

        Class<ArrayList<String>> className1 = Utils.getTypeClassFromCallback(new RxBus.Callback<ArrayList<String>>() {
            @Override
            public void onEvent(ArrayList<String> strings) {

            }
        });
        System.out.println(className1);


        Class<RxBus.Callback> interfaceName0 = Utils.getTypeClassFromCallback(new RxBus.Callback<RxBus.Callback>() {
            @Override
            public void onEvent(RxBus.Callback strings) {

            }
        });
        System.out.println(interfaceName0);

        Class<RxBus.Callback<String>> interfaceName1 = Utils.getTypeClassFromCallback(new RxBus.Callback<RxBus.Callback<String>>() {
            @Override
            public void onEvent(RxBus.Callback<String> strings) {

            }
        });
        System.out.println(interfaceName1);
    }

//    class java.lang.String
//    class java.util.ArrayList
//    interface com.blankj.rxbus.RxBus$Callback
//    interface com.blankj.rxbus.RxBus$Callback


    @Test
    public void getTypeNameFromObject() {
        System.out.println(Utils.getClassFromObject("test"));

        System.out.println(Utils.getClassFromObject(new ArrayList<String>()));

        RxBus.Callback callback0 = new RxBus.Callback() {
            @Override
            public void onEvent(Object o) {

            }
        };

        System.out.println(callback0.getClass());
        System.out.println(Utils.getClassFromObject(callback0));

        RxBus.Callback<RxBus.Callback<String>> callback1 = new RxBus.Callback<RxBus.Callback<String>>() {
            @Override
            public void onEvent(RxBus.Callback<String> strings) {

            }
        };

        System.out.println(callback1.getClass());
        System.out.println(Utils.getClassFromObject(callback1));
    }
}