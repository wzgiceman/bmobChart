package com.bysj.mbss.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 节目表
 * Created by WZG on 2016/6/7.
 */
@Table(name = "ProgramEntity")
public class ProgramEntity implements Serializable {
    @Column(name = "ids",isId = true)
    private int ids;
    @Column(name = "tableId")
    private int tableId;
    //    节目号
    @Column(name = "number")
    private int number;
    //    名称
    @Column(name = "name")
    private String name;
    //    时长
    @Column(name = "time")
    private String time;
    //    演员
    @Column(name = "actor")
    private String actor;
    //    道具
    @Column(name = "props")
    private String props;
    //    麦克
    @Column(name = "mike")
    private String mike;
    //    灯光
    @Column(name = "light")
    private String light;
    //    负责人
    @Column(name = "principal")
    private String principal;

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public String getMike() {
        return mike;
    }

    public void setMike(String mike) {
        this.mike = mike;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
