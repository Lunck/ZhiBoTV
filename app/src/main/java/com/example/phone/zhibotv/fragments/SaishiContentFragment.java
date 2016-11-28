package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.SaiShiContentAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaishiContentFragment extends BaseFragment {
    private PullToRefreshListView refreshListView;
    private ListView mListView;

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

    }

    private void initView() {
        refreshListView = ((PullToRefreshListView) inflate.findViewById(R.id.saishi_content_listview));
        mListView = refreshListView.getRefreshableView();
        SaiShiContentAdapter contentAdapter = new SaiShiContentAdapter(getActivity(),null,R.layout.saishi_one_item,R.layout.saishi_two_item);
        mListView.setAdapter(contentAdapter);


    }
}
