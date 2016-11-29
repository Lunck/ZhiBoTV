package com.example.phone.zhibotv.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.DengLuActivity;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.ZhuCeActivity;
import com.example.phone.zhibotv.event.MessageEvent;
import com.example.phone.zhibotv.widget.SelectPopWindow;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Administrator on 2016-11-26.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener,PlatformActionListener {
    public static final String TAG=MyFragment.class.getSimpleName();
    private TextView mText;

    private SelectPopWindow menuWindow;
    private String icon;
    private String name;
    private ImageView mImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.my_layout, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mText = (TextView)inflate.findViewById(R.id.my_text_denglu);
        mText.setOnClickListener(this);

        mImage = ((ImageView) inflate.findViewById(R.id.my_image_denglu));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe
    public void onEvent(MessageEvent event) {
        icon = event.getIcon();
        name = event.getName();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_text_denglu:
                if (mText.getText().equals("点击登录...")) {
                    menuWindow = new SelectPopWindow(getActivity(), itemsOnClick);
                    menuWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                }else{
                    mText.setText(name);
                    Picasso.with(getActivity()).load(icon).into(mImage);
                }
                break;
        }
    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.image_btn_qq:
                    login(QQ.NAME);
                    break;
                case R.id.image_btn_weichat:
                    login(Wechat.NAME);
                    break;
                case R.id.image_btn_sina:
                    login(SinaWeibo.NAME);
                    break;
                case R.id.btn_zhuce:
                    Intent intent2 = new Intent(getActivity(), ZhuCeActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.btn_denglu:
                    Intent intent = new Intent(getActivity(), DengLuActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    private void login(String name) {
        ShareSDK.initSDK(getActivity());
        Platform platform = ShareSDK.getPlatform(getActivity(), name);
        platform.setPlatformActionListener(this);
        platform.authorize();
        platform.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
