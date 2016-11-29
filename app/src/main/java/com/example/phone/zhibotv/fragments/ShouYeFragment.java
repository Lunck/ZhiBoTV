package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.EpandListViewAdapter;
import com.example.phone.zhibotv.adapters.ShouyeImagePagerAdapter;
import com.example.phone.zhibotv.model.ShouyeModel;
import com.example.phone.zhibotv.pulltorefreshexpandlistview.XExpandListview;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-26.
 */
public class ShouYeFragment extends BaseFragment implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener{
    public static final String TAG=ShouYeFragment.class.getSimpleName();
    public static final String THEADER_URL="http://www.zhibo.tv/app/index/home";
    private XExpandListview mListView;
    private View mHeader;
    private ViewPager mViewPager;
    private TextView mText;
    private LinearLayout mIndicators;
    private HorizontalScrollView mHvs;
    private List<ImageView> mImager;
    private int previndex=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x100:
                    int currentItem = mViewPager.getCurrentItem();
                    currentItem++;
                    mViewPager.setCurrentItem(currentItem % mImager.size());
                    break;
            }
        }
    };
    private ShouyeModel shouyeModel;
    private EpandListViewAdapter adapter;
    private LinearLayout mllcontainer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.shouye_layout, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setupView();
        Log.e(TAG, "onActivityCreated: 2" );
        setupHeader();
        setupSrcoll();
    }

    private void setupSrcoll() {
        OkHttpUtils.get()
                .url(THEADER_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson=new Gson();
                        ShouyeModel srcollShouye=gson.fromJson(response,ShouyeModel.class);
                        List<ShouyeModel.DataBeanX.HotsBean>list=srcollShouye.getData().getHots();
                        for (int i = 0; i < list.size(); i++) {
                            Log.e(TAG, "onResponse: "+list.size() );
                            View inflate1 = LayoutInflater.from(getActivity()).inflate(R.layout.srcollitem,null);
                           ImageView mLlimg = (ImageView) inflate1.findViewById(R.id.ll_img);
                           TextView mLlText1 = (TextView) inflate1.findViewById(R.id.ll_tv);
                           TextView mLlText = ((TextView) inflate1.findViewById(R.id.ll_tv2));
                            Picasso.with(mLlimg.getContext())
                                    .load("http://www.zhibo.tv"+list.get(i).getPicUrl())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .error(R.mipmap.ic_launcher)
                                    .transform(new CropCircleTransformation())
                                    .into(mLlimg);
                            mLlText1.setText(list.get(i).getNickname());
                            Log.e(TAG, "onResponse: "+mLlText1 );
                            mLlText.setText(list.get(i).getCategoryName());
                            Log.e(TAG, "onResponse: "+mLlText );
                            mllcontainer.addView(inflate1);
                        }

                    }
                });
    }
    private void setupView() {
        OkHttpUtils.get()
                .url(THEADER_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " );
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: " +response);
                        Gson gson=new Gson();
                        shouyeModel = gson.fromJson(response,ShouyeModel.class);
                        Log.e(TAG, "onResponse: "+shouyeModel);
                        List<ShouyeModel.DataBeanX.CategoryBean>data=new ArrayList<ShouyeModel.DataBeanX.CategoryBean>();
                        for (int i = 0; i < shouyeModel.getData().getCategory().size(); i++) {
                            ShouyeModel.DataBeanX.CategoryBean parent=new ShouyeModel.DataBeanX.CategoryBean();
                            parent.setName(shouyeModel.getData().getCategory().get(i).getName());
                            ArrayList<ShouyeModel.DataBeanX.CategoryBean.DataBean>children=new ArrayList<ShouyeModel.DataBeanX.CategoryBean.DataBean>();
                            for (int j = 0; j < shouyeModel.getData().getCategory().get(i).getData().size(); j++) {
                                ShouyeModel.DataBeanX.CategoryBean.DataBean child=new ShouyeModel.DataBeanX.CategoryBean.DataBean();
                                child.setFollownum(shouyeModel.getData().getCategory().get(i).getData().get(j).getFollownum());
                                child.setImgUrl(shouyeModel.getData().getCategory().get(i).getData().get(j).getImgUrl());
                                child.setLevel(shouyeModel.getData().getCategory().get(i).getData().get(j).getLevel());
                                child.setPicUrl(shouyeModel.getData().getCategory().get(i).getData().get(j).getPicUrl());
                                child.setNickname(shouyeModel.getData().getCategory().get(i).getData().get(j).getNickname());
                                child.setSex(shouyeModel.getData().getCategory().get(i).getData().get(j).getSex());
                                child.setTitle(shouyeModel.getData().getCategory().get(i).getData().get(j).getTitle());
                                child.setRoomId(shouyeModel.getData().getCategory().get(i).getData().get(j).getRoomId());
                                children.add(child);
                            }
                            parent.setData(children);
                            data.add(parent);
                            Log.e(TAG, "onResponse: "+data.size() );
                        }

                        adapter.updataRes(data);
                        for (int i = 0; i < adapter.getGroupCount(); i++) {
                            mListView.expandGroup(i);
                        }
                    }

                });
    }

    private void setupHeader() {
        OkHttpUtils.get()
                .url(THEADER_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson=new Gson();
                        ShouyeModel shouyeModel=gson.fromJson(response,ShouyeModel.class);
                        List<ShouyeModel.DataBeanX.ScrollBean> scrollBeen = shouyeModel.getData().getScroll();
                        for (int i = 0; i < scrollBeen.size(); i++) {
                            ImageView img=new ImageView(getActivity());
                            View view=new View(getActivity());
                            view.setBackgroundResource(R.mipmap.dot_normal);
                            view.setLayoutParams(new LinearLayout.LayoutParams(15,15));
                            mIndicators.addView(view);
                            Picasso.with(img.getContext())
                                    .load("http://www.zhibo.tv"+scrollBeen.get(i).getImgUrl())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .error(R.mipmap.ic_launcher)
                                    .into(img);
                            Log.e(TAG, "onResponse: "+"http://www.zhibo.tv"+scrollBeen.get(i).getImgUrl() );
                            mImager.add(img);
                            Log.e(TAG, "onResponse: mImager"+mImager );
                            }
                        ShouyeImagePagerAdapter imgadapter = new ShouyeImagePagerAdapter(mImager);
                        mViewPager.setAdapter(imgadapter);
                        mIndicators.getChildAt(0).setBackgroundResource(R.mipmap.dot_enable);
                        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                mIndicators.getChildAt(position).setBackgroundResource(R.mipmap.dot_enable);
                                mIndicators.getChildAt(previndex).setBackgroundResource(R.mipmap.dot_normal);
                                previndex=position;

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                                }

                        });
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (true){
                                    SystemClock.sleep(3000);
                                    handler.sendEmptyMessage(0x100);

                                }

                            }
                        }).start();
                    }
                });

    }

    private void initView() {

        mListView = ( XExpandListview) inflate.findViewById(R.id.shouye_lv);
        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(new XExpandListview.IXListViewListener() {
            @Override
            public void onRefresh() {
                setupView2();
                mListView.stopRefresh();
                mListView.setRefreshTime(System.currentTimeMillis());
            }

            @Override
            public void onLoadMore() {

            }
        });
        mHeader = LayoutInflater.from(getActivity()).inflate(R.layout.shouye_header,null);
        mViewPager = (ViewPager) mHeader.findViewById(R.id.shouye_header_vp);
         mImager=new ArrayList<>();
        mText = (TextView) mHeader.findViewById(R.id.shouye_header_tv);
        mIndicators = (LinearLayout)mHeader.findViewById(R.id.shouye_header_indicators);
        mHvs = (HorizontalScrollView) mHeader.findViewById(R.id.shouye_header_hsv);
        mllcontainer = (LinearLayout) mHeader.findViewById(R.id.ll_contianer);
        mListView.addHeaderView(mHeader);
        adapter = new EpandListViewAdapter(getActivity(),null);
        Log.e(TAG, "initView: " );
         mListView.setAdapter(adapter);
        mListView.setOnGroupClickListener(this);
        mListView.setOnChildClickListener(this);

    }

    private void setupView2() {
        OkHttpUtils.get()
                .url(THEADER_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " );
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: " +response);
                        Gson gson=new Gson();
                        shouyeModel = gson.fromJson(response,ShouyeModel.class);
                        Log.e(TAG, "onResponse: "+shouyeModel);
                        List<ShouyeModel.DataBeanX.CategoryBean>data=new ArrayList<ShouyeModel.DataBeanX.CategoryBean>();
                        for (int i = 0; i < shouyeModel.getData().getCategory().size(); i++) {
                            ShouyeModel.DataBeanX.CategoryBean parent=new ShouyeModel.DataBeanX.CategoryBean();
                            parent.setName(shouyeModel.getData().getCategory().get(i).getName());
                            ArrayList<ShouyeModel.DataBeanX.CategoryBean.DataBean>children=new ArrayList<ShouyeModel.DataBeanX.CategoryBean.DataBean>();
                            for (int j = 0; j < shouyeModel.getData().getCategory().get(i).getData().size(); j++) {
                                ShouyeModel.DataBeanX.CategoryBean.DataBean child=new ShouyeModel.DataBeanX.CategoryBean.DataBean();
                                child.setFollownum(shouyeModel.getData().getCategory().get(i).getData().get(j).getFollownum());
                                child.setImgUrl(shouyeModel.getData().getCategory().get(i).getData().get(j).getImgUrl());
                                child.setLevel(shouyeModel.getData().getCategory().get(i).getData().get(j).getLevel());
                                child.setPicUrl(shouyeModel.getData().getCategory().get(i).getData().get(j).getPicUrl());
                                child.setNickname(shouyeModel.getData().getCategory().get(i).getData().get(j).getNickname());
                                child.setSex(shouyeModel.getData().getCategory().get(i).getData().get(j).getSex());
                                child.setTitle(shouyeModel.getData().getCategory().get(i).getData().get(j).getTitle());
                                child.setRoomId(shouyeModel.getData().getCategory().get(i).getData().get(j).getRoomId());
                                children.add(child);
                            }
                            parent.setData(children);
                            data.add(parent);
                            Log.e(TAG, "onResponse: "+data.size() );
                        }
                        adapter.updataRes(data);
                        }


                });

    }


    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        adapter.getGroup(groupPosition).getData().get(2*childPosition).getRoomId();
        return false;
    }


}
