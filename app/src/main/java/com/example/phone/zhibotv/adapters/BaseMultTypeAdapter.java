package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-11-28.
 */
public abstract class BaseMultTypeAdapter<T> extends BaseAdapter {
    private List<T> data;
    private int[] layoutIds;
    private LayoutInflater inflater;

    public BaseMultTypeAdapter(Context context, List<T> data, int...layoutIds) {
        inflater=LayoutInflater.from(context);
        this.layoutIds=layoutIds;
        if (data==null) {
            this.data=new ArrayList<>();
        }else {
            this.data = data;
        }

    }
    public void updataRes(List<T> data){
        if (data!=null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }
    public void addRes(List<T> data){
        if (data!=null) {
            this.data.clear();
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
    public int getItemViewType(int position) {
        int type = 0;
        //根据位置获取对应对象
        T item = getItem(position);
        Class<?> itemClass = item.getClass();
        try {
            //获取class的type字段
            Field field = itemClass.getDeclaredField("type");
            //获取访问权限
            field.setAccessible(true);
            //获取字段中的值
            type = field.getInt(item);
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return layoutIds.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null) {
            //写布局的时候，布局id在传入的时候要和type对应，type0对应索引0，type1对应索引1，
            convertView=inflater.inflate(layoutIds[getItemViewType(position)],parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        //加载数据
        bindData(holder,getItem(position),position);
        return convertView;
    }

    public abstract void bindData(ViewHolder holder, T item, int position);

    protected static class ViewHolder {
        public View itemView;
        private Map<Integer, View> cacheViews;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            cacheViews = new HashMap<>();
        }

        public View getView(int resId) {
            View view = null;
            if (cacheViews.containsKey(resId)) {
                view = cacheViews.get(resId);
            } else {
                view = itemView.findViewById(resId);
                cacheViews.put(resId, view);
            }
            return view;

        }
    }

}
