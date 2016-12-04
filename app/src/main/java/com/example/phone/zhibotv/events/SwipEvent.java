package com.example.phone.zhibotv.events;

/**
 * Created by Administrator on 2016/12/2.
 */
public class SwipEvent {
    private String msg;
    public final int what;

    public SwipEvent(int what) {
        this.what = what;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
