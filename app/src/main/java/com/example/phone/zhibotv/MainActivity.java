package com.example.phone.zhibotv;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.phone.zhibotv.fragments.GuanZhuFragemnt;
import com.example.phone.zhibotv.fragments.MyFragment;
import com.example.phone.zhibotv.fragments.SaiShiFragment;
import com.example.phone.zhibotv.fragments.ShouYeFragment;

import java.lang.reflect.InvocationTargetException;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    private Fragment showFragment;
    private RadioGroup mRadioGroup;

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
}
