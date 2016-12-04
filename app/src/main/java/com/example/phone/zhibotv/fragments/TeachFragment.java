package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.FragmentOne;
import com.example.phone.zhibotv.model.Self;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import okhttp3.Call;

/**
 * Created by my on 2016/12/2.
 */
public class TeachFragment extends Fragment {

    private static final String THREAD_URL1 = "http://www.zhibo.tv/app/user/getvideo/roomId/";
    private static final String THREAD_URL2 = "/size/20/page/1";
    private static final String TAG = TeachFragment.class.getCanonicalName();
    private View layout;
    private ListView mListview;
    private ImageView mImage;
    private TextView mText;
    private TextView mText2;
    private Bundle bunble;
    private FragmentOne adapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_teach, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mListview = (ListView) layout.findViewById(R.id.fragment_one_lv);
        mImage = (ImageView) layout.findViewById(R.id.fragment_one_img);
        mText = (TextView) layout.findViewById(R.id.fragment_one_tv);
        mText2 = (TextView) layout.findViewById(R.id.fragment_one_tv2);
        bunble = getArguments();
        Log.e(TAG, "initView: "+bunble.getString("picurl") );
        Picasso.with(mImage.getContext())
                .load("http://www.zhibo.tv"+bunble.getString("picurl"))
                .placeholder(R.mipmap.jiazaizhong)
                .transform(new CropCircleTransformation())
                .into(mImage);
        mText.setText(bunble.getString("nickname"));
        mText2.setText("房间ID:"+bunble.getString("roomid"));
        adapter = new FragmentOne(getActivity(),null);
        mListview.setAdapter(adapter);
        setupView();
    }

    private void setupView() {
        Log.e(TAG, "setupView: "+THREAD_URL1+bunble.getString("roomid")+THREAD_URL2 );
        OkHttpUtils.get()
                .url(THREAD_URL1+bunble.getString("roomid")+THREAD_URL2)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson=new Gson();
                        Self self=gson.fromJson(response,Self.class);
                        adapter.updataRes(self.getData().getList());
                    }
                });
    }
}