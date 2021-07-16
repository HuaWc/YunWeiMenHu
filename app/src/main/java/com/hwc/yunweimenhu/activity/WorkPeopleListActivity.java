package com.hwc.yunweimenhu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.hwc.yunweimenhu.R;
import com.hwc.yunweimenhu.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/7/16.
 * By an amateur android developer
 * Email 627447123@qq.com
 * <p>
 * 运维人员手册
 */
public class WorkPeopleListActivity extends BaseActivity {
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

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_people_list);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
