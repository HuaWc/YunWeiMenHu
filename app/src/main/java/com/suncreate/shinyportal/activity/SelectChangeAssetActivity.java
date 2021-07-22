package com.suncreate.shinyportal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.adapter.AssetEquipmentAdapter;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.entity.AssetEquipment;
import com.suncreate.shinyportal.http.ApiClient;
import com.suncreate.shinyportal.http.AppConfig;
import com.suncreate.shinyportal.http.ResultListener;
import com.suncreate.shinyportal.util.EventUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/6/29.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class SelectChangeAssetActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private String faultId;
    private List<AssetEquipment> mList;
    private AssetEquipmentAdapter adapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_select_change_asset);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();

    }

    private void initClick() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        adapter = new AssetEquipmentAdapter(mList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new EventCenter(EventUtil.SELECT_CHANGE_ASSET, mList.get(position)));
                finish();
            }
        });
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("faultId", faultId);
        ApiClient.requestNetPost(this, AppConfig.getAssetList, "查询中", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<AssetEquipment> list = FastJsonUtil.getList(json, AssetEquipment.class);
                if (list != null && list.size() != 0) {
                    mList.addAll(list);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        faultId = extras.getString("faultId");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
