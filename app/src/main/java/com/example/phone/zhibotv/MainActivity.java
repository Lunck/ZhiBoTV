package com.example.phone.zhibotv;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.phone.zhibotv.event.MessageEvent;
import com.example.phone.zhibotv.fragments.GuanZhuFragemnt;
import com.example.phone.zhibotv.fragments.MyFragment;
import com.example.phone.zhibotv.fragments.SaiShiFragment;
import com.example.phone.zhibotv.fragments.ShouYeFragment;
import com.example.phone.zhibotv.widget.SelectPopWindow;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener,PlatformActionListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private Fragment showFragment;
    private RadioGroup mRadioGroup;
    private ImageButton mImageBtn;

    private SelectPopWindow menuWindow;
    private boolean isExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setUpView();
    }

    private void setUpView() {

    }


    private void initView() {
        mImageBtn = (ImageButton) findViewById(R.id.main_img_btn);
        mImageBtn.setOnClickListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        showFragment = new ShouYeFragment();
        transaction.add(R.id.main_framelayout,showFragment,ShouYeFragment.TAG);
        transaction.commit();

        mRadioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_shouye:
                SwipFragment(ShouYeFragment.class,ShouYeFragment.TAG);
                break;
            case R.id.main_saishi:
                SwipFragment(SaiShiFragment.class,SaiShiFragment.TAG);
                break;

            case R.id.main_guanzhu:
                SwipFragment(GuanZhuFragemnt.class,GuanZhuFragemnt.TAG);
                break;
            case R.id.main_wode:
                SwipFragment(MyFragment.class,MyFragment.TAG);
                break;

        }
    }

    private void SwipFragment(Class<? extends Fragment> fragmentClass, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(showFragment);
        showFragment=fragmentManager.findFragmentByTag(tag);
        if (showFragment!=null) {
            transaction.show(showFragment);
        }else {
            try {
                showFragment= fragmentClass.getConstructor().newInstance();
                transaction.add(R.id.main_framelayout,showFragment,tag);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        onSelectPopWindow();
    }

    public void onSelectPopWindow() {
        menuWindow = new SelectPopWindow(this, itemsOnClick);
        menuWindow.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
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
                    Intent intent2 = new Intent(MainActivity.this, ZhuCeActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.btn_denglu:
                    Intent intent = new Intent(MainActivity.this, DengLuActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit=true;
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit=false;
                    }
                },3*1000);
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    private void login(String name) {
        ShareSDK.initSDK(this);
        Platform platform = ShareSDK.getPlatform(this, name);
        platform.setPlatformActionListener(this);
        platform.authorize();
        platform.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        MessageEvent messageEvent = new MessageEvent(platform.getDb().getUserIcon(),platform.getDb().getUserName());
        Log.e(TAG, "onComplete: "+messageEvent );
        EventBus.getDefault().post(messageEvent);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
