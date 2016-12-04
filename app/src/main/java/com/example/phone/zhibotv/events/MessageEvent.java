package com.example.phone.zhibotv.events;

import com.example.phone.zhibotv.model.MessageModel;

/**
 * Created by Administrator on 2016/11/29.
 */
public class MessageEvent {
    private MessageModel model;
    //public final int what;
    /*public MessageEvent(int what) {
        this.what = what;
    }*/

    public MessageEvent(MessageModel model){
        this.model=model;
    }
    public MessageModel getModel() {
        return model;
    }

    public void setModel(MessageModel model) {
        this.model = model;
    }
}
