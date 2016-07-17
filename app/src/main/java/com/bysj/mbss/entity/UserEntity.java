package com.bysj.mbss.entity;

import cn.bmob.v3.BmobUser;

/**
 * 用户数据保存类
 *
 * @author
 */
public class UserEntity extends BmobUser {
    //	 * 头像
    private String photoUrl;
    //    密码
    private String psd;
    //    职业
    private String zy;
    //    权限 1管理员；2用户
    private String line;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
