package com.example.phone.zhibotv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class SwipActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mBack;
    private WebView mWebView;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swip);
        result = getIntent().getStringExtra("roomNum");
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.zhubo_back);
        mBack.setOnClickListener(this);

        mWebView = (WebView) findViewById(R.id.zhubo_webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("http://www.zhibo.tv/"+result);
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
