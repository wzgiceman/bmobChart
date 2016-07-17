package com.bysj.mbss.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**新闻数据
 * Created by WZG on 2016/2/24.
 */
public class NewsEntity extends BmobObject implements Serializable {
//    标题
    private String title;
//    内容
    private String content;
    //     图片
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
