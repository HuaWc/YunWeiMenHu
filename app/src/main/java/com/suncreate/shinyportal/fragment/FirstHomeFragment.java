package com.suncreate.shinyportal.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.activity.AskHelpActivity;
import com.suncreate.shinyportal.activity.EventListActivity;
import com.suncreate.shinyportal.activity.EventManagementActivity;
import com.suncreate.shinyportal.activity.MapActivity;
import com.suncreate.shinyportal.activity.WorkOrderManagementUserActivity;
import com.suncreate.shinyportal.base.BaseFragment;
import com.suncreate.shinyportal.base.MyApplication;
import com.suncreate.shinyportal.entity.UserInfo;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.StringUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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
    @BindView(R.id.cd_gdcl)
    CardView cdGdcl;


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
        initUserInfo();
    }

    private void initUserInfo() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        if (StringUtil.isEmpty(userInfo.getToken())) {
            MyApplication.getInstance().toLogin(getContext());
        } else {
            if (userInfo.getUserType() != null && userInfo.getUserType() == 99) {
                //超级管理员
                cdGdcl.setVisibility(View.VISIBLE);
            } else {
                //不是超级管理员，看看是不是it管理员
                if (!StringUtil.isEmpty(userInfo.getMap().getUserTypes()) && Arrays.asList(userInfo.getMap().getUserTypes().split(",")).contains("5")) {
                    cdGdcl.setVisibility(View.VISIBLE);
                } else {
                    cdGdcl.setVisibility(View.GONE);
                }
            }
        }
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
                toTheActivity(EventManagementActivity.class);
                break;
            case R.id.ll_sjgz:
                //事件跟踪
                toTheActivity(EventListActivity.class);
                break;
            case R.id.ll_dzdt:
                //电子地图
                //checkMapFile();
                toTheActivity(MapActivity.class);
                break;
            case R.id.ll_zxbz:
                //咨询帮助
                toTheActivity(AskHelpActivity.class);
                break;
            case R.id.ll_gdcl:
                //工单处理
                toTheActivity(WorkOrderManagementUserActivity.class);
                break;
            case R.id.ll_jqqd:
                ToastUtil.toast("敬请期待");
                break;
        }
    }

    private void checkMapFile() {
        //检验凭证
        String rootPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = rootPath + "/Mobile GIS/License/SuperMap iMobile Trial Laptop-Owner-Christ-20210727.slm";
        File file = new File(filePath);
        if (file.exists()) {
            //存在
            toTheActivity(MapActivity.class);
        } else {
            //不存在
            ToastUtil.toast("地图凭证未准备好，请重启应用后重试");
        }
    }
}
