package com.suncreate.shinyportal.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.entity.EventMenuInfo;

import java.util.List;

public class EventMenuAdapter extends BaseQuickAdapter<EventMenuInfo, BaseViewHolder> {
    private int type;//两种 一种可以有 评价按钮   另一个没有

    public EventMenuAdapter(@Nullable List<EventMenuInfo> data,int type) {
        super(R.layout.adapter_main_event, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventMenuInfo item) {
        TextView tv_look = helper.getView(R.id.tv_look);
        ImageView iv_sc = helper.getView(R.id.iv_sc);
        TextView tv_evaluate = helper.getView(R.id.tv_evaluate);
        helper.setText(R.id.tv_name, item.getAlarmName())
                .setText(R.id.tv_status, item.getAlarmStatus())
                .setText(R.id.tv_time, item.getMap().getAlarmTime())
                .setText(R.id.tv_g, item.getMap().getOrgName())
                .setText(R.id.tv_ip, item.getIp())
                .setText(R.id.tv_jg, item.getAssetNature() + ">" + item.getAssetType() + ">" + item.getMap().getAssetClass());
        helper.addOnClickListener(R.id.tv_look).addOnClickListener(R.id.tv_evaluate);
        if(type == 1){
            if ("闭环".equals(item.getAlarmStatus())) {
                tv_evaluate.setVisibility(View.VISIBLE);
            } else {
                tv_evaluate.setVisibility(View.GONE);
            }
        } else {
            tv_evaluate.setVisibility(View.GONE);
        }

        if (item.getMap().getIsCollect() != null && item.getMap().getIsCollect() == 1) {
            //收藏
            iv_sc.setSelected(true);
        } else {
            //未收藏
            iv_sc.setSelected(false);
        }
        helper.addOnClickListener(R.id.iv_sc);
    }
}
