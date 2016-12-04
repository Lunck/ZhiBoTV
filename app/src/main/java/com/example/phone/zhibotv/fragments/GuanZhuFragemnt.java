package com.example.phone.zhibotv.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phone.zhibotv.BaseFragment;
import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.SaoWebActivity;
import com.example.phone.zhibotv.SearchActivity;
import com.example.phone.zhibotv.SwipActivity;
import com.example.phone.zhibotv.model.BigGuanZhuModel;
import com.example.phone.zhibotv.utils.UrlUtils;
import com.google.gson.Gson;
import com.rock.qrcodelibrary.CaptureActivity;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016-11-26.
 */
public class GuanZhuFragemnt extends BaseFragment implements View.OnClickListener {
    public static final String TAG=GuanZhuFragemnt.class.getSimpleName();
    private static final int QR_REQUEST_CODE = 100;
    private LinearLayout mLinearLayout;
    private  LinearLayout.LayoutParams params;
    private ImageView mSwip;
    private ImageView mFocus;
    private TextView mText;
    private  View coupon_home_ad_item;
    private boolean isClick;
    private  LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.guanzhu_layout, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setupView();

    }

    private void setupView() {
        OkHttpUtils.get().url(UrlUtils.GUANZHU_URL).build()
                .execute(new Callback<BigGuanZhuModel>() {
                    @Override
                    public BigGuanZhuModel parseNetworkResponse(Response response, int id) throws Exception {
                        String result = response.body().string();
                        Gson gson = new Gson();
                        return gson.fromJson(result, BigGuanZhuModel.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(final BigGuanZhuModel response, int id) {
                        for (int i = 0; i < response.getData().getHosts().size(); i++) {
                            coupon_home_ad_item= LayoutInflater.from(getActivity()).inflate(R.layout.item_scroll_guanzhu, null);
                            coupon_home_ad_item.setLayoutParams(params);
                            linearLayout= (LinearLayout) coupon_home_ad_item.findViewById(R.id.item_scroll_ll);
                            ImageView mImage =(ImageView) coupon_home_ad_item.findViewById(R.id.image_zhubo_guanzhu);
                            TextView mText =(TextView) coupon_home_ad_item.findViewById(R.id.text_zhubo_guanzhu);
                            final Button mBtn = ((Button) coupon_home_ad_item.findViewById(R.id.btn_guanzhu));
                            mBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e(TAG, "onClick: 我是Button" );
                                    mBtn.setText("已关注");
                                }
                            });
                            mText.setText(response.getData().getHosts().get(i).getName());
                            Picasso.with(getActivity()).load(UrlUtils.IMAGE_BASE_URL+response.getData().getHosts().get(i).getIcon())
                                    .transform(new CropCircleTransformation())
                                    .into(mImage);
                            mLinearLayout.addView(coupon_home_ad_item);
                           final int finalI = i;
                            linearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getActivity(), SwipActivity.class);
                                    intent.putExtra("roomNum",response.getData().getHosts().get(finalI).getRoomnum());
                                    //intent.putExtra("name",response.getData().getHosts().get(finalI).getName());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
    }
    /*for (int i = 0; i < linearLayout.getChildCount(); i++) {
        final View child = linearLayout.getChildAt(i);
        final int finalI = i;
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + linearLayout.getChildCount());
                Log.e(TAG, "onClick: " + linearLayout.getChildAt(finalI));
            }
        });
    }*/
    private void initView() {

        mSwip = ((ImageView) inflate.findViewById(R.id.guanzhu_saoyisao));
        mSwip.setOnClickListener(this);

        mFocus = ((ImageView) inflate.findViewById(R.id.guanzhu_search));
        mFocus.setOnClickListener(this);

        //mText= (TextView) inflate.findViewById(R.id.text_swip_result);
        mLinearLayout = (LinearLayout) inflate.findViewById(R.id.lilayout_scroll);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.leftMargin = 20;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guanzhu_saoyisao:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent,QR_REQUEST_CODE);
                break;
            case R.id.guanzhu_search:
                Intent intentSearch=new Intent(getActivity(),SearchActivity.class);
                startActivity(intentSearch);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult:  我走到了这里 1" );
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == QR_REQUEST_CODE) {
                String extra = data.getStringExtra(CaptureActivity.RESULT);
                Intent intent = new Intent(getActivity(), SaoWebActivity.class);
                intent.putExtra("result",extra);
                startActivity(intent);
            /*    SwipEvent event = new SwipEvent(110);
                event.setMsg(extra);
                Log.e(TAG, "onActivityResult: 我走到了这里" );
                EventBus.getDefault().postSticky(event);*/
            }
        }
    }
    /*@Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onEvent(SwipEvent event){
        switch (event.what) {
            case 110:
                Intent intent = new Intent(getActivity(), SaoWebActivity.class);
                intent.putExtra("result",event.getMsg());
                Log.e(TAG, "onEvent: "+event.getMsg() );
                startActivity(intent);
                break;
        }

    }*/
   /* @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }*/
}
