package com.blankj.rxbus.demo;

import com.blankj.rxbus.RxBus;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/05/09
 *     desc  :
 * </pre>
 */
public class RxBusManager {

    private static final String MY_TAG = "MY_TAG";

    public static void subscribeRxBusManagerActivity(final RxBusManagerActivity activity){
        RxBus.getDefault().subscribe(activity, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                activity.updateText("without " + s);
            }
        });

        RxBus.getDefault().subscribe(activity, MY_TAG, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                activity.updateText("with " + s);
            }
        });
    }

    public static void postToRxBusManagerActivity(final String event) {
        RxBus.getDefault().post(event);
    }

    public static void postWithMyTagToRxBusManagerActivity(final String event) {
        RxBus.getDefault().post(event, MY_TAG);
    }

    public static void postStickyToRxBusManagerActivity(final String event) {
        RxBus.getDefault().postSticky(event);
    }

    public static void postStickyWithMyTagToRxBusManagerActivity(final String event) {
        RxBus.getDefault().postSticky(event, MY_TAG);
    }

    public static void unregisterRxBusManagerActivity(final RxBusManagerActivity activity) {
        RxBus.getDefault().unregister(activity);
    }
}
