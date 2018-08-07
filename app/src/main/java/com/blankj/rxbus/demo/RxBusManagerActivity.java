package com.blankj.rxbus.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.rxbus.RxBus;


public class RxBusManagerActivity extends AppCompatActivity {

    private TextView tvSticky;

    public static void start(Context context) {
        Intent starter = new Intent(context, RxBusManagerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        tvSticky = findViewById(R.id.tv_about_manager);

        RxBusManager.subscribeRxBusManagerActivity(this);
    }

    public void updateText(final String s) {
        tvSticky.setText(Config.appendMsg(s));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBusManager.unregisterRxBusManagerActivity(this);
    }

    public void postWithoutTag(View view) {
        Config.restoreMsg();
        RxBusManager.postToRxBusManagerActivity("tag");
    }

    public void postWithTag(View view) {
        Config.restoreMsg();
        RxBusManager.postWithMyTagToRxBusManagerActivity("tag");
    }

    public void postStickyWithoutTag(View view) {
        Config.restoreMsg();
        RxBusManager.postStickyToRxBusManagerActivity("tag");
        StickyTestActivity.start(this);
    }

    public void postStickyWithTag(View view) {
        Config.restoreMsg();
        RxBusManager.postStickyWithMyTagToRxBusManagerActivity("tag");
        StickyTestActivity.start(this);
    }
}
