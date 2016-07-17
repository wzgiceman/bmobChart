package com.bysj.mbss.entity;

import cn.bmob.v3.BmobObject;

/**
 * 涂鸦数据
 * Created by WZG on 2016/6/8.
 */
public class PaintSrcEntity extends BmobObject {
    //    地址
    private String path;
    //    用户id
    private String userId;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
