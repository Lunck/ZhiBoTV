package com.example.phone.zhibotv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phone.zhibotv.adapters.DbAdapter;
import com.example.phone.zhibotv.model.History;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,DbAdapter.OnEveryitemClick {
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
    private LinearLayout mAlldelete;
    private ImageView mDelete;
    private  AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        setupView();
        intitDialog();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.search_lv);
        mBack = (ImageView) findViewById(R.id.search_back);
        msearchEd = (EditText) findViewById(R.id.search_ed);
        mSearch = (ImageView) findViewById(R.id.search_search);
        empty = (TextView) findViewById(R.id.search_empty);
        mAlldelete = (LinearLayout) findViewById(R.id.delete_history);
        mDelete = (ImageView) findViewById(R.id.delete_data);
        mDelete.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mBack.setOnClickListener(this);
        adaoter = new DbAdapter(this,null);
        mListView.setAdapter(adaoter);
        adaoter.setEveryitemClick(this);
       // mListView.setOnItemClickListener(this);
    }

    private void setupView() {
        DbManager db=x.getDb(config);
        try {
         List<History> data = db.selector(History.class).findAll();
            if (data!=null&&data.size()!=0) {
                mAlldelete.setVisibility(View.VISIBLE);
                adaoter.updataRes(data);

            }else {
                mAlldelete.setVisibility(View.GONE);
                mListView.setEmptyView(empty);
                adaoter.updataRes(data);

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
                break;
            case R.id.delete_data:
                DbManager db1=x.getDb(config);
                alertDialog.show();

        }
    }
    private void intitDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.kelian);
        builder.setTitle("删除");
        builder.setMessage("你确定要全部删除吗？？");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbManager db = x.getDb(config);
                try {
                    List<History> all = db.selector(History.class).findAll();
                    for (History date:all) {
                        db.delete(date);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }

                setupView();

            }

        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog=builder.create();
    }
    @Override
    protected void onResume() {
        super.onResume();
         setupView();
    }
/* @Override
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

    }*/

    @Override
    public void sendtext(int id) {
        Log.e(TAG, "onItemClick: " );
        DbManager db=x.getDb(config);
        try {
            List<History>data= db.selector(History.class).where("id","=",id).findAll();
            msearchEd.setText(data.get(0).getRoomid());
            Log.e(TAG, "onItemClick: " );
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void senddelete(int id) {
        DbManager db=x.getDb(config);
        WhereBuilder builder=WhereBuilder.b("id","=",id);
        try {
            db.delete(History.class,builder);
        } catch (DbException e) {
            e.printStackTrace();
        }
        setupView();
    }
}
