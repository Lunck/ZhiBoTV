package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiOneFragment extends BaseFragment {
    public static final String TAG=SaiShiOneFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         inflate=inflater.inflate(R.layout.saishi_one_layout,container,false);
        return inflate;
    }
}
