package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.model.Paihang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by my on 2016/12/2.
 */
public class AllAdapter extends BaseAdapter {
    private static final String TAG = AllAdapter.class.getSimpleName();
    private final LayoutInflater inflate;
    private List<Paihang.RankingBean.TotalBean> data;
    private Context context;
    public AllAdapter(Context context, List<Paihang.RankingBean.TotalBean> data) {
        inflate = LayoutInflater.from(context);
        if (data!=null) {
            this.data=data;
        }else {
            this.data=new ArrayList<>();
        }
        this.context=context;
    }

    public  void  updataRes(List<Paihang.RankingBean.TotalBean> data){
        if (data!=null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public Paihang.RankingBean.TotalBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            convertView=inflate.inflate(R.layout.paihang_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.text1.setText(data.get(position).getRank());
        Picasso.with(holder.img1.getContext())
                .load("http://www.zhibo.tv"+data.get(position).getPic())
                .placeholder(R.mipmap.jiazaizhong)
                .error(R.mipmap.jiazaizhong)
                .transform(new CropCircleTransformation())
                .into(holder.img1);
        switch (data.get(position).getRank()) {
            case "1":
                Log.e(TAG, "getView: "+ data.get(position).getRank());
                holder.img2.setImageResource(R.mipmap.vip_level1);
                break;
            case "2":
                Log.e(TAG, "getView: "+ data.get(position).getRank());
                holder.img2.setImageResource(R.mipmap.vip_level2);
                break;
            case "3":
                Log.e(TAG, "getView: "+ data.get(position).getRank());
                holder.img2.setImageResource(R.mipmap.vip_level3);
                break;
            default:
                holder.img2.setVisibility(View.GONE);
                break;
        }
        holder.text2.setText(data.get(position).getName());
        holder.text3.setText(data.get(position).getPoints());

        return convertView;
    }
    private class  ViewHolder{
        TextView text1,text2,text3;
        ImageView img1,img2;
        public ViewHolder(View convertView) {
            text1 = (TextView) convertView.findViewById(R.id.panghang_item_tv1);
            img1=((ImageView) convertView.findViewById(R.id.panghang_item_img));
            text3=((TextView) convertView.findViewById(R.id.panghang_item_tv3));
            text2 = (TextView) convertView.findViewById(R.id.panghang_item_tv2);
            img2=((ImageView) convertView.findViewById(R.id.panghang_item_img2));
            }
    }
}
