package com.hwc.yunweimenhu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hwc.yunweimenhu.R;
import com.hwc.yunweimenhu.activity.AskHelpActivity;
import com.hwc.yunweimenhu.base.BaseFragment;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FirstHomeFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.ll_sjsb)
    LinearLayout llSjsb;
    @BindView(R.id.ll_sjgz)
    LinearLayout llSjgz;
    @BindView(R.id.ll_dzdt)
    LinearLayout llDzdt;
    @BindView(R.id.ll_zxbz)
    LinearLayout llZxbz;
    @BindView(R.id.ll_gdcl)
    LinearLayout llGdcl;
    @BindView(R.id.ll_jqqd)
    LinearLayout llJqqd;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.all)
    LinearLayout all;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        initBar();

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


    @OnClick({R.id.ll_sjsb, R.id.ll_sjgz, R.id.ll_dzdt, R.id.ll_zxbz, R.id.ll_gdcl, R.id.ll_jqqd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_sjsb:
                //事件上报
                break;
            case R.id.ll_sjgz:
                //事件跟踪
                break;
            case R.id.ll_dzdt:
                //电子地图
                break;
            case R.id.ll_zxbz:
                //咨询帮助
                toTheActivity(AskHelpActivity.class);
                break;
            case R.id.ll_gdcl:
                //工单处理
                break;
            case R.id.ll_jqqd:
                ToastUtil.toast("敬请期待");
                break;
        }
    }
}
