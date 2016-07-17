package com.bysj.mbss.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * 自定义fragment基础类
 *
 * @author WZG
 */
public abstract class BaseFragment extends BaseToolFragment {
    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
        init();
    }
    /**
     * 初始化
     */
    protected void init() {
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


}
