package com.example.phone.zhibotv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int START_ACTIVITY = 100;
    private ImageView mImage;
    private int count;
    private TextView mText;
    private boolean isLoad=false;
    private Intent intent;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.what == START_ACTIVITY) {
                mText.setText(count + "");
                if (count==0) {
                    if (isLoad) {
                        intent = new Intent(StartActivity.this, LoadActivity.class);
                    } else {
                        intent = new Intent(StartActivity.this, MainActivity.class);
                    }
                    startActivity(intent);
                    StartActivity.this.finish();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        count=3;
        SharedPreferences preferences = getSharedPreferences("load_medicine",
                MODE_PRIVATE);
        isLoad = preferences.getBoolean("isLoad", true);
        initView();
        new Thread(new MyRunnable()).start();
    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.image_start);
        mText = (TextView) findViewById(R.id.text_start);

        mImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //mText.setText(count+"");
        if (isLoad) {
            intent = new Intent(StartActivity.this, LoadActivity.class);
        } else {
            intent = new Intent(StartActivity.this, MainActivity.class);
        }
        startActivity(intent);
        StartActivity.this.finish();

    }
    class MyRunnable implements Runnable {
        @Override
        public void run() {
            while(count!=0) {
                SystemClock.sleep(1000);
                count--;
                handler.sendEmptyMessage(START_ACTIVITY);
            }
            }
        }
    }
