package com.example.phone.zhibotv.fragments;

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
    private static final int RESULT_OK = 101;
    private LinearLayout mLinearLayout;
    private  LinearLayout.LayoutParams params;
    private ImageView mSwip;
    private ImageView mFocus;
    private TextView mText;
    private  View coupon_home_ad_item;
    private Button mBtn;
    private boolean isClick;

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
                    public void onResponse(BigGuanZhuModel response, int id) {
                        for (int i = 0; i < response.getData().getHosts().size(); i++) {
                            coupon_home_ad_item= LayoutInflater.from(getActivity()).inflate(R.layout.item_scroll_guanzhu, null);
                            coupon_home_ad_item.setLayoutParams(params);
                            ImageView mImage =(ImageView) coupon_home_ad_item.findViewById(R.id.image_zhubo_guanzhu);
                            TextView mText =(TextView) coupon_home_ad_item.findViewById(R.id.text_zhubo_guanzhu);
                            mBtn = ((Button) coupon_home_ad_item.findViewById(R.id.btn_guanzhu));
                            mText.setText(response.getData().getHosts().get(i).getName());
                            Picasso.with(getActivity()).load(UrlUtils.IMAGE_BASE_URL+response.getData().getHosts().get(i).getIcon())
                                    .transform(new CropCircleTransformation())
                                    .into(mImage);
                            mLinearLayout.addView(coupon_home_ad_item);
                        }
                    }
                });
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            final View child = mLinearLayout.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: "+mLinearLayout.getChildCount());
                    Log.e(TAG, "onClick: "+child );
                }
            });
        }
    }

    private void initView() {

        mSwip = ((ImageView) inflate.findViewById(R.id.guanzhu_saoyisao));
        mSwip.setOnClickListener(this);

        mFocus = ((ImageView) inflate.findViewById(R.id.guanzhu_search));
        mFocus.setOnClickListener(this);

        //mText= (TextView) inflate.findViewById(R.id.text_swip_result);

        mLinearLayout = (LinearLayout) inflate.findViewById(R.id.lilayout_scroll);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.leftMargin = 10;
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
            case R.id.btn_guanzhu:
                mBtn.setText("已关注");
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == QR_REQUEST_CODE) {
                String extra = data.getStringExtra(CaptureActivity.RESULT);
                Intent intent = new Intent(getActivity(), SwipActivity.class);
                intent.putExtra("result",extra);
                startActivity(intent);
            }
        }

    }
}
