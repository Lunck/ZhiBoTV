package com.example.phone.zhibotv.model;

import java.util.List;

/**
 * Created by Administrator on 2016-11-28.
 */
public class SaiShiContenModel {
   /*       "id":"22984",
            "leftName":"",
            "leftIcon":"",
            "rightName":"",
            "rightIcon":"",
            "category":"台球：中式台球公开赛外围赛第二日",
            "time":1480305600,
            "servertime":1480311666,
            "isSubscribe":0,
            "status":1,
            "type":"1",
            "scheduleIcon":"/uploads/imgs/2016/11-27/403bd8dcda664b4d301a3b52e66234e7.png",*/

    private String id;
    private String leftName;
    private String leftIcon;
    private String rightName;
    private String rightIcon;
    private String category;
    private int type;
    private String scheduleIcon;
    private List<SaiShiContentModelAnchors> anchors;
    private List<SaiShiContentModelSourceList> sourceList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(String leftIcon) {
        this.leftIcon = leftIcon;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(String rightIcon) {
        this.rightIcon = rightIcon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getScheduleIcon() {
        return scheduleIcon;
    }

    public void setScheduleIcon(String scheduleIcon) {
        this.scheduleIcon = scheduleIcon;
    }

    public List<SaiShiContentModelAnchors> getAnchors() {
        return anchors;
    }

    public void setAnchors(List<SaiShiContentModelAnchors> anchors) {
        this.anchors = anchors;
    }

    public List<SaiShiContentModelSourceList> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<SaiShiContentModelSourceList> sourceList) {
        this.sourceList = sourceList;
    }
}
