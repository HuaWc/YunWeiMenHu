package com.suncreate.shinyportal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.entity.AddCameraChild;

import java.util.List;

public class AdapterAddCameraChild extends BaseQuickAdapter<AddCameraChild, BaseViewHolder> {
    public AdapterAddCameraChild(@Nullable List<AddCameraChild> data) {
        super(R.layout.add_camera_child_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddCameraChild item) {
        helper.setText(R.id.tv_name, item.getCameraName()).setText(R.id.tv_g, item.getPoliceStation());

    }
}
