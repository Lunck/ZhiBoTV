package com.example.phone.zhibotv.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016-11-28.
 */
@Table(name = "SaiShiTabTitleModel")
public class SaiShiTabTitleModel {
    @Column(name = "id",isId = true,autoGen = false)
    private String id;
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
