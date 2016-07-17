package com.bysj.mbss.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.ab.util.AbLogUtil;

/**
 * fragment基础工具类
 * Created by WZG on 2016/1/28.
 */
public class BaseToolFragment extends Fragment {
    /**
     * 显示基本信息
     *
     * @param msg
     */
    protected void showMsg(int msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示基本信息
     *
     * @param msg
     */
    protected void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }



    /**
     * 跳转到指定的activity
     *
     * @param cls
     */
    public void jumpActivity(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * 带参数跳转到指定的activity
     *
     * @param cls
     * @param bundle
     */
    public void jumpActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected  void myLog(String content){
        AbLogUtil.d("tag", content);
    }
}
