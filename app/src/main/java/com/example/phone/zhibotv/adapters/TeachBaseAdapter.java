package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-11-30.
 */
public abstract class TeachBaseAdapter<T> extends BaseAdapter {

    private List<T> data;
    private LayoutInflater inflater;
    private int layoutResId;

    public TeachBaseAdapter(Context context, List<T> data, int layoutResId) {
        inflater=LayoutInflater.from(context);
        this.layoutResId=layoutResId;
        if (data==null){
            this.data=new ArrayList<>();
        }else {
            this.data = data;
        }

    }

    public void updateRes(List<T> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addRes(List<T> data) {
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }

    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(layoutResId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //数据加载
        bindData(holder,getItem(position),position);

        return convertView;
    }

    protected abstract void bindData(ViewHolder holder, T item, int position);

    public static class ViewHolder {
        View itemView;
        //做一个map缓存，专门缓存已经实例化的的View
        Map<Integer,View> cacheView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            cacheView=new HashMap<>();
        }

        /**
         * 获取itemView中的childView
         *
         * @param resId
         * @return
         */
        public View getView(int resId) {
            View view=null;
            //判断缓存中是否包含我们实例化的view
            if (cacheView.containsKey(resId)) {
                //可以直接返回
                view=cacheView.get(resId);
            }else {
                //实例化一个，添加到缓存中
                view=itemView.findViewById(resId);
                cacheView.put(resId,view);
            }
            return view;
        }
        public void setText(int resId,String content){
            TextView textView = (TextView) getView(resId);
            textView.setText(content);

        }
    }

}
