package com.blankj.rxbus.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.rxbus.RxBus;


public class RxBusActivity extends AppCompatActivity {

    private TextView tvSticky;

    public static void start(Context context) {
        Intent starter = new Intent(context, RxBusActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSticky = findViewById(R.id.tv_sticky);

        RxBus.getDefault().subscribe(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                tvSticky.setText(Config.appendMsg("without " + s));
                throw new NullPointerException("");
            }
        });

        RxBus.getDefault().subscribe(this, "my tag", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                tvSticky.setText(Config.appendMsg("with " + s));
            }
        });

        RxBus.getDefault().subscribe(this, new RxBus.Callback<TestCallback>() {
            @Override
            public void onEvent(TestCallback testCallback) {
                tvSticky.setText(Config.appendMsg(testCallback.call()));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }

    public void postWithoutTag(View view) {
        Config.restoreMsg();
        RxBus.getDefault().post("tag");
    }

    public void postWithTag(View view) {
        Config.restoreMsg();
        RxBus.getDefault().post("tag", "my tag");
    }

    public void postStickyWithoutTag(View view) {
        Config.restoreMsg();
        RxBus.getDefault().postSticky("tag");
        StickyTestActivity.start(this);
    }

    public void postStickyWithTag(View view) {
        Config.restoreMsg();
        RxBus.getDefault().postSticky("tag", "my tag");
        StickyTestActivity.start(this);
    }

    public void useManager(View view) {
        RxBusManagerActivity.start(this);
        finish();
    }

    public void testInterface(View view) {
        Config.restoreMsg();
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            RxBus.getDefault().post(new TestCallback() {
                @Override
                public String call() {
                    return String.valueOf(finalI);
                }
            });
        }
    }
}
