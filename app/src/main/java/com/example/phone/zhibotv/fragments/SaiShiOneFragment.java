package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiOneFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, TabLayout.OnTabSelectedListener, View.OnClickListener {
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
    private ImageView mMore;
    private ViewGroup container;
    private View popwindow;
    private TextView mBack;
    public DbManager.DaoConfig config=new DbManager.DaoConfig()
            .setDbName("historys.db")
            .setAllowTransaction(true)
            .setDbDir(Environment.getExternalStorageDirectory())
            .setDbVersion(1);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate=inflater.inflate(R.layout.saishi_one_layout,container,false);
        this.container=container;
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentList=new ArrayList<>();
        titleList=new ArrayList<>();
        initView();
        getDataFromDb();

    }

    private void getDataFromDb() {
        DbManager db = x.getDb(config);
        try {
            typeList = db.selector(SaiShiTabTitleModel.class).findAll();
            if (typeList!=null&&typeList.size()!=0) {
                for (int i = 0; i < typeList.size(); i++) {
                    SaishiContentFragment contentFragment = new SaishiContentFragment();
                    fragmentList.add(contentFragment);
                    titleList.add(typeList.get(i).getName());
                }
                SaiShiViewPagerAdapter pagerAdapter = new SaiShiViewPagerAdapter(getChildFragmentManager(),fragmentList,titleList);
                mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                mViewPager.setAdapter(pagerAdapter);
                mTablayout.setupWithViewPager(mViewPager);
                mTablayout.setOnTabSelectedListener(SaiShiOneFragment.this);
            }else {
                setupView();
            }
        } catch (DbException e) {
            e.printStackTrace();
            setupView();
        }
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
                        DbManager db = x.getDb(config);
                        for (int i = 0; i < typeList.size(); i++) {
                            SaishiContentFragment contentFragment = new SaishiContentFragment();
                            fragmentList.add(contentFragment);
                            titleList.add(typeList.get(i).getName());
                            try {
                                db.saveOrUpdate(typeList.get(i));
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
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
        //mViewPager.setOffscreenPageLimit(1);
        mRadiogroup = ((RadioGroup) inflate.findViewById(R.id.saishi_time_radiogroup));
        mMore = ((ImageView) inflate.findViewById(R.id.saishi_more));
        mMore.setOnClickListener(this);

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
        //mTablayout.setOnTabSelectedListener(SaiShiOneFragment.this);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SaiShiEvent event1 = new SaiShiEvent(120);
        SaiShiEvent event2 = new SaiShiEvent(130);
        switch (checkedId) {
            case R.id.saishi_btn1:
                EventBus.getDefault().postSticky(event2);
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime())+"";
                break;
            case R.id.saishi_btn2:
                EventBus.getDefault().postSticky(event1);
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime()+(86400000))+"";
                break;
            case R.id.saishi_btn3:
                EventBus.getDefault().postSticky(event1);
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime()+(86400000*2))+"";
                break;
            case R.id.saishi_btn4:
                EventBus.getDefault().postSticky(event1);
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime()+(86400000*3))+"";
                break;
            case R.id.saishi_btn5:
                EventBus.getDefault().postSticky(event1);
                time= android.text.format.DateFormat.format("yyyy-MM-dd",date.getTime()+(86400000*4))+"";
                break;
            case R.id.saishi_btn6:
                EventBus.getDefault().postSticky(event1);
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


    @Override
    public void onClick(View v) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.saishi_one_viewgroup,new SaiShiMoreFragment(),SaiShiMoreFragment.TAG);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode=ThreadMode.MAIN,sticky = true)
    public void onEvent(SaiShiEvent event){
        if (event.what==110) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            Fragment fragmentByTag = manager.findFragmentByTag(SaiShiMoreFragment.TAG);
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(fragmentByTag);
            fragmentTransaction.commit();
            mViewPager.setCurrentItem(event.getPosition());
        }
    }
}
