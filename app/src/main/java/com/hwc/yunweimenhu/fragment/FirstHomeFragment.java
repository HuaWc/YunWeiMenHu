package com.hwc.yunweimenhu.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hwc.yunweimenhu.R;
import com.hwc.yunweimenhu.base.BaseFragment;
import com.hwc.yunweimenhu.view.dialog.BaseDialog;
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
    @BindView(R.id.ll_dwkc)
    LinearLayout llDwkc;
    @BindView(R.id.ll_gjgl)
    LinearLayout llGjgl;
    @BindView(R.id.ll_dzdt)
    LinearLayout llDzdt;
    @BindView(R.id.ll_rwgl)
    LinearLayout llRwgl;
    @BindView(R.id.ll_gdgl)
    LinearLayout llGdgl;
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

}
