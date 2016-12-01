package com.example.phone.zhibotv.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by my on 2016/12/1.
 */
@Table(name = "Chazhao")
public class Chazhao {
    @Column(name = "roomid",isId = true,autoGen = false)
    private String roomid;
    @Column(name = "title")
    private String title;
    @Column(name = "nickname")
    private String nickname;

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
