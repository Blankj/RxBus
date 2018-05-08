package com.blankj.rxbus.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.rxbus.RxBus;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/25
 *     desc  :
 * </pre>
 */
public class StickyTestActivity extends AppCompatActivity {

    private TextView tvSticky;

    public static void start(Context context) {
        Intent starter = new Intent(context, StickyTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_test);

        tvSticky = findViewById(R.id.tv_sticky);

        RxBus.getDefault().subscribeSticky(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                tvSticky.setText(Config.appendMsg("sticky without " + s));
            }
        });

        RxBus.getDefault().subscribeSticky(this, "my tag", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                tvSticky.setText(Config.appendMsg("sticky with " + s));
            }
        });
    }

    public void postWithoutTag(View view) {
        Config.restoreMsg();
        RxBus.getDefault().post("tag");
    }

    public void postWithTag(View view) {
        Config.restoreMsg();
        RxBus.getDefault().post("tag", "my tag");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }
}
