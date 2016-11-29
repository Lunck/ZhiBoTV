package com.example.phone.zhibotv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phone.zhibotv.adapters.RecyclerViewMultAdapter;
import com.example.phone.zhibotv.adapters.ZhuBoContentAdapter;
import com.example.phone.zhibotv.model.ZhuBoContent;
import com.example.phone.zhibotv.model.ZhuBoContentModel;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class SaiShiZhuBoActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewMultAdapter.OnItemClickListener {
    public static final String TAG=SaiShiZhuBoActivity.class.getSimpleName();
    private RecyclerView mRecycler;
    private ZhuBoContentAdapter adapter;
    private  String url="";
    private ImageView mBack;
    private TextView mTitle;
    private LinearLayout mLinearLayout;
    private ImageView mImage;
    private TextView mText;
    private View inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sai_shi_zhu_bo);
        Intent intent = getIntent();
        url=intent.getStringExtra("url");
        initView();
        setupView();
    }

    private void setupView() {
        Log.e(TAG, "setupView: "+ UrlUtils.ZHUBO_CONTENT_HEADER+url+UrlUtils.ZHUBO_CONTENT_FOOTER);
        OkHttpUtils.get()
                .url(UrlUtils.ZHUBO_CONTENT_HEADER+url+UrlUtils.ZHUBO_CONTENT_FOOTER)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        ZhuBoContent zhuBoContent = gson.fromJson(response, ZhuBoContent.class);
                        mTitle.setText(zhuBoContent.getData().getName());
                        if (zhuBoContent.getData().getBanner()!=""&&zhuBoContent.getData().getBanner()!=null) {
                            Picasso.with(SaiShiZhuBoActivity.this).load(UrlUtils.BASE_URL+zhuBoContent.getData().getBanner()).into(mImage);
                            mText.setText(zhuBoContent.getData().getBannerDesc());
                            adapter.setHeaderView(inflate);
                        }
                        adapter.addDatas((ArrayList<ZhuBoContentModel>) zhuBoContent.getData().getData());

                        Log.e(TAG, "onResponse: "+ zhuBoContent.getData().getData().toString());

                    }
                });
    }

    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.saishi_zhubo_content_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecycler.setLayoutManager(layoutManager);
        adapter = new ZhuBoContentAdapter();
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        mBack = (ImageView) findViewById(R.id.saishi_zhubo_content_back);
        mBack.setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.saishi_zhubo_content_biaoti);
       /* mLinearLayout = (LinearLayout) findViewById(R.id.saishi_zhubo_content_header);
        mLinearLayout.setVisibility(View.GONE);*/
        inflate = LayoutInflater.from(this).inflate(R.layout.zhubo_header_layout,null);
        mImage = (ImageView)inflate.findViewById(R.id.saishi_zhubo_content_titleimg);
        mText = ((TextView)inflate.findViewById(R.id.saishi_zhubo_content_jieshao));
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(int position, Object data) {
        String roomId = ((ZhuBoContentModel) data).getRoomId();
        Intent intent = new Intent(this,SaiShiWebActivity.class);
        intent.putExtra("url",UrlUtils.IMAGE_BASE_URL+roomId);
        Log.e(TAG, "onItemClick: "+UrlUtils.IMAGE_BASE_URL+roomId);
        startActivity(intent);
    }
}
