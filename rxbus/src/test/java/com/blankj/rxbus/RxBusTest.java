package com.blankj.rxbus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/08/05
 *     desc  :
 * </pre>
 */
public class RxBusTest {

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
//        FlowableProcessor<Object> processor = PublishProcessor.create().toSerialized();
//
//        processor.ofType(String.class)
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        System.out.println(s + "2");
//                    }
//                });
//        processor.ofType(String.class)
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        System.out.println(s + "1");
//                    }
//                });
//
//
//        processor.onNext("2");


        RxBus.getDefault().postSticky("haha");
        RxBus.getDefault().postSticky("haha");

        RxBus.getDefault().subscribeSticky(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                System.out.println(s);
            }
        });

        RxBus.getDefault().postSticky("haha");
        RxBus.getDefault().post("haha");
    }

    @After
    public void tearDown() {
        RxBus.getDefault().unregister(this);
    }
}
