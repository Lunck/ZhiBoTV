package com.example.phone.zhibotv.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.phone.zhibotv.R;
import com.example.phone.zhibotv.adapters.ZimuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/12/2.
 */
public class TeachFragmenttwo extends Fragment implements View.OnClickListener,Handler.Callback {
    private static final String TAG = TeachFragmenttwo.class.getSimpleName();
    private  View layout;
    private Button button;
    private LinearLayout ll;
    private EditText edt;
    private ListView mListview;
    private List<String >list=new ArrayList<>();
    private ZimuAdapter adapter;
    private Handler handler=new Handler(this);
  private int current=0;
    private List<String >data=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       layout = inflater.inflate(R.layout.fragment_teach_two, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       /* TextView textView = (TextView) layout.findViewById(R.id.teach_fragment_content);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String content = bundle.getString("content");
            textView.setText(content);
        }else{
            textView.setText("预留页面");
        }*/
        return layout;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        button = (Button) layout.findViewById(R.id.two_btn);
        ll = (LinearLayout) layout.findViewById(R.id.line);
        edt = ((EditText) layout.findViewById(R.id.two_edt));
        mListview = ((ListView) layout.findViewById(R.id.two_lv));
        button.setOnClickListener(this);
        String[]wenzi=getResources().getStringArray(R.array.zimu);
        for (String str:wenzi) {
            list.add(str);
        }
        adapter = new ZimuAdapter(getActivity(),null);
        mListview.setAdapter(adapter);
        setupView();
    }

    private void setupView() {
        handler.sendEmptyMessage(0x100);
    }

    @Override
    public void onClick(View v) {
        String wo=edt.getText().toString();
        data.add(wo);
        adapter.updataRes(data);
        edt.setText("");


    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0x100:
                Log.e(TAG, "handleMessage: "+list.size() );
              data.add(list.get(current %list.size()));
                current++;
                Log.e(TAG, "handleMessage: "+data.size() );
                adapter.updataRes(data);
                handler.sendEmptyMessageDelayed(0x100,3000);
                break;
        }
        return false;
    }
}