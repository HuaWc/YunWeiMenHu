package com.suncreate.shinyportal.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fri.libfriapkrecord.read.SignRecordTools;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.base.MyApplication;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/11/4.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class AppInfoActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.tv3)
    TextView tv3;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_app_info);
    }

    @Override
    protected void initLogic() {
        initBar();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        tv1.setText("六安移动运维");
        tv2.setText("v" + MyApplication.getInstance().getVersionName());
        try {
            final String apkPath = getNativeApkPath(AppInfoActivity.this.getApplicationContext());
            //读取备案号
            String recordNum = SignRecordTools.readNumbers(apkPath);
            tv3.setText("全国注册备案号：" + recordNum);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    //获取系统内APK文件路径
    public static String getNativeApkPath(@NonNull final Context context) {
        String apkPath = null;
        try {
            final ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo == null) {
                return null;
            }
            apkPath = applicationInfo.sourceDir;
        } catch (Throwable e) {
        }
        return apkPath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
