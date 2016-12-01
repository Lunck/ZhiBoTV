package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phone.zhibotv.NowZhiboActivity;
import com.example.phone.zhibotv.PEactivity;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.ZhuBoActivity;
import com.example.phone.zhibotv.model.ShouyeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by my on 2016/11/28.
 */
public class EpandListViewAdapter extends BaseExpandableListAdapter {
    private static final String TAG = EpandListViewAdapter.class.getSimpleName();
    private List<ShouyeModel.DataBeanX.CategoryBean> data;
    private Context context;
    private LayoutInflater inflater;

    public EpandListViewAdapter(Context context, List<ShouyeModel.DataBeanX.CategoryBean> data){
        inflater = LayoutInflater.from(context);
        if (data!=null) {
            this.data=data;
        }else {
            this.data = new ArrayList<>();
        }
        this.context=context;
    }
    public void updataRes(List<ShouyeModel.DataBeanX.CategoryBean> data){
        if (data!=null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取组的个数
     */
    @Override
    public int getGroupCount() {
        return data != null ? data.size() : 0;
    }

    /**
     * 根据组的位置返回对应的Child个数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        List<ShouyeModel.DataBeanX.CategoryBean.DataBean> children = data.get(groupPosition).getData();
        return (children != null ? children.size() : 0)/2;
    }

    /**
     * 根据位置返回对应的组
     */
    @Override
    public ShouyeModel.DataBeanX.CategoryBean getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    /**
     * 根据组的位置和 Child的位置返回对应Child 数据
     */
    @Override
    public ShouyeModel.DataBeanX.CategoryBean.DataBean getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getData().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 是否具有稳定的id 默认false，如果真的有稳定唯一的id，可以返回true，可以提高加载效率
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获取组的View
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderParent holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.shouye_lv_parentiteam,parent,false);
            holder = new ViewHolderParent(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolderParent) convertView.getTag();
        }
        // 加载数据
        holder.name.setText(data.get(groupPosition).getName());
        holder.gengduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NowZhiboActivity.class);
                intent.putExtra("id",data.get(groupPosition).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    /**
     * 获取Child的view
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.shouye_lv_childitem,parent,false);
            holder = new ViewHolderChild(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolderChild) convertView.getTag();
        }
        // 加载数据
        Picasso.with(holder.bigimg1.getContext())
                .load("http://www.zhibo.tv"+data.get(groupPosition).getData().get(2*childPosition).getImgUrl())
                .placeholder(R.mipmap.jiazaizhong)
                .error(R.mipmap.jiazaizhong)
                .into(holder.bigimg1);
        Log.e(TAG, "getChildView: "+"http://www.zhibo.tv"+data.get(groupPosition).getData().get(2*childPosition).getImgUrl());
        Picasso.with(holder.smallimg1.getContext())
                .load("http://www.zhibo.tv"+data.get(groupPosition).getData().get(2*childPosition).getPicUrl())
                .placeholder(R.drawable.common_loading3)
                .error(R.mipmap.lp_defult_avatar)
                .transform(new CropCircleTransformation())
                .into(holder.smallimg1);
        Log.e(TAG, "getChildView: "+"http://www.zhibo.tv"+data.get(groupPosition).getData().get(2*childPosition).getPicUrl());
        Picasso.with(holder.bigimg2.getContext())
                .load("http://www.zhibo.tv"+data.get(groupPosition).getData().get(2*childPosition+1).getImgUrl())
                .placeholder(R.mipmap.jiazaizhong)
                .error(R.mipmap.jiazaizhong)
                .into(holder.bigimg2);
        Picasso.with(holder.smallimg2.getContext())
                .load("http://www.zhibo.tv"+data.get(groupPosition).getData().get(2*childPosition+1).getPicUrl())
                .placeholder(R.drawable.common_loading3)
                .error(R.mipmap.lp_defult_avatar)
                .transform(new CropCircleTransformation())
                .into(holder.smallimg2);
        holder.title1.setText(getChild(groupPosition,2*childPosition).getTitle());
        holder.caryge1.setText(getChild(groupPosition,2*childPosition).getNickname());
        holder.num1.setText(getChild(groupPosition,2*childPosition).getFollownum());
        holder.title2.setText(getChild(groupPosition,2*childPosition+1).getTitle());
        holder.caryge2.setText(getChild(groupPosition,2*childPosition+1).getNickname());
        holder.num2.setText(getChild(groupPosition,2*childPosition+1).getFollownum());
        final int[]imags={R.mipmap.host0,R.mipmap.host1,R.mipmap.host2,R.mipmap.host3,R.mipmap.host4,
                R.mipmap.host5,R.mipmap.host6,R.mipmap.host7,R.mipmap.host8,R.mipmap.host9,
                R.mipmap.host10,R.mipmap.host11,R.mipmap.host12,R.mipmap.host13,R.mipmap.host14,
                R.mipmap.host15,R.mipmap.host16,R.mipmap.host17,R.mipmap.host18,R.mipmap.host19,
                R.mipmap.host20,R.mipmap.host21,R.mipmap.host22,R.mipmap.host23,R.mipmap.host24,
                R.mipmap.host25,R.mipmap.host26,R.mipmap.host27, R.mipmap.host28,R.mipmap.host29,
                R.mipmap.host30, R.mipmap.host31,R.mipmap.host32,R.mipmap.host33,R.mipmap.host33,
                R.mipmap.host34,R.mipmap.host35,R.mipmap.host36, R.mipmap.host37,R.mipmap.host38,
                R.mipmap.host39};
        for (int i = 0; i < data.get(groupPosition).getData().size(); i++) {
            if ((i+"").equals(data.get(groupPosition).getData().get(2*childPosition).getLevel())){
                holder.levelimg1.setBackgroundResource(imags[i]);
            }
            if ((i+"").equals(data.get(groupPosition).getData().get(2*childPosition+1).getLevel())){
                holder.levelimg2.setBackgroundResource(imags[i]);
            }
        }
        if ("0".equals(data.get(groupPosition).getData().get(2*childPosition).getSex())) {
            holder.seximg1.setBackgroundResource(R.mipmap.gril);
        }else {
            holder.seximg1.setBackgroundResource(R.mipmap.boy);
        }
        if ("0".equals(data.get(groupPosition).getData().get(2*childPosition+1).getSex())) {
            holder.seximg2.setBackgroundResource(R.mipmap.gril);
        }else {
            holder.seximg2.setBackgroundResource(R.mipmap.boy);
        }
        if ("0".equals( data.get(groupPosition).getData().get(2*childPosition).getLiveStatus())) {
            holder.status1.setText("录播");
        }else{
            holder.status1.setText("直播");
        }
        if ("0".equals( data.get(groupPosition).getData().get(2*childPosition+1).getLiveStatus())) {
            holder.status2.setText("录播");
        }else{
            holder.status2.setText("直播");
        }
        holder.relayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ZhuBoActivity.class);
                intent.putExtra("roomid",data.get(groupPosition).getData().get(2*childPosition).getRoomId());
                intent.putExtra("title",data.get(groupPosition).getData().get(2*childPosition).getTitle());
                intent.putExtra("status",data.get(groupPosition).getData().get(2*childPosition).getLiveStatus());
                context.startActivity(intent);
            }
        });

        holder.relayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ZhuBoActivity.class);
                intent.putExtra("roomid",data.get(groupPosition).getData().get(2*childPosition+1).getRoomId());
                intent.putExtra("title",data.get(groupPosition).getData().get(2*childPosition+1).getTitle());
                intent.putExtra("status",data.get(groupPosition).getData().get(2*childPosition+1).getLiveStatus());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    /**
     * Child是否可以被选择   false Child 不可点 true Child 可点击
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    private static class ViewHolderParent{
        TextView name,gengduo;

        public ViewHolderParent(View itemView){
            name = (TextView) itemView.findViewById(R.id.shouye_lv_item_tv);
            gengduo= (TextView) itemView.findViewById(R.id.item_item_gengduo);

        }
    }

    private static class ViewHolderChild{
        RelativeLayout relayout1,relayout2;
        TextView title1,caryge1,num1,title2,caryge2,num2,status1,status2;
        ImageView bigimg1,smallimg1,levelimg1,seximg1,bigimg2,smallimg2,levelimg2,seximg2;
        public ViewHolderChild(View itemView){
            bigimg1= (ImageView) itemView.findViewById(R.id.item_item_img);
            smallimg1= (ImageView) itemView.findViewById(R.id.item_item_img2);
            levelimg1= (ImageView) itemView.findViewById(R.id.item_item_img3);
            seximg1= (ImageView) itemView.findViewById(R.id.item_item_img9);
            bigimg2= (ImageView) itemView.findViewById(R.id.item_item_img4);
            smallimg2= (ImageView) itemView.findViewById(R.id.item_item_img5);
            levelimg2= (ImageView) itemView.findViewById(R.id.item_item_img6);
            seximg2= (ImageView) itemView.findViewById(R.id.item_item_img10);
            title1= (TextView) itemView.findViewById(R.id.item_item_tv1);
            caryge1= (TextView) itemView.findViewById(R.id.item_item_tv2);
            num1= (TextView) itemView.findViewById(R.id.item_item_tv3);
            title2= (TextView) itemView.findViewById(R.id.item_item_tv4);
            caryge2= (TextView) itemView.findViewById(R.id.item_item_tv5);
            num2= (TextView) itemView.findViewById(R.id.item_item_tv6);
            relayout1= (RelativeLayout) itemView.findViewById(R.id.item_item_relayout1);
            relayout2= (RelativeLayout) itemView.findViewById(R.id.item_item_relayout2);
            status1= (TextView) itemView.findViewById(R.id.item_item_tv7);
            status2= (TextView) itemView.findViewById(R.id.item_item_tv8);
        }

    }
    //-------------------- 多布局 支持Group多布局，也支持Child多布局 ------------------------------
    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public int getChildTypeCount() {
        return super.getChildTypeCount();
    }

    @Override
    public int getGroupType(int groupPosition) {
        return super.getGroupType(groupPosition);
    }

    @Override
    public int getGroupTypeCount() {
        return super.getGroupTypeCount();
    }

}
