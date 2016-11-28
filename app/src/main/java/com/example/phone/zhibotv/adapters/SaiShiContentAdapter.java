package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.model.SaiShiContenModel;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.squareup.picasso.Picasso;


import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiContentAdapter extends BaseMultTypeAdapter<SaiShiContenModel> {
    private Context context;
    public SaiShiContentAdapter(Context context, List<SaiShiContenModel> data, int... layoutIds) {
        super(context, data, layoutIds);
        this.context=context;
    }

    @Override
    public void bindData(ViewHolder holder, SaiShiContenModel item, int position) {
        switch (item.getType()) {
            case 0:
                ImageView leftImage = (ImageView) holder.getView(R.id.saishi_item_two_leftIcon);
                TextView leftName = (TextView) holder.getView(R.id.saishi_item_two_leftName);
                ImageView rightImage = (ImageView) holder.getView(R.id.saishi_item_two_rightIcon);
                TextView rightName = (TextView) holder.getView(R.id.saishi_item_two_rightName);
                TextView timeView = (TextView) holder.getView(R.id.saishi_item_two_time);
                TextView titleView = (TextView) holder.getView(R.id.saishi_item_two_title);
                LinearLayout liveList = (LinearLayout) holder.getView(R.id.saishi_item_two_sourceList);
                LinearLayout anchor = (LinearLayout) holder.getView(R.id.saishi_item_two_anchors);
                Picasso.with(context).load(UrlUtils.BASE_URL+item.getLeftIcon()).into(leftImage);
                Picasso.with(context).load(UrlUtils.BASE_URL+item.getRightIcon()).into(rightImage);
                leftName.setText(item.getLeftName());
                rightName.setText(item.getRightName());
                timeView.setText(DateFormat.format("HH:mm",item.getTime()));
                titleView.setText(item.getCategory());
                for (int i = 0; i < item.getSourceList().size(); i++) {
                    TextView view = new TextView(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.leftMargin=12;
                    view.setLayoutParams(layoutParams);
                    view.setText(item.getSourceList().get(i).getUname());
                    liveList.addView(view);
                }
                for (int i = 0; i < item.getAnchors().size(); i++) {
                    ImageView imageView1 = new ImageView(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.leftMargin=10;
                    imageView1.setLayoutParams(layoutParams);
                    CropCircleTransformation transformation = new CropCircleTransformation();
                    Picasso.with(context).load(UrlUtils.BASE_URL+item.getAnchors().get(i).getUhimg()).transform(transformation).into(imageView1);
                    anchor.addView(imageView1);
                }
                break;
            case 1:
                ImageView imageView = (ImageView) holder.getView(R.id.saishi_item_one_image);
                TextView title = (TextView) holder.getView(R.id.saishi_item_one_title);
                LinearLayout sourceList = (LinearLayout) holder.getView(R.id.saishi_item_one_sourceList);
                LinearLayout anchors = (LinearLayout) holder.getView(R.id.saishi_item_one_anchors);
                Picasso.with(context).load(UrlUtils.BASE_URL+item.getScheduleIcon()).into(imageView);
                title.setText(item.getCategory());
                for (int i = 0; i < item.getSourceList().size(); i++) {
                    TextView view = new TextView(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.leftMargin=12;
                    view.setLayoutParams(layoutParams);
                    view.setText(item.getSourceList().get(i).getUname());
                    sourceList.addView(view);
                }
                for (int i = 0; i < item.getAnchors().size(); i++) {
                    ImageView imageView1 = new ImageView(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.leftMargin=10;
                    imageView1.setLayoutParams(layoutParams);
                    CropCircleTransformation transformation = new CropCircleTransformation();
                    Picasso.with(context).load(UrlUtils.BASE_URL+item.getAnchors().get(i).getUhimg()).transform(transformation).into(imageView1);
                    anchors.addView(imageView1);
                }
                break;
        }
    }
}
