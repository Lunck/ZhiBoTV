package com.example.phone.zhibotv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.phone.zhibotv.utils.UrlUtils;

public class SaiShiWebActivity extends AppCompatActivity {

    private static final String TAG = SaiShiWebActivity.class.getSimpleName();
    private WebView mWeb;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sai_shi_web);
        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        Log.e(TAG, "onCreate: "+url);
        initView();

    }

    private void initView() {
        mWeb = (WebView) findViewById(R.id.saishi_web);
        mWeb.setWebViewClient(new WebViewClient());
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        mWeb.loadUrl(url);
    }
}
