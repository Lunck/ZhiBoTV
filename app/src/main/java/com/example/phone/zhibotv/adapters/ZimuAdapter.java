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
public class ZimuAdapter extends BaseAdapter {
    private static final String TAG = ZimuAdapter.class.getSimpleName();
    private final LayoutInflater inflate;
    private List<String> data;
    private Context context;
    public ZimuAdapter(Context context, List<String> data) {
        inflate = LayoutInflater.from(context);
        if (data!=null) {
            this.data=data;
        }else {
            this.data=new ArrayList<>();
        }
        this.context=context;
    }

    public  void  updataRes(List<String> data){
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
    public String  getItem(int position) {
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
            convertView=inflate.inflate(R.layout.zimu,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
              holder.text1.setText(data.get(position));
        return convertView;
    }
    private class  ViewHolder{
        TextView text1;

        public ViewHolder(View convertView) {
            text1 = (TextView) convertView.findViewById(R.id.bofang);
               }
    }
}
