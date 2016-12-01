package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.model.History;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/11/30.
 */
public class DbAdapter extends BaseAdapter {
    private final LayoutInflater inflate;
    private List<History> data;
    public DbAdapter(Context context,List<History> data) {
        inflate = LayoutInflater.from(context);
        if (data!=null) {
            this.data=data;
        }else {
            this.data=new ArrayList<>();
        }
    }
    public  void  updataRes(List<History> data){
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
    public History getItem(int position) {
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
            convertView=inflate.inflate(R.layout.history,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
          holder.text.setText(data.get(position).getRoomid());
        return convertView;
    }
    private class  ViewHolder{
        TextView text;
        public ViewHolder(View convertView) {
            text = (TextView) convertView.findViewById(R.id.history_tv);
        }
    }
}
