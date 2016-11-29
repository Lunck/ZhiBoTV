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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.SaiShiViewPagerAdapter;
import com.example.phone.zhibotv.events.SaiShiEvent;
import com.example.phone.zhibotv.model.SaiShiTabTitleData;
import com.example.phone.zhibotv.model.SaiShiTabTitleModel;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiOneFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, TabLayout.OnTabSelectedListener {
    public static final String TAG=SaiShiOneFragment.class.getSimpleName();
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private List<String>  titleList;
    private RadioGroup mRadiogroup;
    private Date date;
    private String time;
    private String type="0";
    private List<SaiShiTabTitleModel> typeList;
    private String url;

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
        Log.e(TAG, "setupView: " );
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
                        typeList = titleData.getData().getTypeList();
                        for (int i = 0; i < typeList.size(); i++) {
                            SaishiContentFragment contentFragment = new SaishiContentFragment();
                            fragmentList.add(contentFragment);
                            titleList.add(typeList.get(i).getName());
                        }
                        Log.e(TAG, "onResponse: " );

                        SaiShiViewPagerAdapter pagerAdapter = new SaiShiViewPagerAdapter(getChildFragmentManager(),fragmentList,titleList);
                        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        mViewPager.setAdapter(pagerAdapter);
                        mTablayout.setupWithViewPager(mViewPager);
                        mTablayout.setOnTabSelectedListener(SaiShiOneFragment.this);

                    }
                });
    }

    private void initView() {
        mTablayout = ((TabLayout) inflate.findViewById(R.id.saishi_tablayout));
        mViewPager = ((ViewPager) inflate.findViewById(R.id.saizhi_viewpager));
        mRadiogroup = ((RadioGroup) inflate.findViewById(R.id.saishi_time_radiogroup));



        date = new Date();
        time=android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime())+"";
        url = UrlUtils.SAISHI_HEADER_URL + type + UrlUtils.SAISHI_FOOTER_URL + time;
        SaiShiEvent event = new SaiShiEvent(100);
        event.setMsg(url);
        Log.e(TAG, "onTabSelected: "+url );
        EventBus.getDefault().postSticky(event);
        Log.e(TAG, "initView: "+date );
        for (int i = 0; i < mRadiogroup.getChildCount(); i++) {
            if (i>0) {
                RadioButton button = (RadioButton) mRadiogroup.getChildAt(i);
                button.setText(android.text.format.DateFormat.format("MM/dd",date.getTime()+(86400000*i)));
            }
        }
        mRadiogroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.saishi_btn1:
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime())+"";
                break;
            case R.id.saishi_btn2:
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime()+(86400000))+"";
                break;
            case R.id.saishi_btn3:
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime()+(86400000*2))+"";
                break;
            case R.id.saishi_btn4:
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime()+(86400000*3))+"";
                break;
            case R.id.saishi_btn5:
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime()+(86400000*4))+"";
                break;
            case R.id.saishi_btn6:
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime()+(86400000*5))+"";
                break;
        }
        url = UrlUtils.SAISHI_HEADER_URL + type + UrlUtils.SAISHI_FOOTER_URL + time;;
        SaiShiEvent event = new SaiShiEvent(100);
        event.setMsg(url);
        EventBus.getDefault().postSticky(event);
        Log.e(TAG, "onCheckedChanged: "+url );
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.e(TAG, "onTabSelected: "+tab.getText() );
        for (int i = 0; i < typeList.size(); i++) {
            if (tab.getText().equals(typeList.get(i).getName())) {
                type=typeList.get(i).getId();
            }
        }
        url = UrlUtils.SAISHI_HEADER_URL + type + UrlUtils.SAISHI_FOOTER_URL + time;
        SaiShiEvent event = new SaiShiEvent(100);
        event.setMsg(url);
        Log.e(TAG, "onTabSelected: "+url );
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }



}
