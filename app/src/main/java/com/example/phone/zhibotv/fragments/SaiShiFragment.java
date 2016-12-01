package com.example.phone.zhibotv.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.rock.qrcodelibrary.CaptureActivity;

/**
 * Created by Administrator on 2016-11-26.
 */
public class SaiShiFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    public static final String TAG=SaiShiFragment.class.getSimpleName();
    private static final int REQUEST_CODE = 99;
    private RadioGroup mRadiogroup;
    private Fragment showFragment;
    private ImageView mSearch;
    private ImageView mSaoYiSao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.saishi_layout, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mRadiogroup = ((RadioGroup) inflate.findViewById(R.id.saishi_radiogroup));
        mRadiogroup.setOnCheckedChangeListener(this);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        showFragment = new SaiShiOneFragment();
        transaction.add(R.id.saishi_fragment,showFragment,SaiShiOneFragment.TAG);
        transaction.commit();

        mSearch = ((ImageView) inflate.findViewById(R.id.saishi_search));
        mSaoYiSao = ((ImageView) inflate.findViewById(R.id.saishi_saoyisao));
        mSearch.setOnClickListener(this);
        mSaoYiSao.setOnClickListener(this);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.saishi_btn:
                SwipFragment(SaiShiOneFragment.class,SaiShiOneFragment.TAG);
                break;

            case R.id.zhubo_btn:
                SwipFragment(SaiShiTwoFragment.class,SaiShiTwoFragment.TAG);
                break;
        }
    }

    private void SwipFragment(Class<? extends Fragment> fragmentClass, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(showFragment);
        showFragment=fragmentManager.findFragmentByTag(tag);
        if (showFragment!=null) {
            transaction.show(showFragment);
        }else {
            try {
                showFragment= fragmentClass.newInstance();
                transaction.add(R.id.saishi_fragment,showFragment,tag);
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        transaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saishi_search:

                break;

            case R.id.saishi_saoyisao:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                getActivity().startActivityForResult(intent,REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE&&resultCode== Activity.RESULT_OK) {
            String extra = data.getStringExtra(CaptureActivity.RESULT);
        }
    }
}
