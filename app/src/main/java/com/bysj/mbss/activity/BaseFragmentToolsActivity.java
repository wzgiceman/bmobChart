package com.bysj.mbss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ab.util.AbLogUtil;
import com.bigkoo.alertview.AlertView;

/**
 * 基础类工具类
 * 公共方法
 * Created by WZG on 2016/1/28.
 */
public class BaseFragmentToolsActivity extends BaseFragmentManagerActivity {
    /**
     * 自定义log输出
     *
     * @param content
     */
    protected void myLog(String content) {
        AbLogUtil.d("tag", content);
    }

    /**
     * 显示基本信息
     *
     * @param msg
     */
    protected void showMsg(int msg) {
        new AlertView("提示", getString(msg), null, null, new String[]{"确定"}, this, AlertView.Style.Alert, null).setCancelable
                (true).show();
    }

    /**
     * 显示基本信息
     *
     * @param msg
     */
    protected void showMsg(String msg) {
        new AlertView("提示", msg, null, null, new String[]{"确定"}, this, AlertView.Style.Alert, null).setCancelable(true).show();
    }

    /**
     * 显示基本信息
     *
     * @param msg
     */
    protected void showTaos(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转到指定的activity
     *
     * @param cls
     */
    public void jumpActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void jumpActivityFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    /**
     * 带参数跳转到指定的activity
     *
     * @param cls
     * @param bundle
     */
    public void jumpActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

      /**
     * 带参数跳转到指定的activity,然后销毁
     *
     * @param cls
     * @param bundle
     */
    public void jumpActivityFinish(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
