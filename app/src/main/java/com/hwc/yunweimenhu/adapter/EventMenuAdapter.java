package com.hwc.yunweimenhu.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwc.yunweimenhu.R;
import com.hwc.yunweimenhu.entity.EventMenuInfo;

import java.util.List;

public class EventMenuAdapter extends BaseQuickAdapter<EventMenuInfo, BaseViewHolder> {
    public EventMenuAdapter(@Nullable List<EventMenuInfo> data) {
        super(R.layout.adapter_main_event, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventMenuInfo item) {
        TextView tv_look = helper.getView(R.id.tv_look);
        TextView tv_evaluate = helper.getView(R.id.tv_evaluate);
        helper.setText(R.id.tv_name, item.getAlarmName())
                .setText(R.id.tv_status, item.getAlarmStatus())
                .setText(R.id.tv_time, item.getMap().getAlarmTime())
                .setText(R.id.tv_g, item.getMap().getOrgName())
                .setText(R.id.tv_ip, item.getIp())
                .setText(R.id.tv_jg, item.getAssetNature() + ">" + item.getAssetType() + ">" + item.getMap().getAssetClass());
        helper.addOnClickListener(R.id.tv_look).addOnClickListener(R.id.tv_evaluate);
        if ("闭环".equals(item.getAlarmStatus())) {
            tv_evaluate.setVisibility(View.VISIBLE);
        } else {
            tv_evaluate.setVisibility(View.GONE);
        }
    }
}
