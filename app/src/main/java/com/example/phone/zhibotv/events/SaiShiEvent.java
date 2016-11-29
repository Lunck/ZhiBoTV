package com.example.phone.zhibotv.events;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiEvent {
    private String msg;
    public final int what;

    public SaiShiEvent(int what) {
        this.what = what;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
