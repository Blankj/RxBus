package com.blankj.rxbus;

import org.junit.Assert;
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
//        Class<String> className0 = Utils.getTypeClassFromParadigm(new RxBus.Callback<String>() {
//            @Override
//            public void onEvent(String strings) {
//
//            }
//        });
//        System.out.println(className0);
//
//        Class<ArrayList<String>> className1 = Utils.getTypeClassFromParadigm(new RxBus.Callback<ArrayList<String>>() {
//            @Override
//            public void onEvent(ArrayList<String> strings) {
//
//            }
//        });
//        System.out.println(className1);


        Class<RxBus.Callback> interfaceName0 = Utils.getTypeClassFromParadigm(new RxBus.Callback<RxBus.Callback>() {
            @Override
            public void onEvent(RxBus.Callback strings) {

            }
        });
        System.out.println(interfaceName0);
//
//        Class<RxBus.Callback<String>> interfaceName1 = Utils.getTypeClassFromParadigm(new RxBus.Callback<RxBus.Callback<String>>() {
//            @Override
//            public void onEvent(RxBus.Callback<String> strings) {
//
//            }
//        });
//        System.out.println(interfaceName1);
    }

    @Test
    public void getTypeNameFromObject() {
        Assert.assertEquals(String.class, Utils.getClassFromObject("test"));
        Assert.assertEquals(ArrayList.class, Utils.getClassFromObject(new ArrayList<String>()));

        RxBus.Callback<Integer> callback = new RxBus.Callback<Integer>() {
            @Override
            public void onEvent(Integer o) {

            }
        };

        Assert.assertEquals(RxBus.Callback.class, Utils.getClassFromObject(callback));
        Assert.assertEquals(Integer.class, Utils.getTypeClassFromParadigm(callback));


        Call<Integer> call = new Call<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        };

        Assert.assertEquals(Call.class, Utils.getClassFromObject(call));

        Assert.assertEquals(Integer.class, Utils.getClassFromObject(1));
    }
}