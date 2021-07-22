package com.suncreate.shinyportal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.entity.WorkPeople;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * Created by Christ on 2021/7/19.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class WorkPeopleAdapter extends BaseQuickAdapter<WorkPeople, BaseViewHolder> {
    public WorkPeopleAdapter(@Nullable List<WorkPeople> data) {
        super(R.layout.adapter_work_people, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkPeople item) {
            helper.setText(R.id.tv_name, StringUtil.isEmpty(item.getRealName())?"":item.getRealName())
                    .setText(R.id.tv_mobile,StringUtil.isEmpty(item.getMobileNo())?"无":item.getMobileNo());
    }
}
