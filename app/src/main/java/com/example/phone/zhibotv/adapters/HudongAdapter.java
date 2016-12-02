package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.model.SaiShiContentModelAnchors;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016-11-30.
 */
public class HudongAdapter extends TeachBaseAdapter<SaiShiContentModelAnchors> {
    private Context context;
    public HudongAdapter(Context context, List<SaiShiContentModelAnchors> data, int layoutResId) {
        super(context, data, layoutResId);
        this.context=context;
    }

    @Override
    protected void bindData(ViewHolder holder, SaiShiContentModelAnchors item, int position) {
        ImageView touxiang = (ImageView) holder.getView(R.id.hudong_item_touxiang);
        holder.setText(R.id.hudong_item_name,item.getUname());
        ImageView biaozhi = (ImageView) holder.getView(R.id.hudong_item_biaozhiimg);
        Picasso.with(context).load(UrlUtils.BASE_URL+item.getUhimg()).transform(new CropCircleTransformation()).into(touxiang);
        Picasso.with(context).load(UrlUtils.BASE_URL+item.getSourceImg()).into(biaozhi);
    }
}
