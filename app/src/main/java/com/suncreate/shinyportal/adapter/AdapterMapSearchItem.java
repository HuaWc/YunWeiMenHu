package com.suncreate.shinyportal.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.entity.MapSearchItem;

import java.util.List;

public class AdapterMapSearchItem extends BaseQuickAdapter<MapSearchItem, BaseViewHolder> {
    public AdapterMapSearchItem(@Nullable List<MapSearchItem> data) {
        super(R.layout.adapter_map_search_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MapSearchItem item) {
        helper.setText(R.id.tv_name, item.getCameraName());
    }
}
