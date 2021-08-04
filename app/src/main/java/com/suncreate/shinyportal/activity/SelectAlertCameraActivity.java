package com.suncreate.shinyportal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.adapter.AdapterAddCameraChild;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.entity.AddCameraChild;
import com.suncreate.shinyportal.http.ApiClient;
import com.suncreate.shinyportal.http.AppConfig;
import com.suncreate.shinyportal.http.ResultListener;
import com.suncreate.shinyportal.util.RecyclerViewHelper;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectAlertCameraActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.bar)
    View bar;

    private AdapterAddCameraChild adapterAddCameraChild;
    private List<AddCameraChild> addCameraChildList;
    private String positionName;
    private String orgName;
    private String describe;
    private String positionCode;
    private String longitude;
    private String latitude;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_select_alert_camera);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
    }

    private void initAdapter() {
        addCameraChildList = new ArrayList<>();
        adapterAddCameraChild = new AdapterAddCameraChild(addCameraChildList);
        rv.setAdapter(adapterAddCameraChild);
        rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewHelper.recyclerviewAndScrollView(rv);
        adapterAddCameraChild.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("cameraId", addCameraChildList.get(position).getId());
                bundle.putInt("from", 1);

                //bundle.putString("positionName", positionName);
                toTheActivity(AddEventActivity.class, bundle);
            }
        });
        getData();
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {
        if (StringUtil.isEmpty(positionCode)) {
            Toast.makeText(this, "杆件位置码不存在，请重试", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("positionCode", positionCode);
        ApiClient.requestNetPost(this, AppConfig.filePositionDetails, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                positionName = FastJsonUtil.getString(json, "positionName");
                orgName = FastJsonUtil.getString(json, "orgName");
                describe = FastJsonUtil.getString(json, "describe");
                longitude = FastJsonUtil.getString(json, "longitude");
                latitude = FastJsonUtil.getString(json, "latitude");
                List<AddCameraChild> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "ptCameraInfoList"), AddCameraChild.class);
                addCameraChildList.clear();
                if (list != null && list.size() != 0) {
                    addCameraChildList.addAll(list);
                }
                SelectAlertCameraActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterAddCameraChild.notifyDataSetChanged();
                        tv1.setText(positionName);
                        tv2.setText(orgName);
                        tv3.setText(describe);
                    }
                });

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(SelectAlertCameraActivity.this, "杆件位置码不存在，请重试", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        positionCode = extras.getString("positionCode");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
