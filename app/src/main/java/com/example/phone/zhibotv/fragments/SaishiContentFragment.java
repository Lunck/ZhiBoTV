package com.example.phone.zhibotv.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.DengLuActivity;
import com.example.phone.zhibotv.MainActivity;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.SaiShiWebActivity;
import com.example.phone.zhibotv.ZhuCeActivity;
import com.example.phone.zhibotv.adapters.HudongAdapter;
import com.example.phone.zhibotv.adapters.SaiShiContentAdapter;
import com.example.phone.zhibotv.adapters.ZhiBoAdapter;
import com.example.phone.zhibotv.event.MessageEvent;
import com.example.phone.zhibotv.events.SaiShiEvent;
import com.example.phone.zhibotv.model.SaiShiContenModel;
import com.example.phone.zhibotv.model.SaiShiContentData;
import com.example.phone.zhibotv.model.SaiShiContentModelAnchors;
import com.example.phone.zhibotv.model.SaiShiContentModelSourceList;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.example.phone.zhibotv.widget.SelectPopWindow;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaishiContentFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener, PlatformActionListener, PullToRefreshBase.OnRefreshListener2{
    public static final String TAG=SaishiContentFragment.class.getSimpleName();
    private PullToRefreshListView refreshListView;
    private ListView mListView;
    private SaiShiContentAdapter contentAdapter;
    private String url="";
    private List<SaiShiContenModel> data;
    private View popwindow;
    private PopupWindow popupWindow;
    private LinearLayout mHuDongLayout;
    private LinearLayout mZhiBoLayout;
    private ListView mHuDongList;
    private ListView mZhiBoList;
    private TextView mBack;
    private SelectPopWindow menuWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate=inflater.inflate(R.layout.saishi_content_layout,container,false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data=new ArrayList<>();
        initView();
    }

    private void setupView(String url) {
        Log.e(TAG, "setupView: "+url );
        //data.clear();
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: "+response );
                        Gson gson = new Gson();
                        SaiShiContentData contentData = gson.fromJson(response, SaiShiContentData.class);
                        Log.e(TAG, "onResponse: "+contentData.getData().getScheduleList().toString());
                        contentAdapter.addRes(contentData.getData().getScheduleList());
                        data=contentData.getData().getScheduleList();
                        refreshListView.onRefreshComplete();
                    }
                });

    }

    private void initView() {
        refreshListView = ((PullToRefreshListView) inflate.findViewById(R.id.saishi_content_listview));
        refreshListView.setOnRefreshListener(this);
        mListView = refreshListView.getRefreshableView();
        contentAdapter = new SaiShiContentAdapter(getActivity(),null,R.layout.saishi_two_item,R.layout.saishi_one_item);
        mListView.setAdapter(contentAdapter);
        View emptyView = inflate.findViewById(R.id.saishi_empty_view);
        mListView.setEmptyView(emptyView);
        refreshListView.setOnItemClickListener(this);
        //refreshListView.setMode(PullToRefreshBase.Mode.DISABLED);

        popwindow = LayoutInflater.from(getContext()).inflate(R.layout.saishi_popwindow_layout,null);
        mHuDongLayout = ((LinearLayout) popwindow.findViewById(R.id.popwndow_hudong));
        mZhiBoLayout = ((LinearLayout) popwindow.findViewById(R.id.popwndow_zhibo));
        mHuDongList = ((ListView) popwindow.findViewById(R.id.popwndow_hudong_listview));
        mZhiBoList = ((ListView) popwindow.findViewById(R.id.popwndow_zhibo_listview));
        mBack = ((TextView) popwindow.findViewById(R.id.popwndow_zhibo_text));
        mBack.setOnClickListener(this);

        popupWindow = new PopupWindow(popwindow, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());


    }
    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN ,sticky = true)
    public void onEvent(SaiShiEvent event){
        if (event.what==100) {
            url=event.getMsg();
            ConnectivityManager systemService = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (systemService.getActiveNetworkInfo()!=null) {
                setupView(url);
                Log.e(TAG, "onEvent: 网络连接正常");
            }else {
                Toast.makeText(getContext(), "当前没有网络，请检查网络连接！", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onEvent: 当前没有网络，请检查网络连接！");
            }

        }
        if (event.what==120){
            mBack.setText("预约");
        }
        if (event.what==130){
            mBack.setText("取消");
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemClick: " +view.getId()+"-------");
        //Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();

        final List<SaiShiContentModelAnchors> anchorsList = data.get(position-1).getAnchors();
        if (anchorsList!=null&&anchorsList.size()!=0) {
            mHuDongLayout.setVisibility(View.VISIBLE);
            HudongAdapter hudongAdapter = new HudongAdapter(getContext(),anchorsList,R.layout.hudong_item_layout);
            mHuDongList.setAdapter(hudongAdapter);
            mHuDongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popupWindow.dismiss();
                    Intent intent = new Intent(getActivity(), SaiShiWebActivity.class);
                    intent.putExtra("url", UrlUtils.IMAGE_BASE_URL+anchorsList.get(position).getRoomid());
                    getActivity().startActivity(intent);
                }
            });
        }
        final List<SaiShiContentModelSourceList> sourceList = data.get(position-1).getSourceList();
        if (sourceList!=null&&sourceList.size()!=0) {
            mZhiBoLayout.setVisibility(View.VISIBLE);
            ZhiBoAdapter zhiboAdapter = new ZhiBoAdapter(getContext(),sourceList,R.layout.zhibo_itme_layout);
            mZhiBoList.setAdapter(zhiboAdapter);
            mZhiBoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popupWindow.dismiss();
                    Intent intent = new Intent(getActivity(), SaiShiWebActivity.class);
                    intent.putExtra("url",UrlUtils.IMAGE_BASE_URL+sourceList.get(position).getRoomid());
                    getActivity().startActivity(intent);
                }
            });
        }
        popupWindow.showAtLocation(popwindow, Gravity.BOTTOM,0,0);


    }

    @Override
    public void onClick(View v) {
        TextView v1 = (TextView) v;
        if (v1.getText().equals("取消")) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
        if (v1.getText().equals("预约")) {
            Toast.makeText(getActivity(), "请先登录账号！", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
            onSelectPopWindow();
        }

    }
    public void onSelectPopWindow() {
        FrameLayout inflate = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
        menuWindow = new SelectPopWindow(getActivity(), itemsOnClick);
        menuWindow.showAtLocation(inflate.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.image_btn_qq:
                    login(QQ.NAME);
                    break;
                case R.id.image_btn_weichat:
                    login(Wechat.NAME);
                    break;
                case R.id.image_btn_sina:
                    login(SinaWeibo.NAME);
                    break;
                case R.id.btn_zhuce:
                    Intent intent2 = new Intent(getActivity(), ZhuCeActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.btn_denglu:
                    Intent intent = new Intent(getActivity(), DengLuActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    private void login(String name) {
        ShareSDK.initSDK(getActivity());
        Platform platform = ShareSDK.getPlatform(getActivity(), name);
        platform.setPlatformActionListener(this);
        platform.authorize();
        platform.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        MessageEvent messageEvent = new MessageEvent(platform.getDb().getUserIcon(),platform.getDb().getUserName());
        Log.e(TAG, "onComplete: "+messageEvent );
        EventBus.getDefault().post(messageEvent);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        setupView(url);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
}
