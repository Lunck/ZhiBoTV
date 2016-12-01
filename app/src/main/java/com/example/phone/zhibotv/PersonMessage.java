package com.example.phone.zhibotv;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class PersonMessage extends AppCompatActivity implements View.OnClickListener {

    private static final int TAG = 100;
    private ImageView mImage;
    private String message;
    private String iconMessage;
    private TextView mText;
    private ImageView mBack;
    private TextView mTuiChu;
    private SharedPreferences preferences;
   // private TuichuClickListener listener;

  /*  public PersonMessage(TuichuClickListener listener) {
        this.listener = listener;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_message);
        preferences= getSharedPreferences("pass_on", MODE_PRIVATE);
        message = preferences.getString("name","用户名");
        iconMessage = preferences.getString("icon","头像");
        initView();
    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.peron_icon);
        mText = (TextView) findViewById(R.id.person_name);

        mBack = (ImageView) findViewById(R.id.search_back);
        mBack.setOnClickListener(this);
        mTuiChu = (TextView) findViewById(R.id.person_tuichu);
        mTuiChu.setTag(TAG);
        mTuiChu.setOnClickListener(this);
        mText.setText(message);
        Picasso.with(this).load(iconMessage).transform(new CropCircleTransformation()).into(mImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                this.finish();
                break;
            case R.id.person_tuichu:
                final Integer position = (Integer) v.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("是否确认退出");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  listener.onclickListener(position);
                        SharedPreferences sharePreferences = getSharedPreferences("pass_on", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharePreferences.edit();
                        //edit.clear();
                        /*edit.putString("name","");
                        edit.putString("icon","");*/
                        edit.putBoolean("isDengLu", false);
                        edit.commit();
                        PersonMessage.this.finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
        }
    }

   /* public interface TuichuClickListener{
        void onclickListener(int position);
    }*/
}
