package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.TeachViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/12/2.
 */
public class TeachFragmentthree extends Fragment {

    private static final String TAG = TeachFragmentthree.class.getSimpleName();
    private View layout;
    private ViewPager mviewPager;
    private TabLayout mtabLayout;
    private TeachViewPagerAdapter adapter;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_teach_three, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        Bundle bundle=getArguments();
        uid = bundle.getString("uid");
        Log.e(TAG, "initView: "+uid );
        mviewPager = ((ViewPager) layout.findViewById(R.id.three_vp));
        adapter = new TeachViewPagerAdapter(getChildFragmentManager(),getData());
        mviewPager.setAdapter(adapter);
        mtabLayout = ((TabLayout) layout.findViewById(R.id.three_tab));
        TabLayout.Tab newTab = mtabLayout.newTab();
        newTab.setText("周榜");
        mtabLayout.addTab(newTab);
        TabLayout.Tab newTab2 = mtabLayout.newTab();
        newTab2.setText("本场榜");
        mtabLayout.addTab(newTab2);
        TabLayout.Tab newTab3 = mtabLayout.newTab();
        newTab3.setText("总榜");
        mtabLayout.addTab(newTab3);
        // 添加viewPager滚动监听
        mviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mtabLayout));
        // 添加Tab选中的监听
        mtabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mviewPager));
    }
    private List<Fragment> getData() {
        List<Fragment> data = new ArrayList<>();
        WeekFragment fragment = new WeekFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        Log.e(TAG, "uid: "+uid);
        fragment.setArguments(bundle);
        data.add(fragment);
        NowTestFragment fragmenttwo = new NowTestFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("uid",uid);
        Log.e(TAG, "uid: "+uid);
        fragmenttwo.setArguments(bundle2);
        data.add(fragmenttwo);
        AllFragment fragmentthrenew=new AllFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("uid",uid);
        fragmentthrenew.setArguments(bundle3);
        data.add(fragmentthrenew);
        return data;
    }
}