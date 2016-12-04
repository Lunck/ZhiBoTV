package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.PanghangAdapter;
import com.example.phone.zhibotv.model.Paihang;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by my on 2016/12/2.
 */
public class NowTestFragment extends Fragment{
    private static final String THREAD_URL = " http://www.zhibo.tv/app/ranking/getranking?uid=";
    private static final String TAG = WeekFragment.class.getSimpleName();
    private View layout;
    private ListView mViewList;
    private PanghangAdapter adapter;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.week_fragment,container,false);
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
        mViewList = ((ListView) layout.findViewById(R.id.week_lv));
        adapter = new PanghangAdapter(getActivity(),null);
        mViewList.setAdapter(adapter);
        setupView();
    }

    private void setupView() {
        Log.e(TAG, "setupView: "+THREAD_URL+uid );
        OkHttpUtils.get()
                .url(THREAD_URL+uid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson=new Gson();
                        Paihang paihang=gson.fromJson(response,Paihang.class);
                        adapter.updataRes(paihang.getRanking().getWeek());

                    }
                });
    }
}
