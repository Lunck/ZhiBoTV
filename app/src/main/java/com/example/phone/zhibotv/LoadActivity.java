package com.example.phone.zhibotv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.phone.zhibotv.adapters.LoadAdapter;
import com.example.phone.zhibotv.fragments.LoadFourFragment;
import com.example.phone.zhibotv.fragments.LoadOneFragment;
import com.example.phone.zhibotv.fragments.LoadThreeFragment;
import com.example.phone.zhibotv.fragments.LoadTwoFragment;

import java.util.ArrayList;
import java.util.List;

public class LoadActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private LoadAdapter adapter;
    private List<Fragment> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        initView();
    }
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.daohang_viewpager);
        adapter = new LoadAdapter(getSupportFragmentManager(), getData());
        mViewPager.setAdapter(adapter);
    }
    public List<Fragment> getData() {
        List<Fragment> data=new ArrayList<>();
        data.add(new LoadOneFragment());
        data.add(new LoadTwoFragment());
        data.add(new LoadThreeFragment());
        data.add(new LoadFourFragment());
        return data;
    }
}
