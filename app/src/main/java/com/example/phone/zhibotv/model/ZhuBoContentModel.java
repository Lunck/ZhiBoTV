package com.example.phone.zhibotv.model;

/**
 * Created by Administrator on 2016-11-29.
 */
public class ZhuBoContentModel {
                /*
                "uid":"51",
                "roomId":"6006",
                "nickname":"艾菲_joan",
                "title":"【录播】丁俊晖比赛回放",
                "imgUrl":"/uploads/imgs/2016/10-30/1477805942.JPG",
                "level":17,
                "follownum":"63249",
                "picUrl":"/uploads/imgs/2015/12-23/87d4bed99b431ccad4c80c57b75dc435.jpg",
                "roomHaibaoImg":"/uploads/imgs/2016/10-30/1477805942.JPG",
                "roomViewerNum":"6472",
                "sex":"0",
                "roomIsLive":"1",
                "liveEquipment":"0",
                "liveStatus":"0",
                "userStreamName":"s_51"
            */
    private String uid;
    private String roomId;
    private String nickname;
    private String title;
    private String imgUrl;
    private String level;
    private String follownum;
    private String picUrl;
    private String sex;
    private int liveStatus;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFollownum() {
        return follownum;
    }

    public void setFollownum(String follownum) {
        this.follownum = follownum;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }
}
