package com.bysj.mbss.common;

import android.app.Application;

import com.bysj.mbss.entity.ProgramEntity;
import com.bysj.mbss.entity.UserEntity;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

import cn.bmob.v3.Bmob;

/**
 * 全局变量存放
 * Created by WZG on 2016/1/19.
 */
public class MyApplication extends Application {
    //    数据库
    public static DbManager db;
    public static UserEntity User;
    public static ProgramEntity PG;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "e105de6bb8a4168124b4663e5d54c107");

        x.Ext.init(this);
        x.Ext.setDebug(true);
//        AbAppUtil.importDatabase(this,"bysj", R.raw.bysj);

//        初始化db
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("bysj")
                .setDbDir(new File("/data/data/com.bysj.mbss/databases"))
                .setDbVersion(1);
        db = x.getDb(daoConfig);


        // 注册crashHandler 异常捕获 防止直接崩溃影响使用
//        EduCrashHandler crashHandler = EduCrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

    }
}
