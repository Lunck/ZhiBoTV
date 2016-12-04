package com.example.phone.zhibotv;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import com.example.phone.zhibotv.adapters.TeachViewPagerAdapter;
import com.example.phone.zhibotv.fragments.TeachFragment;
import com.example.phone.zhibotv.fragments.TeachFragmentthree;
import com.example.phone.zhibotv.fragments.TeachFragmenttwo;

import java.util.ArrayList;
import java.util.List;

public class ZhuBoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ZhuBoActivity.class.getSimpleName();
    private ImageView mBack;
    private TextView mZhubotv;
    private ImageView mZhuboqingxi;
    private WebView mWebview;
    private View contentview;
    private PopupWindow popupWindow;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TeachViewPagerAdapter adapter;
         private  Intent intent;
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
         intent=getIntent();
        if ("0".equals(intent.getStringExtra("status"))) {
            mZhubotv.setText("录播:"+intent.getStringExtra("title"));
        }else {
            mZhubotv.setText("直播:"+intent.getStringExtra("title"));
        }
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.loadUrl("http://www.zhibo.tv/"+intent.getStringExtra("roomid"));
        mTabLayout = (TabLayout) findViewById(R.id.tab_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.vp_viewpager);
        adapter = new TeachViewPagerAdapter(getSupportFragmentManager(),getData());
        mViewPager.setAdapter(adapter);
           TabLayout.Tab newTab = mTabLayout.newTab();
           newTab.setText("主播");
        mTabLayout.addTab(newTab);
           TabLayout.Tab newTab2 = mTabLayout.newTab();
           newTab2.setText("聊天");
        mTabLayout.addTab(newTab2);
           TabLayout.Tab newTab3 = mTabLayout.newTab();
           newTab3.setText("排行");
            mTabLayout.addTab(newTab3);
        // 添加viewPager滚动监听
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        // 添加Tab选中的监听
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
       mBack.setOnClickListener(this);
        mZhuboqingxi.setOnClickListener(this);
        initPopu();
    }
    private List<Fragment> getData() {
        List<Fragment> data = new ArrayList<>();
        TeachFragment fragment = new TeachFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomid",intent.getStringExtra("roomid"));
        bundle.putString("picurl",intent.getStringExtra("picurl"));
        Log.e(TAG, "getData: "+ bundle.getString("picurl"));
        bundle.putString("nickname",intent.getStringExtra("nickname"));
        Log.e(TAG, "getData: "+ bundle.getString("nickname"));
        fragment.setArguments(bundle);
        data.add(fragment);
        TeachFragmenttwo fragmenttwo = new TeachFragmenttwo();
        data.add(fragmenttwo);
        TeachFragmentthree fragmentthrenew=new TeachFragmentthree();
        Bundle bundle2 = new Bundle();
        bundle2.putString("uid",intent.getStringExtra("uid"));
        Log.e(TAG, "getData: "+intent.getStringExtra("uid") );
        fragmentthrenew.setArguments(bundle2);
        data.add(fragmentthrenew);
        return data;
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
