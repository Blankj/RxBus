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

    @Test
    public void getTypeNameFromObject() {
//        System.out.println(Utils.getClassFromObject("test"));
//
//        System.out.println(Utils.getClassFromObject(new ArrayList<String>()));
//
//        RxBus.Callback callback0 = new RxBus.Callback<Integer>() {
//            @Override
//            public void onEvent(Integer o) {
//
//            }
//        };
//
//        System.out.println(callback0.getClass());
//        System.out.println(Utils.getClassFromObject(callback0));
//
//
//        System.out.println(Utils.getTypeClassFromCallback(callback0));
        System.out.println(Utils.getClassFromObject(new A()));
        System.out.println(Utils.getClassFromObject(new B() {
        }));
        System.out.println(Utils.getClassFromObject(1));
    }

    class A {

    }

    interface B {

    }
}