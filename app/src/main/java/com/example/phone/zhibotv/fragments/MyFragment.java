package com.example.phone.zhibotv.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.BoBiChongZhiActivity;
import com.example.phone.zhibotv.BoBiTaskActivity;
import com.example.phone.zhibotv.DengLuActivity;
import com.example.phone.zhibotv.HistoryActivity;
import com.example.phone.zhibotv.MyFensiActivity;
import com.example.phone.zhibotv.MyFocusActivity;
import com.example.phone.zhibotv.MyMessageActivity;
import com.example.phone.zhibotv.PersonMessage;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.ZhuCeActivity;
import com.example.phone.zhibotv.events.MessageEvent;
import com.example.phone.zhibotv.model.MessageModel;
import com.example.phone.zhibotv.widget.SelectPopWindow;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016-11-26.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener,PlatformActionListener, RadioGroup.OnCheckedChangeListener {
    public static final String TAG = MyFragment.class.getSimpleName();
    private TextView mText;

    private SelectPopWindow menuWindow;
    private String icon;
    private String name;
    private ImageView mImage;
    private MessageModel model;
    private Handler mHandler;
    private boolean isDenglu;
    private boolean isTuiChu;
    private String message;
    private String iconMessage;
    private TextView mText1;
    private LinearLayout mLinear;
    private ImageView mImage1;
    private SharedPreferences preferences;
    private RadioGroup mRbGroup;
    private LinearLayout mLinearLayout, mLinearLayout2, mLinearLayout3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.my_layout, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferences = getActivity().getSharedPreferences("pass_on", getActivity().MODE_PRIVATE);
        message = preferences.getString("name", "用户名");
        iconMessage = preferences.getString("icon", "头像");
        isDenglu = preferences.getBoolean("isDengLu", false);
        mText = (TextView) inflate.findViewById(R.id.my_text_denglu);
        mImage = ((ImageView) inflate.findViewById(R.id.my_image_denglu));
        mText1 = ((TextView) inflate.findViewById(R.id.yonghuming_denglu));
        mLinear = ((LinearLayout) inflate.findViewById(R.id.bobi_denglu_message));
        //PersonMessage personMessage = new PersonMessage(this);
        initView();
    }

    private void passin() {
        mText1.setVisibility(View.VISIBLE);
        mLinear.setVisibility(View.VISIBLE);
        mText1.setText(message);
        Picasso.with(getActivity()).load(iconMessage).transform(new CropCircleTransformation()).into(mImage);
        mText.setText("直播ID:31297252");
        mText.setClickable(false);
    }

    private void initView() {
        mText.setOnClickListener(this);
        mImage1 = ((ImageView) inflate.findViewById(R.id.denglu_tiaozhuan));
        mImage1.setOnClickListener(this);

        mRbGroup = ((RadioGroup) inflate.findViewById(R.id.rb_paly));
        mRbGroup.setOnCheckedChangeListener(this);

        mLinearLayout = ((LinearLayout) inflate.findViewById(R.id.my_message));
        mLinearLayout2 = ((LinearLayout) inflate.findViewById(R.id.my_fensi));
        mLinearLayout3 = ((LinearLayout) inflate.findViewById(R.id.my_focus));
        mLinearLayout.setOnClickListener(this);
        mLinearLayout2.setOnClickListener(this);
        mLinearLayout3.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences = getActivity().getSharedPreferences("pass_on", getActivity().MODE_PRIVATE);
        message = preferences.getString("name", "用户名");
        iconMessage = preferences.getString("icon", "头像");
        isDenglu = preferences.getBoolean("isDengLu", false);

       // isTuiChu = preferences.getBoolean("isTuiChu", false);
        if (isDenglu) {
            passin();
        } else {
            mText1.setVisibility(View.GONE);
            mLinear.setVisibility(View.GONE);
            mText.setText("点击登录...");
            mText.setClickable(true);
            mImage.setImageResource(R.mipmap.lp_defult_avatar);
        }
        // mText.setClickable(false);
        /*if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
       /* if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }*/
    }

    @Subscribe
    public void onEvent(MessageEvent event) {
               /* model = event.getModel();
                name=model.getName();
                icon=model.getIcon();
                Log.e(TAG, "onEvent: "+name );
                Log.e(TAG, "onEvent: "+icon );
                mText.setText(name);
                Picasso.with(getActivity()).load(icon).into(mImage);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_text_denglu:
                if (isDenglu) {
                    return;
                } else {
                    onPopWindow();
                }
                break;
            case R.id.denglu_tiaozhuan:
                if (isDenglu) {
                    Intent intent = new Intent(getActivity(), PersonMessage.class);
                    startActivity(intent);
                } else {
                    onPopWindow();
                }
                break;
            case R.id.my_message:
                if (isDenglu) {
                    Intent intent = new Intent(getActivity(), MyMessageActivity.class);
                    startActivity(intent);
                } else {
                    onPopWindow();
                }
                break;
            case R.id.my_fensi:
                if (isDenglu) {
                    Intent intent = new Intent(getActivity(), MyFensiActivity.class);
                    startActivity(intent);
                } else {
                    onPopWindow();
                }
                break;
            case R.id.my_focus:
                if (isDenglu) {
                    Intent intent = new Intent(getActivity(), MyFocusActivity.class);
                    startActivity(intent);
                } else {
                    onPopWindow();
                }
                break;
        }
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

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
        Log.e(TAG, "onComplete: userId=" + platform.getDb().getUserId());

        SharedPreferences preferences = getActivity().getSharedPreferences("pass_on",
                getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", platform.getDb().getUserName());
        editor.putString("icon", platform.getDb().getUserIcon());
        editor.putBoolean("isDengLu", true);
        editor.commit();

    }


    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_item_history:
                if (isDenglu) {
                    Intent intent = new Intent(getActivity(), HistoryActivity.class);
                    startActivity(intent);
                } else {
                    onPopWindow();
                }
                break;
            case R.id.rb_item_task:
                if (isDenglu) {
                    Intent intent = new Intent(getActivity(), BoBiTaskActivity.class);
                    startActivity(intent);
                } else {
                    onPopWindow();
                }
                break;
            case R.id.btn_item_chongzhi:
                if (isDenglu) {
                    Intent intent = new Intent(getActivity(), BoBiChongZhiActivity.class);
                    startActivity(intent);
                } else {
                    onPopWindow();
                }
                break;
        }
    }

    public void onPopWindow() {
        menuWindow = new SelectPopWindow(getActivity(), itemsOnClick);
        menuWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /*@Override
    public boolean handleMessage(Message msg) {
        if (msg.what == 0x100) {
                Bundle bundle = (Bundle) msg.obj;
                mText1.setVisibility(View.VISIBLE);
                mText1.setText(bundle.getString("name"));
                Picasso.with(getActivity()).load(bundle.getString("icon")).transform(new CropCircleTransformation()).into(mImage);
                mLinear.setVisibility(View.VISIBLE);
                mText.setText("直播ID:31297252");
                mText.setClickable(false);
                isDenglu=true;
        }
        return false;
    }*/
    /*@Override
    public void onclickListener(int position) {
        SharedPreferences sharePreferences = getActivity().getSharedPreferences("pass_in", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor edit = sharePreferences.edit();
        edit.clear();
        edit.commit();
    }*/
}