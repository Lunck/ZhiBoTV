package com.example.phone.zhibotv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhuBoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mZhubotv;
    private ImageView mZhuboqingxi;
    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_bo);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.zhubo_back);
        mZhubotv = (TextView) findViewById(R.id.zhubo_title);
        mZhuboqingxi = (ImageView) findViewById(R.id.zhubo_qingxi);
        mWebview = (WebView) findViewById(R.id.zhubo_webview);
        Intent intent=getIntent();
        if ("0".equals(intent.getStringExtra("status"))) {
            mZhubotv.setText("录播:"+intent.getStringExtra("title"));
        }else {
            mZhubotv.setText("直播:"+intent.getStringExtra("title"));
        }
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.loadUrl("http://www.zhibo.tv/"+intent.getStringExtra("roomid"));
        mBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhubo_back:
                 finish();
                break;
            case R.id.zhubo_qingxi:

                break;
        }

    }
}
