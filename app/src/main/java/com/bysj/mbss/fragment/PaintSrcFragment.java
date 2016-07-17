package com.bysj.mbss.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bysj.mbss.R;
import com.bysj.mbss.activity.PaintSrcDetailActivity;
import com.bysj.mbss.adapter.PaintSrcAdapter;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.entity.PaintSrcEntity;
import com.bysj.mbss.event.PaintSrcUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 涂鸦列表
 * Created by WZG on 2016/6/8.
 */
@ContentView(R.layout.fragment_program)
public class PaintSrcFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView
        .OnItemLongClickListener, OnItemClickListener {
    @ViewInject(R.id.ltview)
    private ListView mLtview;
    private PaintSrcAdapter adapter;
    //    位置
    private int currentPosition;
    //    是否具有修改功能;主要是区分编辑者-和用户功能
    private boolean change;


    public static PaintSrcFragment newInstance(boolean change) {
        PaintSrcFragment fragment = new PaintSrcFragment();
        Bundle args = new Bundle();
        args.putBoolean("change", change);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initResource() {
        change = getArguments().getBoolean("change");
        adapter = new PaintSrcAdapter(getActivity());
    }

    @Override
    protected void initWidget() {
        mLtview.setAdapter(adapter);
        mLtview.setOnItemClickListener(this);
        if (change) {
            mLtview.setOnItemLongClickListener(this);
        }
        event(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PaintSrcEntity entity = (PaintSrcEntity) adapter.getLtData().get(currentPosition);
        Bundle bundle = new Bundle();
        bundle.putString("path", entity.getPath());
        jumpActivity(PaintSrcDetailActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        currentPosition = position;
        new AlertView("删除", "确定删除？", null, null, new String[]{"取消", "确定"}, getActivity(), AlertView.Style.Alert,
                this)
                .setCancelable(true).show();
        return true;
    }

    @Override
    public void onItemClick(Object o, int i) {
        if (i == 1) {
            PaintSrcEntity entity = (PaintSrcEntity) adapter.getLtData().get(currentPosition);
            entity.delete(getActivity());
            adapter.getLtData().remove(entity);
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void event(PaintSrcUpdateEvent event) {
        BmobQuery<PaintSrcEntity> query = new BmobQuery<>();
        if(change){
            query.addWhereEqualTo("userId", MyApplication.User.getObjectId());
        }
        query.setLimit(50);
        query.findObjects(getActivity(), new FindListener<PaintSrcEntity>() {
            @Override
            public void onSuccess(List<PaintSrcEntity> object) {
                adapter.setLtData(object);
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
