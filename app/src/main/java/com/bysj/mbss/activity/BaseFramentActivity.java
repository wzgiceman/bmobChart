package com.bysj.mbss.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import org.xutils.x;

/**
 * fangmentactivity 自定义基础类
 * 可滑动销毁父类
 *
 * @author WZG
 */
public abstract class BaseFramentActivity extends BaseFragmentToolsActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initResource();
        initWidget();
    }

    /**
     * 初始化数据
     */
    protected abstract void initResource();

    /**
     * 初始化控件
     */
    protected abstract void initWidget();

    /**
     * 初始化固有控件
     */
    protected void initConstantWidget() {
    }

    @Override
    public void onClick(View v) {
    }
}
