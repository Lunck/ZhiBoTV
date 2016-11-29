package com.example.phone.zhibotv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private ImageView mSearch;
    private EditText msearchEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.search_back);
        msearchEd = (EditText) findViewById(R.id.search_ed);
        mSearch = (ImageView) findViewById(R.id.search_search);
        mSearch.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                this.finish();
                break;
            case R.id.search_search:

        }
    }
}
