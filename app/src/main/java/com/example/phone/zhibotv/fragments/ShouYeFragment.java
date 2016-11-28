package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.ShouyeImagePagerAdapter;
import com.example.phone.zhibotv.model.ShouyeModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-26.
 */
public class ShouYeFragment extends BaseFragment{
    public static final String TAG=ShouYeFragment.class.getSimpleName();
    public static final String THEADER_URL="http://www.zhibo.tv/app/index/home";
    private ListView mListView;
    private View mHeader;
    private ViewPager mViewPager;
    private TextView mText;
    private LinearLayout mIndicators;
    private HorizontalScrollView mHvs;
    private LinearLayout mLlImag;
    private LinearLayout mLlText;
    private LinearLayout mLlText2;
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
        setupHeader();
    }

    private void setupView() {
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
                            Picasso.with(getActivity())
                                    .load("http://www.zhibo.tv"+scrollBeen.get(i).getImgUrl())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .error(R.mipmap.ic_launcher);
                               mImager.add(img);
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
        mListView = (ListView) inflate.findViewById(R.id.shouye_lv);
        mHeader = LayoutInflater.from(getActivity()).inflate(R.layout.shouye_header,null);
        mViewPager = (ViewPager) mHeader.findViewById(R.id.shouye_header_vp);
         mImager=new ArrayList<>();
        mText = (TextView) mHeader.findViewById(R.id.shouye_header_tv);
        mIndicators = (LinearLayout)mHeader.findViewById(R.id.shouye_header_indicators);
        mHvs = (HorizontalScrollView) mHeader.findViewById(R.id.shouye_header_hsv);
        mLlImag = (LinearLayout) mHeader.findViewById(R.id.ll_img_container);
        mLlText = (LinearLayout) mHeader.findViewById(R.id.ll_tv_container);
        mLlText2 = (LinearLayout) mHeader.findViewById(R.id.ll_tv2_container);
        mListView.addHeaderView(mHeader);
    }


}
