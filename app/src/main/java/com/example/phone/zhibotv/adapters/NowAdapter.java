package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.ZhuBoActivity;
import com.example.phone.zhibotv.model.NowModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by my on 2016/11/29.
 */
public class NowAdapter extends BaseAdapter {
    private static final String TAG = NowAdapter.class.getSimpleName();
    private final LayoutInflater inflate;
    List<NowModel.DataBeanX.DataBean> data;
    private Context context;

    public NowAdapter(Context contexts, List<NowModel.DataBeanX.DataBean> data) {
        inflate = LayoutInflater.from(contexts);
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
        this.context = contexts;
    }

    public void updataRes(List<NowModel.DataBeanX.DataBean> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addRes(List<NowModel.DataBeanX.DataBean> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }

    }

    @Override
    public int getCount() {
        return (data != null ? data.size() : 0)/2;
    }

    @Override
    public NowModel.DataBeanX.DataBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.shouye_lv_childitem, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(holder.bigimg1.getContext())
                .load("http://www.zhibo.tv" + data.get(2 * position).getImgUrl())
                .placeholder(R.drawable.common_loading3)
                .error(R.mipmap.ic_launcher)
                .into(holder.bigimg1);
        Log.e(TAG, "getChildView: " + "http://www.zhibo.tv" + data.get(2 * position).getImgUrl());
        Picasso.with(holder.smallimg1.getContext())
                .load("http://www.zhibo.tv" + data.get(2 * position).getPicUrl())
                .placeholder(R.drawable.common_loading3)
                .error(R.mipmap.ic_launcher)
                .transform(new CropCircleTransformation())
                .into(holder.smallimg1);
        Log.e(TAG, "getChildView: " + "http://www.zhibo.tv" + data.get(2 * position).getPicUrl());
        Picasso.with(holder.bigimg2.getContext())
                .load("http://www.zhibo.tv" + data.get(2 * position + 1).getImgUrl())
                .placeholder(R.drawable.common_loading3)
                .error(R.mipmap.ic_launcher)
                .into(holder.bigimg2);
        Picasso.with(holder.smallimg2.getContext())
                .load("http://www.zhibo.tv" + data.get(2 * position + 1).getPicUrl())
                .placeholder(R.drawable.common_loading3)
                .error(R.mipmap.ic_launcher)
                .transform(new CropCircleTransformation())
                .into(holder.smallimg2);
        holder.title1.setText(data.get(2 * position).getTitle());
        holder.caryge1.setText(data.get(2 * position).getNickname());
        holder.num1.setText(data.get(2 * position).getFollownum());
        holder.title2.setText(data.get(2 * position + 1).getTitle());
        holder.caryge2.setText(data.get(2 * position + 1).getNickname());
        holder.num2.setText(data.get(2 * position + 1).getFollownum());
        final int[] imags = {R.mipmap.host0, R.mipmap.host1, R.mipmap.host2, R.mipmap.host3, R.mipmap.host4,
                R.mipmap.host5, R.mipmap.host6, R.mipmap.host7, R.mipmap.host8, R.mipmap.host9,
                R.mipmap.host10, R.mipmap.host11, R.mipmap.host12, R.mipmap.host13, R.mipmap.host14,
                R.mipmap.host15, R.mipmap.host16, R.mipmap.host17, R.mipmap.host18, R.mipmap.host19,
                R.mipmap.host20, R.mipmap.host21, R.mipmap.host22, R.mipmap.host23, R.mipmap.host24,
                R.mipmap.host25, R.mipmap.host26, R.mipmap.host27, R.mipmap.host28, R.mipmap.host29,
                R.mipmap.host30, R.mipmap.host31, R.mipmap.host32, R.mipmap.host33, R.mipmap.host33,
                R.mipmap.host34, R.mipmap.host35, R.mipmap.host36, R.mipmap.host37, R.mipmap.host38,
                R.mipmap.host39};
        for (int i = 0; i < data.size(); i++) {
            if ((i + "").equals(data.get(2 * position).getLevel())) {
                holder.levelimg1.setBackgroundResource(imags[i]);
            }
            if ((i + "").equals(data.get(2 * position + 1).getLevel())) {
                holder.levelimg2.setBackgroundResource(imags[i]);
            }
        }
        if ("0".equals(data.get(2 * position).getSex())) {
            holder.seximg1.setBackgroundResource(R.mipmap.gril);
        } else {
            holder.seximg1.setBackgroundResource(R.mipmap.boy);
        }
        if ("0".equals(data.get(2 * position + 1).getSex())) {
            holder.seximg2.setBackgroundResource(R.mipmap.gril);
        } else {
            holder.seximg2.setBackgroundResource(R.mipmap.boy);
        }
        if ("0".equals(data.get(2 * position).getLiveStatus())) {
            holder.status1.setText("录播");
        } else {
            holder.status1.setText("直播");
        }
        if ("0".equals(data.get(2 * position + 1).getLiveStatus())) {
            holder.status2.setText("录播");
        } else {
            holder.status2.setText("直播");
        }
        holder.relayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZhuBoActivity.class);
                intent.putExtra("roomid", data.get(2 * position).getRoomId());
                intent.putExtra("title", data.get(2 * position).getTitle());
                intent.putExtra("status", data.get(2 * position).getLiveStatus());
                context.startActivity(intent);
            }
        });

        holder.relayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZhuBoActivity.class);
                intent.putExtra("roomid", data.get(2 * position + 1).getRoomId());
                intent.putExtra("title", data.get(2 * position + 1).getTitle());
                intent.putExtra("status", data.get(2 * position + 1).getLiveStatus());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        RelativeLayout relayout1, relayout2;
        TextView title1, caryge1, num1, title2, caryge2, num2, status1, status2;
        ImageView bigimg1, smallimg1, levelimg1, seximg1, bigimg2, smallimg2, levelimg2, seximg2;

        public ViewHolder(View itemView) {
            bigimg1 = (ImageView) itemView.findViewById(R.id.item_item_img);
            smallimg1 = (ImageView) itemView.findViewById(R.id.item_item_img2);
            levelimg1 = (ImageView) itemView.findViewById(R.id.item_item_img3);
            seximg1 = (ImageView) itemView.findViewById(R.id.item_item_img9);
            bigimg2 = (ImageView) itemView.findViewById(R.id.item_item_img4);
            smallimg2 = (ImageView) itemView.findViewById(R.id.item_item_img5);
            levelimg2 = (ImageView) itemView.findViewById(R.id.item_item_img6);
            seximg2 = (ImageView) itemView.findViewById(R.id.item_item_img10);
            title1 = (TextView) itemView.findViewById(R.id.item_item_tv1);
            caryge1 = (TextView) itemView.findViewById(R.id.item_item_tv2);
            num1 = (TextView) itemView.findViewById(R.id.item_item_tv3);
            title2 = (TextView) itemView.findViewById(R.id.item_item_tv4);
            caryge2 = (TextView) itemView.findViewById(R.id.item_item_tv5);
            num2 = (TextView) itemView.findViewById(R.id.item_item_tv6);
            relayout1 = (RelativeLayout) itemView.findViewById(R.id.item_item_relayout1);
            relayout2 = (RelativeLayout) itemView.findViewById(R.id.item_item_relayout2);
            status1 = (TextView) itemView.findViewById(R.id.item_item_tv7);
            status2 = (TextView) itemView.findViewById(R.id.item_item_tv8);
        }
    }
}