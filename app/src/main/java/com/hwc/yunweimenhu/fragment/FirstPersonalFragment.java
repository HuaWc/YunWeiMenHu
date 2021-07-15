package com.hwc.yunweimenhu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hwc.yunweimenhu.R;
import com.hwc.yunweimenhu.activity.LoginActivity;
import com.hwc.yunweimenhu.base.ActivityStackManager;
import com.hwc.yunweimenhu.base.BaseFragment;
import com.hwc.yunweimenhu.base.MyApplication;
import com.hwc.yunweimenhu.http.ApiClient;
import com.hwc.yunweimenhu.http.AppConfig;
import com.hwc.yunweimenhu.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的
 *
 * @author Administrator
 */
public class FirstPersonalFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_ip)
    TextView tvIp;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_logout)
    TextView tvLogout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));

        initClick();




    }

    private void initClick() {
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        MyApplication.getInstance().cleanUserInfo();
        ToastUtil.toast("退出成功！");
        ActivityStackManager.getInstance().killAllActivity();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
