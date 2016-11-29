package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.events.SaiShiEvent;
import com.example.phone.zhibotv.model.SaiShiTabTitleModel;
import com.example.phone.zhibotv.model.ZhuBoModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiMoreAdapter extends RecyclerView.Adapter<SaiShiMoreAdapter.ViewHolder> implements View.OnClickListener {
    private List<SaiShiTabTitleModel> data;
    private LayoutInflater inflater;

    public SaiShiMoreAdapter(Context context, List<SaiShiTabTitleModel> data) {
        inflater=LayoutInflater.from(context);
        if (data!=null) {
            this.data = data;
        }else {
            this.data=new ArrayList<>();
        }

    }
    public void addRes(List<SaiShiTabTitleModel> data){
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
        holder.text.setText(data.get(position).getName());
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        SaiShiEvent saiShiEvent = new SaiShiEvent(110);
        saiShiEvent.setPosition(position);
        EventBus.getDefault().postSticky(saiShiEvent);
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
