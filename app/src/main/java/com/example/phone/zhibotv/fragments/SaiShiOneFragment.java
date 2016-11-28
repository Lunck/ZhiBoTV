package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.SaiShiViewPagerAdapter;
import com.example.phone.zhibotv.model.SaiShiTabTitleData;
import com.example.phone.zhibotv.model.SaiShiTabTitleModel;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiOneFragment extends BaseFragment {
    public static final String TAG=SaiShiOneFragment.class.getSimpleName();
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private List<String>  titleList;
    private PullToRefreshListView refreshListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         inflate=inflater.inflate(R.layout.saishi_one_layout,container,false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentList=new ArrayList<>();
        titleList=new ArrayList<>();
        initView();
        setupView();
    }


    private void setupView() {
        OkHttpUtils.get()
                .url(UrlUtils.SAISHI_TITLE_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        SaiShiTabTitleData titleData = gson.fromJson(response, SaiShiTabTitleData.class);
                        List<SaiShiTabTitleModel> typeList = titleData.getData().getTypeList();
                        for (int i = 0; i < typeList.size(); i++) {
                            SaishiContentFragment contentFragment = new SaishiContentFragment();
                            fragmentList.add(contentFragment);
                            titleList.add(typeList.get(i).getName());
                        }
                        SaiShiViewPagerAdapter pagerAdapter = new SaiShiViewPagerAdapter(getChildFragmentManager(),fragmentList,titleList);
                        mTablayout.setupWithViewPager(mViewPager);
                        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        mViewPager.setAdapter(pagerAdapter);
                    }
                });
    }

    private void initView() {
        mTablayout = ((TabLayout) inflate.findViewById(R.id.saishi_tablayout));
        mViewPager = ((ViewPager) inflate.findViewById(R.id.saizhi_viewpager));

    }
}
