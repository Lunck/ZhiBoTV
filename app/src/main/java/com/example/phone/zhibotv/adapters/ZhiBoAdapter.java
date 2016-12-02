package com.example.phone.zhibotv.adapters;

import android.content.Context;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.model.SaiShiContentModelSourceList;

import java.util.List;

/**
 * Created by Administrator on 2016-11-30.
 */
public class ZhiBoAdapter extends TeachBaseAdapter<SaiShiContentModelSourceList> {
    public ZhiBoAdapter(Context context, List<SaiShiContentModelSourceList> data, int layoutResId) {
        super(context, data, layoutResId);
    }

    @Override
    protected void bindData(ViewHolder holder, SaiShiContentModelSourceList item, int position) {
        holder.setText(R.id.zhibo_item_name,item.getUname());
    }
}
