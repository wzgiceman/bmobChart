package com.bysj.mbss.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bysj.mbss.R;
import com.bysj.mbss.fragment.BaseFragment;

import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 基础类管理类
 * 系统方法
 * Created by WZG on 2016/1/28.
 */
public class BaseFragmentManagerActivity extends SwipeBackActivity {
    protected FragmentManager fragmentManager;
    private List<BaseFragment> ltFragmetn;
    protected int show = 0;
    private int layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
    }

    /**
     * 单一framgent显示的fragment页面
     *
     * @param fragment
     */
    protected void setFragment(int layout, Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fade_out);
        ft.replace(layout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }


    /**
     * 初始化fragment显示界面
     *
     * @param layout
     * @param ltFragmetn
     */
    protected void initFragment(int layout, List<BaseFragment> ltFragmetn) {
        this.ltFragmetn = ltFragmetn;
        this.layout = layout;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fade_out);
        for (BaseFragment fragment : ltFragmetn) {
            transaction.add(layout, fragment).hide(fragment);
        }
        transaction.show(ltFragmetn.get(show)).commit();
    }

    /**
     * 指定显示的fragment位置
     *
     * @param index
     */
    protected void showFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (show != index) {
            transaction.hide(ltFragmetn.get(show));
            BaseFragment fragment = ltFragmetn.get(index);
            if (fragment.isAdded()) {
                transaction.show(fragment).commit();
            } else {
                transaction.add(layout, fragment).show(fragment).commit();
            }
            show = index;
        }
    }


    /**
     * 点击空白位置 隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
