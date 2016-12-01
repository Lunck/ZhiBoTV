package com.example.phone.zhibotv;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phone.zhibotv.model.Chazhao;
import com.example.phone.zhibotv.model.ShouyeModel;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

public class PEactivity extends AppCompatActivity implements View.OnClickListener {

    private static final String THEADER_URL = "http://www.zhibo.tv/app/index/home";
    private ImageView mBack;
    private TextView mZhubotv;
    private ImageView mZhuboqingxi;
    private WebView mWebview;
    private View contentview;
    private PopupWindow popupWindow;
    private String roomid;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x100:
                    finish();
                    break;
            }
        }
    };
    private TextView meikanbo;
    public DbManager.DaoConfig config=new DbManager.DaoConfig()
            .setDbName("historys.db")
            .setAllowTransaction(true)
            .setDbDir(Environment.getExternalStorageDirectory())
            .setDbVersion(1);

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
        meikanbo = (TextView) findViewById(R.id.meikanbo);
        Intent intent=getIntent();
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient());
        roomid = intent.getStringExtra("roomid");
        mBack.setOnClickListener(this);
        mZhuboqingxi.setOnClickListener(this);
        setupView();
        initPopu();

    }

    private void setupView() {
        DbManager db= x.getDb(config);
        try {
            List<Chazhao>data=db.selector(Chazhao.class).where("roomid","=",roomid).or("nickname","=",roomid).findAll();
            if (data!=null&&data.size()!=0){
                mWebview.loadUrl("http://www.zhibo.tv/"+data.get(0).getRoomid());
                mZhubotv.setText(data.get(0).getTitle());
            }else {
                meikanbo.setVisibility(View.VISIBLE);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }


    }
    /*private void setupView() {
        OkHttpUtils.get()
                .url(THEADER_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                    Gson gson=new Gson();
                        ShouyeModel shouyeModel = gson.fromJson(response, ShouyeModel.class);
                        for (int i = 0; i < shouyeModel.getData().getCategory().size(); i++) {
                            for (int j = 0; j < shouyeModel.getData().getCategory().get(i).getData().size(); j++) {
                                Pattern p = Pattern.compile("[0-9]+");
                                Matcher m = p.matcher(roomid);
                                if(m.matches() ){
                                    if (roomid.equals(shouyeModel.getData().getCategory().get(i).getData().get(j).getRoomId())) {
                                        roomid=shouyeModel.getData().getCategory().get(i).getData().get(j).getRoomId();
                                        mWebview.loadUrl("http://www.zhibo.tv/"+roomid);
                                        mZhubotv.setText(shouyeModel.getData().getCategory().get(i).getData().get(j).getTitle());
                                    }else {
                                        meikanbo.setVisibility(View.VISIBLE);
                                    }
                                }
                                p=Pattern.compile("[\u4e00-\u9fa5]+");
                                m=p.matcher(roomid);
                                if(m.matches()){
                                    if (roomid.equals(shouyeModel.getData().getCategory().get(i).getData().get(j).getNickname())) {
                                        meikanbo.setVisibility(View.GONE);
                                        roomid=shouyeModel.getData().getCategory().get(i).getData().get(j).getRoomId();
                                        mWebview.loadUrl("http://www.zhibo.tv/"+roomid);
                                        mZhubotv.setText(shouyeModel.getData().getCategory().get(i).getData().get(j).getTitle());
                                    }else {
                                        meikanbo.setVisibility(View.VISIBLE);
                                    }

                                }
                            }
                        }
                        for (int i = 0; i < shouyeModel.getData().getHots().size(); i++) {
                                Pattern p = Pattern.compile("[0-9]*");
                                Matcher m = p.matcher(roomid);
                                if(m.matches() ){
                                    if (roomid.equals(shouyeModel.getData().getHots().get(i).getRoomId())) {
                                        meikanbo.setVisibility(View.GONE);
                                        roomid=shouyeModel.getData().getHots().get(i).getRoomId();
                                        mWebview.loadUrl("http://www.zhibo.tv/"+roomid);
                                        mZhubotv.setText(shouyeModel.getData().getHots().get(i).getTitle());
                                    }else {
                                        meikanbo.setVisibility(View.VISIBLE);
                                    }
                                }
                                p=Pattern.compile("[\u4e00-\u9fa5]*");
                                m=p.matcher(roomid);
                                if(m.matches()){
                                    if (roomid.equals(shouyeModel.getData().getHots().get(i).getNickname())) {
                                        meikanbo.setVisibility(View.GONE);
                                        roomid=shouyeModel.getData().getHots().get(i).getRoomId();
                                        mWebview.loadUrl("http://www.zhibo.tv/"+roomid);
                                        mZhubotv.setText(shouyeModel.getData().getHots().get(i).getTitle());
                                    }else {
                                        meikanbo.setVisibility(View.VISIBLE);
                                    }

                                }
                            }



                    }
                });

    }*/

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
                popupWindow.showAtLocation(contentview, Gravity.TOP|Gravity.RIGHT,-500,130);
                break;
        }
    }
}
