package com.example.phone.zhibotv.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.phone.zhibotv.MainActivity;
import com.example.phone.zhibotv.R;

/**
 * Created by Administrator on 2016/11/17.
 */
public class LoadFourFragment extends Fragment implements View.OnClickListener {
    private View inflate;
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.loadfourfragment,container,false);
        return inflate;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    private void initView() {

        button = ((Button) inflate.findViewById(R.id.daohang_btn));
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        SharedPreferences preferences = getActivity().getSharedPreferences("load_medicine", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoad", false);
        editor.commit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
