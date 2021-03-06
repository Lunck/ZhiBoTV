package com.example.phone.zhibotv.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.example.phone.zhibotv.R;

/**
 * Created by Administrator on 2016/11/28.
 */
public class SelectPopWindow extends PopupWindow {
    private final Button mZhuCe;
    private final Button mDengLu;
    private ImageButton image_btn_qq, image_btn_weichat, image_btn_sina;
    private View mMenuView;

    public SelectPopWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.image_btn_item, null);

        mZhuCe = ((Button) mMenuView.findViewById(R.id.btn_zhuce));
        mDengLu = ((Button) mMenuView.findViewById(R.id.btn_denglu));
        image_btn_qq = (ImageButton) mMenuView.findViewById(R.id.image_btn_qq);
        image_btn_weichat = (ImageButton) mMenuView.findViewById(R.id.image_btn_weichat);
        image_btn_sina = (ImageButton) mMenuView.findViewById(R.id.image_btn_sina);


        image_btn_qq.setOnClickListener(itemsOnClick);
        image_btn_weichat.setOnClickListener(itemsOnClick);
        image_btn_sina.setOnClickListener(itemsOnClick);
        mZhuCe.setOnClickListener(itemsOnClick);
        mDengLu.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.anim.anim_bottom);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
