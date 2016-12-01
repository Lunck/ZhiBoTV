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

public class ZhuBoActivity extends AppCompatActivity implements View.OnClickListener {

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
               /* PopupMenu popupMenu=new PopupMenu(this,mZhuboqingxi);
                this.getMenuInflater().inflate(R.menu.qingxi,popupMenu.getMenu());
                 popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                     @Override
                     public boolean onMenuItemClick(MenuItem item) {
                         switch (item.getItemId()) {
                             case R.id.biaoqing:
                                 break;
                             case R.id.gaoqing:
                                 break;
                         }
                         return false;
                     }
                 });
                popupMenu.show();*/
                popupWindow.showAtLocation(contentview, Gravity.TOP|Gravity.RIGHT,-400,130);
                break;
        }
    }

}
