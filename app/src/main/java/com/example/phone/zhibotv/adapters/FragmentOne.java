package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phone.zhibotv.Jingcaihuifang;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.model.History;
import com.example.phone.zhibotv.model.Self;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/11/30.
 */
public class FragmentOne extends BaseAdapter implements View.OnClickListener {
    private final LayoutInflater inflate;
    private List<Self.DataBean.ListBean> data;
    private Context context;
    public FragmentOne(Context context, List<Self.DataBean.ListBean> data) {
        inflate = LayoutInflater.from(context);
        if (data!=null) {
            this.data=data;
        }else {
            this.data=new ArrayList<>();
        }
        this.context=context;
    }

    public  void  updataRes(List<Self.DataBean.ListBean> data){
        if (data!=null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return (data!=null?data.size():0)/2;
    }

    @Override
    public Self.DataBean.ListBean getItem(int position) {
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
            convertView=inflate.inflate(R.layout.fragmentone_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
     Picasso.with(holder.img1.getContext())
                .load(data.get(2*position).getImgUrl())
                .placeholder(R.mipmap.jiazaizhong)
                .error(R.mipmap.jiazaizhong)
                .into(holder.img1);
        holder.text1.setText(data.get(2*position).getTitle());
        Picasso.with(holder.img2.getContext())
                .load(data.get(2*position+1).getImgUrl())
                .placeholder(R.mipmap.jiazaizhong)
                .error(R.mipmap.jiazaizhong)
                .into(holder.img2);
        holder.text2.setText(data.get(2*position+1).getTitle());
        holder.linearLayout1.setOnClickListener(this);
        holder.linearLayout1.setTag(2*position);
        holder.linearLayout2.setOnClickListener(this);
        holder.linearLayout2.setTag(2*position+1);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position= (int) v.getTag();
        Intent intent=new Intent(context, Jingcaihuifang.class);
        intent.putExtra("url",data.get(position).getVideoList().get(0).getVideoUrl());
        context.startActivity(intent);


        }

    private class  ViewHolder{
        LinearLayout linearLayout1,linearLayout2;
        TextView text1,text2;
        ImageView img1,img2;
        public ViewHolder(View convertView) {
            text1 = (TextView) convertView.findViewById(R.id.one_item_tv1);
            img1=((ImageView) convertView.findViewById(R.id.one_item_img1));
            linearLayout1=((LinearLayout) convertView.findViewById(R.id.one_item_ll1));
            text2 = (TextView) convertView.findViewById(R.id.one_item_tv2);
            img2=((ImageView) convertView.findViewById(R.id.one_item_img2));
            linearLayout2=((LinearLayout) convertView.findViewById(R.id.one_item_ll2));
        }
    }
}

