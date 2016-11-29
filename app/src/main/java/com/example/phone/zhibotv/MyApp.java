package com.example.phone.zhibotv;

import android.app.Application;
import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.OkHttpClient;


/**
 * Created by Administrator on 2016-11-28.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient client = new OkHttpClient.Builder().build();
        OkHttpUtils.initClient(client);
        Picasso picasso = new Picasso.Builder(this)
                .loggingEnabled(true)
                // RGB565 可以配置全局的图片加载质量
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .build();
        Picasso.setSingletonInstance(picasso);
    }
}
