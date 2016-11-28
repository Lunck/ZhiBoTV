package com.example.phone.zhibotv.model;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiContentModelSourceList {
    /*                  "id":"421627",
                        "roomid":"20030510",
                        "sourceUrl":"http://123.103.12.169:8088/flvvideo5/index.html",
                        "uname":"高清直播"
*/

    private String id;
    private String roomid;
    private  String sourceUrl;
    private String uname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
