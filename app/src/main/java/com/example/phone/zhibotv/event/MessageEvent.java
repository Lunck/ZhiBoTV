package com.example.phone.zhibotv.event;

/**
 * Created by Administrator on 2016/11/29.
 */
public class MessageEvent {

    private String icon;
    private String name;

    public MessageEvent(String icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
