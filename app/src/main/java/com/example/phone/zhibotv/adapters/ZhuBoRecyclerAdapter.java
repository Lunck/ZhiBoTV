package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.SaiShiZhuBoActivity;
import com.example.phone.zhibotv.ZhuBoActivity;
import com.example.phone.zhibotv.model.ZhuBoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-28.
 */
public class ZhuBoRecyclerAdapter extends RecyclerView.Adapter<ZhuBoRecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private List<ZhuBoModel> data;
    private LayoutInflater inflater;
    private Context context;

    public ZhuBoRecyclerAdapter(Context context,List<ZhuBoModel> data) {
        inflater=LayoutInflater.from(context);
        this.context=context;
        if (data!=null) {
            this.data = data;
        }else {
            this.data=new ArrayList<>();
        }

    }
    public void addRes(List<ZhuBoModel> data){
        if (data!=null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.zhubo_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(data.get(position).getpName());
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Intent intent = new Intent(context, SaiShiZhuBoActivity.class);
        intent.putExtra("url",data.get(position).getpId());
        context.startActivity(intent);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView text;
        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            img= ((ImageView) itemView.findViewById(R.id.zhubo_item_img));
            text= ((TextView) itemView.findViewById(R.id.zhubo_item_text));
        }
    }
}
