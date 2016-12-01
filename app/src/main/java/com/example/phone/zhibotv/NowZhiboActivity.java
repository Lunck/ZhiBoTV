package com.example.phone.zhibotv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.phone.zhibotv.adapters.NowAdapter;
import com.example.phone.zhibotv.model.NowModel;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;

public class NowZhiboActivity extends AppCompatActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2 {

    public static final String TEATHED_URL1=" http://www.zhibo.tv/app/index/loadmore?page=";
    public static final String TEATHED_URL2="&id=";
    public static final String TEATHED_URL3="&equipment=1&size=20";
    private static final String TAG = NowAdapter.class.getSimpleName();
    private ImageView mBack;
    private ListView mListview;
    private int id;
    private int p=1;
    private NowAdapter adapter;
    private PullToRefreshListView mRefresh;
    private ILoadingLayout loadingLayoutProxy;
    private SimpleDateFormat format;
    private Date date;
    private String dateStr;
    private int totalpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_zhibo);
        initView();
        setupView(State.DOWN,p);
    }
    private void setupView(final State state,int p) {
        if (p==totalpage){
            mRefresh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
        Log.e(TAG, "setupView: "+TEATHED_URL1+p+TEATHED_URL2+id+TEATHED_URL3 );

        OkHttpUtils.get()
                .url(TEATHED_URL1+p+TEATHED_URL2+id+TEATHED_URL3)
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson=new Gson();
                        Log.e(TAG, "onResponse: NowAdapter"+response );
                        NowModel nowModel=gson.fromJson(response,NowModel.class);
                        totalpage = nowModel.getData().getTotalpage();
                        switch (state) {
                            case DOWN:
                                adapter.updataRes(nowModel.getData().getData());
                                break;
                            case UP:
                                adapter.addRes(nowModel.getData().getData());

                                break;
                        }
                                mRefresh.onRefreshComplete();
                        }

                });
    }

    private void initView() {
        Intent intent=getIntent();
        id = intent.getIntExtra("id",0);
        mBack = (ImageView) findViewById(R.id.now_back);
        mRefresh = (PullToRefreshListView) findViewById(R.id.now_lv);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        loadingLayoutProxy=mRefresh.getLoadingLayoutProxy(true,false);
        loadingLayoutProxy.setPullLabel("继续下拉刷新");
        loadingLayoutProxy.setReleaseLabel("释放刷新");
        loadingLayoutProxy.setRefreshingLabel("正在刷新");
        ILoadingLayout footer = mRefresh.getLoadingLayoutProxy(false, true);
        footer.setReleaseLabel("正在加载...");
        String  pattern="yyyy年MM月dd日HH:mm:ss";
        format = new SimpleDateFormat(pattern);
        mListview =mRefresh.getRefreshableView();
        adapter = new NowAdapter(this,null);
        mListview.setAdapter(adapter);
        mBack.setOnClickListener(this);

    }
  enum State{
        DOWN,UP
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.now_back:
                finish();
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        date = new Date();
        dateStr = format.format(date);
        loadingLayoutProxy.setLastUpdatedLabel("上次刷新:"+dateStr);
        p=1;
        setupView(State.DOWN,p);
    }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                p+=1;
                setupView(State.UP,p);
        }


}
