package com.blankj.rxbus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/08/05
 *     desc  :
 * </pre>
 */
public class EventBusTest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        EventBus.getDefault().unregister(this);
    }

    @Test
    public void test() {
        EventBus.getDefault().postSticky("haha");
        EventBus.getDefault().postSticky(10086);

        EventBus.getDefault().register(this);

        EventBus.getDefault().post("haha");
        EventBus.getDefault().post(10086);

        EventBus.getDefault().unregister(this);
        System.out.println("-----");
        EventBus.getDefault().register(this);
        EventBus.getDefault().unregister(this);
        System.out.println("-----");
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1, sticky = true)
    public void onMessageEvent1(String event) {
        System.out.println(event + "1");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 5, sticky = true)
    public void onMessageEvent5(String event) {
        System.out.println(event + "5");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 2, sticky = true)
    public void onMessageEvent2(String event) {
        System.out.println(event + "2");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 3, sticky = true)
    public void onIntEvent(Integer event) {
        System.out.println(event);
    }
}
