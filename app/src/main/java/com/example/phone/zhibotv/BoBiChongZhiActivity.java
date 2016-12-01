package com.example.phone.zhibotv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BoBiChongZhiActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bo_bi_chong_zhi);
        initView();
    }
    private void initView() {
        mBack = (ImageView) findViewById(R.id.search_back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                this.finish();
                break;
        }



    }
}
