package com.example.phone.zhibotv;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phone.zhibotv.adapters.DbAdapter;
import com.example.phone.zhibotv.model.History;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
   public DbManager.DaoConfig config=new DbManager.DaoConfig()
           .setDbName("historys.db")
           .setAllowTransaction(true)
           .setDbDir(Environment.getExternalStorageDirectory())
           .setDbVersion(1);
    private ImageView mBack;
    private ImageView mSearch;
    private EditText msearchEd;
    private DbAdapter adaoter;
    private ListView mListView;
    private TextView empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        setupView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.search_lv);
        mBack = (ImageView) findViewById(R.id.search_back);
        msearchEd = (EditText) findViewById(R.id.search_ed);
        mSearch = (ImageView) findViewById(R.id.search_search);
        empty = (TextView) findViewById(R.id.search_empty);
        mSearch.setOnClickListener(this);
        mBack.setOnClickListener(this);
        adaoter = new DbAdapter(this,null);
        mListView.setAdapter(adaoter);
        mListView.setOnItemClickListener(this);
    }

    private void setupView() {
        DbManager db=x.getDb(config);
        try {
         List<History> data = db.selector(History.class).findAll();
            if (data!=null&&data.size()!=0) {
                adaoter.updataRes(data);
            }else {
                mListView.setEmptyView(empty);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                this.finish();
                break;
            case R.id.search_search:
                String text = msearchEd.getText().toString();
                Pattern p = Pattern.compile("[0-9]+|[\u4e00-\u9fa5]+");
                Matcher m = p.matcher(text);
                if(m.matches() ) {
                    Intent intent = new Intent(this, PEactivity.class);
                    intent.putExtra("roomid", text);
                    startActivity(intent);
                    msearchEd.setText("");
                } else {
                    Toast.makeText(SearchActivity.this, "你查找的主播没开播或不存在，请重新输入", Toast.LENGTH_SHORT).show();
                    msearchEd.setText("");
                }
                /*p=Pattern.compile("[\u4e00-\u9fa5]*");
                m=p.matcher(text);
                if(m.matches()){
                    Intent intent=new Intent(this,PEactivity.class);
                    intent.putExtra("roomid",text);
                    startActivity(intent);
                }
                p=Pattern.compile("[0-9]*[\u4e00-\u9fa5]|[a-zA-Z]*|[0-9]*[\u4e00-\u9fa5][a-zA-Z]");
                m=p.matcher(text);
                if (m.matches()){
                    Toast.makeText(SearchActivity.this, "你查找的主播没开播或不存在，请重新输入", Toast.LENGTH_SHORT).show();
                    msearchEd.setText("");
                }*/
                History history = new History();
                if (text!=null){
                    history.setRoomid(text);
                }
                DbManager db=x.getDb(config);
                Log.e(TAG, "onClick: "+db );
                try {
                    List<History>data=db.selector(History.class).where("roomid","=",text).findAll();
                    if (data==null||data.size()==0) {
                        Log.e(TAG, "onClick: 哈哈");
                        db.saveOrUpdate(history);
                    }
                } catch (DbException e) {
                    Log.e(TAG, "onClick: "+e );
                    e.printStackTrace();
                }

                setupView();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemClick: " );
       DbManager db=x.getDb(config);
        List<History>data= null;
        try {
            data = db.selector(History.class).findAll();
            msearchEd.setText(data.get(position).getRoomid());
            Log.e(TAG, "onItemClick: " );
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
