package com.suncreate.shinyportal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.entity.WorkOrderUser;
import com.zds.base.util.StringUtil;

import java.util.List;

public class SimilarWorkOrderAdapter extends BaseQuickAdapter<WorkOrderUser, BaseViewHolder> {
    public SimilarWorkOrderAdapter(@Nullable List<WorkOrderUser> data) {
        super(R.layout.adapter_similar_work_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkOrderUser item) {
        helper.setText(R.id.tv_name, item.getAlarmName())
                .setText(R.id.tv_asset_name, item.getMap().getAssetName())
                .setText(R.id.tv_time1, "告警时间："+ (StringUtil.isEmpty(item.getAlarmTime()) ? "" : StringUtil.dealDateFormat(item.getAlarmTime())))
                .setText(R.id.tv_time2,"派单时间："+ (StringUtil.isEmpty(item.getAddTime()) ? "" : StringUtil.dealDateFormat(item.getAddTime())))
                .setText(R.id.tv_time3, "截止时间："+ (StringUtil.isEmpty(item.getDeadlineTime()) ? "" : StringUtil.dealDateFormat(item.getDeadlineTime())));

    }
}
