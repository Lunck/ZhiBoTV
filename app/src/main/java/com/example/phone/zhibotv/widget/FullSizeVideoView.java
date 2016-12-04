package com.example.phone.zhibotv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by Rock on 2016/11/10.
 */
public class FullSizeVideoView extends VideoView {

    public FullSizeVideoView(Context context) {
        super(context);
    }

    public FullSizeVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullSizeVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 咱们自己测量
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // 将自己测量出来的值设置给VideoView
        setMeasuredDimension(width,height);
    }
}
