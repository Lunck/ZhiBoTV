package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiOneFragment extends BaseFragment {
    public static final String TAG=SaiShiOneFragment.class.getSimpleName();
    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         inflate=inflater.inflate(R.layout.saishi_one_layout,container,false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setupView();
    }

    private void setupView() {

    }

    private void initView() {
        mTablayout = ((TabLayout) inflate.findViewById(R.id.saishi_tablayout));
        mViewPager = ((ViewPager) inflate.findViewById(R.id.saizhi_viewpager));

    }
}
