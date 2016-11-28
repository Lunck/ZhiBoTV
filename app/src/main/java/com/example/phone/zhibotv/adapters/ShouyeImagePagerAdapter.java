package com.example.phone.zhibotv.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by my on 2016/11/28.
 */
public class ShouyeImagePagerAdapter extends PagerAdapter {
    private List<ImageView> data;
    public ShouyeImagePagerAdapter(List<ImageView> data) {
        this.data = data;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (data!=null&&data.size()>position) {
            container.addView(data.get(position));
            return data.get(position);
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (data!=null&&data.size()>position) {
            container.removeView(data.get(position));
        }
    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

}
