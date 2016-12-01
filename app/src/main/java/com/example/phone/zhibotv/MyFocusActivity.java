package com.example.phone.zhibotv;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MyFocusActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageView mBack;
    private SwipeRefreshLayout mRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_focus);
        initView();
    }
    private void initView() {
        mBack = (ImageView) findViewById(R.id.search_back);
        mBack.setOnClickListener(this);

        mRefresh = (SwipeRefreshLayout) findViewById(R.id.focus_refresh);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        mRefresh.setProgressBackgroundColorSchemeResource(R.color.colorPrimaryDark);
        mRefresh.setSize(SwipeRefreshLayout.LARGE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                this.finish();
                break;
        }



    }

    @Override
    public void onRefresh() {

    }
}
