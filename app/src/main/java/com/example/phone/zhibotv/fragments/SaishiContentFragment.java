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
import com.example.phone.zhibotv.events.SaiShiEvent;
import com.example.phone.zhibotv.model.SaiShiContentData;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaishiContentFragment extends BaseFragment {
    public static final String TAG=SaishiContentFragment.class.getSimpleName();
    private PullToRefreshListView refreshListView;
    private ListView mListView;
    private SaiShiContentAdapter contentAdapter;
    private String url="";

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
    }

    private void setupView(String url) {
        Log.e(TAG, "setupView: "+url );
        OkHttpUtils.get()
                .url(url)
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
    @Subscribe(threadMode = ThreadMode.MAIN ,sticky = true)
    public void onEvent(SaiShiEvent event){
        if (event.what==100) {
            url=event.getMsg();
            setupView(url);
        }

    }
}
