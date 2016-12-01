package com.example.phone.zhibotv.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by my on 2016/11/30.
 */
@Table(name="History")
public class History {
    @Column(name = "roomid")
    private String roomid;
    @Column(name = "id",isId = true)
    private int id;
    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
