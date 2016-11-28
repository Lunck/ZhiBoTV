package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;

/**
 * Created by Administrator on 2016-11-26.
 */
public class SaiShiFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    public static final String TAG=SaiShiFragment.class.getSimpleName();
    private RadioGroup mRadiogroup;
    private Fragment showFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.saishi_layout, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mRadiogroup = ((RadioGroup) inflate.findViewById(R.id.saishi_radiogroup));
        mRadiogroup.setOnCheckedChangeListener(this);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        showFragment = new SaiShiOneFragment();
        transaction.add(R.id.saishi_fragment,showFragment,SaiShiOneFragment.TAG);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.saishi_btn:
                SwipFragment(SaiShiOneFragment.class,SaiShiOneFragment.TAG);
                break;

            case R.id.zhubo_btn:
                SwipFragment(SaiShiTwoFragment.class,SaiShiTwoFragment.TAG);
                break;
        }
    }

    private void SwipFragment(Class<? extends Fragment> fragmentClass, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(showFragment);
        showFragment=fragmentManager.findFragmentByTag(tag);
        if (showFragment!=null) {
            transaction.show(showFragment);
        }else {
            try {
                showFragment= fragmentClass.newInstance();
                transaction.add(R.id.saishi_fragment,showFragment,tag);
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        transaction.commit();

    }
}
