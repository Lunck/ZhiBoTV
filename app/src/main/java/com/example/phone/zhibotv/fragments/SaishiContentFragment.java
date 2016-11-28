package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.SaiShiContentAdapter;
import com.example.phone.zhibotv.model.SaiShiContentData;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaishiContentFragment extends BaseFragment {
    public static final String TAG=SaishiContentFragment.class.getSimpleName();
    private PullToRefreshListView refreshListView;
    private ListView mListView;
    private SaiShiContentAdapter contentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate=inflater.inflate(R.layout.saishi_content_layout,container,false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setupView();
    }

    private void setupView() {
        OkHttpUtils.get()
                .url(UrlUtils.SAISHI_HEADER_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: "+response );
                        Gson gson = new Gson();
                        SaiShiContentData contentData = gson.fromJson(response, SaiShiContentData.class);
                        Log.e(TAG, "onResponse: "+contentData.getData().getScheduleList().toString());
                        contentAdapter.addRes(contentData.getData().getScheduleList());
                    }
                });
    }

    private void initView() {
        refreshListView = ((PullToRefreshListView) inflate.findViewById(R.id.saishi_content_listview));
        mListView = refreshListView.getRefreshableView();
        contentAdapter = new SaiShiContentAdapter(getActivity(),null,R.layout.saishi_two_item,R.layout.saishi_one_item);
        mListView.setAdapter(contentAdapter);


    }
}
