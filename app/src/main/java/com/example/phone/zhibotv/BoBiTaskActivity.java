package com.example.phone.zhibotv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BoBiTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private Button btn,btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bo_bi_task);
        initView();
    }
    private void initView() {
        mBack = (ImageView) findViewById(R.id.search_back);
        mBack.setOnClickListener(this);
        btn=(Button) findViewById(R.id.btn_lingqu);
        btn2=(Button) findViewById(R.id.btn_lingqu2);
        btn3=(Button) findViewById(R.id.btn_lingqu3);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                this.finish();
                break;
            case R.id.btn_lingqu:
                btn.setText("已领取");
                break;
            case R.id.btn_lingqu2:
                btn2.setText("已领取");
                break;
            case R.id.btn_lingqu3:
                btn3.setText("已领取");
                break;
        }



    }
}
