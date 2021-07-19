package com.hwc.yunweimenhu.activity;

import android.os.Bundle;
import android.os.Handler;

import android.widget.ImageView;


import com.hwc.yunweimenhu.R;
import com.hwc.yunweimenhu.base.BaseActivity;
import com.hwc.yunweimenhu.base.MyApplication;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.img_welcome)
    ImageView imgWelcome;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initLogic() {
        //imgWelcome.setImageResource(R.mipmap.img_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //do something
                if (MyApplication.getInstance().checkUser()) {
                    if (MyApplication.getInstance().getUserInfo().isRemember()) {
                        toTheActivity(MainActivity.class);
                    } else {
                        MyApplication.getInstance().cleanUserInfo();
                        toTheActivity(LoginActivity.class);
                    }
                } else {
                    MyApplication.getInstance().cleanUserInfo();
                    toTheActivity(LoginActivity.class);
                }

                finish();

            }
        }, 3000);
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
