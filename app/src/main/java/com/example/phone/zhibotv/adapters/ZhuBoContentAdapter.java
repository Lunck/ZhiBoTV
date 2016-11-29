package com.example.phone.zhibotv.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.model.ZhuBoContentModel;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016-11-29.
 */
public class ZhuBoContentAdapter extends RecyclerViewMultAdapter<ZhuBoContentModel> {
    private Context context;
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saishi_zhubo_content_item, parent, false);
        this.context=parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, ZhuBoContentModel data) {
        if(viewHolder instanceof ViewHolder) {

            switch (data.getLiveStatus()) {
                case 0:
                    ((ViewHolder) viewHolder).text.setText("录播");
                    break;
                case 1:
                    ((ViewHolder) viewHolder).text.setText("直播");
                    break;
            }
            ((ViewHolder) viewHolder).title.setText(data.getTitle());
            ((ViewHolder) viewHolder).name.setText(data.getNickname());
            ((ViewHolder) viewHolder).people.setText(data.getFollownum());
            Picasso.with(context).load(UrlUtils.BASE_URL+data.getImgUrl()).into( ((ViewHolder) viewHolder).img);
            Picasso.with(context).load(UrlUtils.BASE_URL+data.getPicUrl()).transform(new CropCircleTransformation()).into( ((ViewHolder) viewHolder).img2);
            switch (data.getLiveStatus()) {
                case 0:
                    ((ViewHolder) viewHolder).imgsex.setImageResource(R.mipmap.gril);
                    break;
                case 1:
                    ((ViewHolder) viewHolder).imgsex.setImageResource(R.mipmap.boy);
                    break;
            }
        }


    }
    public class ViewHolder extends RecyclerViewMultAdapter.Holder{

        ImageView img,img2,imgsex;
        TextView text,title,name,people;

        public ViewHolder(View itemView) {
            super(itemView);
            img= ((ImageView) itemView.findViewById(R.id.zhubo_content_item_img));
            img2= ((ImageView) itemView.findViewById(R.id.zhubo_content_item_img2));
            imgsex= ((ImageView) itemView.findViewById(R.id.zhubo_content_item_sex));


            text= ((TextView) itemView.findViewById(R.id.zhubo_content_item_text));
            title= ((TextView) itemView.findViewById(R.id.zhubo_content_item_title));
            name= ((TextView) itemView.findViewById(R.id.zhubo_content_item_name));
            people= ((TextView) itemView.findViewById(R.id.zhubo_content_item_people));
        }
    }
}
