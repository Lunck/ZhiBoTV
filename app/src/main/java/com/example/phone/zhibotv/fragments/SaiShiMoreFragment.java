package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.SaiShiMoreAdapter;
import com.example.phone.zhibotv.model.SaiShiTabTitleData;
import com.example.phone.zhibotv.model.SaiShiTabTitleModel;
import com.example.phone.zhibotv.model.ZhuBoData;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-29.
 */
public class SaiShiMoreFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG=SaiShiMoreFragment.class.getSimpleName();
    private ImageView mBack;
    private RecyclerView mRecycler;
    private List<SaiShiTabTitleModel> typeList;
    private SaiShiMoreAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate=inflater.inflate(R.layout.saishi_more_layout,container,false);
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
                        adapter.addRes(typeList);
                    }
                });
    }

    private void initView() {
        mBack = ((ImageView) inflate.findViewById(R.id.saishi_more_back));
        mBack.setOnClickListener(this);
        mRecycler = ((RecyclerView) inflate.findViewById(R.id.saishi_more_recycler));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecycler.setLayoutManager(gridLayoutManager);
        adapter = new SaiShiMoreAdapter(getActivity(),null);
        mRecycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment fragmentByTag = manager.findFragmentByTag(TAG);
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.remove(fragmentByTag);
        fragmentTransaction.commit();
    }
}
