package com.suncreate.shinyportal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.adapter.RepeatAlertAdapter;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.entity.AlertMenuInfo;
import com.suncreate.shinyportal.http.ApiClient;
import com.suncreate.shinyportal.http.AppConfig;
import com.suncreate.shinyportal.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/11/16.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class RepeatAlertListActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private String deviceCode;
    private String alarmName;
    private List<AlertMenuInfo> mList;
    private RepeatAlertAdapter adapter;
    private int page = 1;
    private int pageSize = 10;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_repeat_alert_list);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();

    }


    private void initClick(){
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(false);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        adapter = new RepeatAlertAdapter(mList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getData(false);

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    private void getData(boolean more) {
        if (more) {
            page++;
        } else {
            page = 1;
            mList.clear();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("keyWords", alarmName);
        params.put("deviceCode", deviceCode);
        params.put("flag", true);
        ApiClient.requestNetGet(this, AppConfig.OpAlarmInfoList, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                 List<AlertMenuInfo> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), AlertMenuInfo.class);
                if (list != null && list.size() != 0) {
                    mList.addAll(list);
                } else {
                    if (page > 1) {
                        page--;
                    }
                }
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(String msg) {
                if (page > 1) {
                    page--;
                }
                ToastUtil.toast(msg);
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();

            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        deviceCode = extras.getString("deviceCode");
        alarmName = extras.getString("alarmName");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
