package com.example.phone.zhibotv.model;

import java.util.List;

/**
 * Created by Administrator on 2016-11-29.
 */
public class ZhuBoContentData {
    private List<ZhuBoContentModel> data;
    private String banner;
    private String bannerDesc;
    private int totalpage;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBannerDesc() {
        return bannerDesc;
    }

    public void setBannerDesc(String bannerDesc) {
        this.bannerDesc = bannerDesc;
    }

    public List<ZhuBoContentModel> getData() {
        return data;
    }

    public void setData(List<ZhuBoContentModel> data) {
        this.data = data;
    }
}
