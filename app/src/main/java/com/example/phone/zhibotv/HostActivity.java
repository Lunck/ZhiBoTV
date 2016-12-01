package com.example.phone.zhibotv;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.phone.zhibotv.R;

public class HostActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mZhubotv;
    private ImageView mZhuboqingxi;
    private WebView mWebview;
    private View contentview;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_bo);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.qingxi,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.biaoqing:
                break;
            case R.id.gaoqing:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.zhubo_back);
        mZhubotv = (TextView) findViewById(R.id.zhubo_title);
        mZhuboqingxi = (ImageView) findViewById(R.id.zhubo_qingxi);
        mWebview = (WebView) findViewById(R.id.zhubo_webview);
        Intent intent=getIntent();
        mZhubotv.setText(intent.getStringExtra("title"));
        mZhubotv.setText(intent.getStringExtra("title"));
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.loadUrl("http://www.zhibo.tv/"+intent.getStringExtra("roomid"));
        mBack.setOnClickListener(this);
        mZhuboqingxi.setOnClickListener(this);
        initPopu();
    }
    private void initPopu() {
        contentview = LayoutInflater.from(this).inflate(R.layout.zhubopopu,null);
        popupWindow = new PopupWindow(contentview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        TextView biaoqing = (TextView) contentview.findViewById(R.id.biaoqing);
        TextView gaoqing = (TextView) contentview.findViewById(R.id.gaoqing);
        biaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        gaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhubo_back:
                finish();
                break;
            case R.id.zhubo_qingxi:
                /*PopupMenu popupMenu=new PopupMenu(this,v);
                this.getMenuInflater().inflate(R.menu.qingxi,popupMenu.getMenu());
                popupMenu.show();*/
                popupWindow.showAtLocation(contentview, Gravity.TOP|Gravity.RIGHT,-300,130);
                break;
        }
    }
}
