package com.bysj.mbss.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 表数据
 * Created by WZG on 2016/6/7.
 */
@Table(name = "TableEntity")
public class TableEntity extends BmobObject {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "ids")
    private int ids;
    //    标名
    @Column(name = "name")
    private String name;
    //    用户id
    @Column(name = "UserId")
    private String UserId;
    //    类型1：节目表****
    @Column(name = "type")
    private int type;
    //    表数据服务器
    private List<ProgramEntity> ltPg;
    private List<ProgramsEntity> ltPgs;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public List<ProgramEntity> getLtPg() {
        return ltPg;
    }

    public void setLtPg(List<ProgramEntity> ltPg) {
        this.ltPg = ltPg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
