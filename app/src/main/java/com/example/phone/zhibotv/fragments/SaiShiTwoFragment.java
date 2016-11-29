package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.ZhuBoRecyclerAdapter;
import com.example.phone.zhibotv.model.ZhuBoData;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiTwoFragment extends BaseFragment {
    public static final String TAG=SaiShiTwoFragment.class.getSimpleName();
    private RecyclerView mRecycler;
    private ZhuBoRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate=inflater.inflate(R.layout.saishi_two_layout,container,false);
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
                .url(UrlUtils.ZHUBO_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        ZhuBoData zhuBoData = gson.fromJson(response, ZhuBoData.class);
                        adapter.addRes(zhuBoData.getData());
                    }
                });
    }

    private void initView() {
        mRecycler = ((RecyclerView) inflate.findViewById(R.id.zhubo_recycler));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        mRecycler.setLayoutManager(layoutManager);
        adapter = new ZhuBoRecyclerAdapter(getActivity(),null);
        mRecycler.setAdapter(adapter);
    }
}
