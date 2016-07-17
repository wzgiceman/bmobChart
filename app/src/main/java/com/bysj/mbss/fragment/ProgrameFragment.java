package com.bysj.mbss.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bysj.mbss.R;
import com.bysj.mbss.activity.CreateProgramActivity;
import com.bysj.mbss.adapter.TableAdapter;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.db.DbOpertor;
import com.bysj.mbss.entity.TableEntity;
import com.bysj.mbss.event.PullEvent;
import com.bysj.mbss.event.TableUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 创建的节目界面-管理员界面
 */
@ContentView(R.layout.fragment_program)
public class ProgrameFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView
        .OnItemLongClickListener, OnItemClickListener {
    @ViewInject(R.id.ltview)
    private ListView mLtview;
    private TableAdapter adapter;
    //    位置
    private int currentPosition;

    @Override
    protected void initResource() {
        adapter = new TableAdapter(getActivity());
    }


    @Override
    protected void initWidget() {
        mLtview.setAdapter(adapter);
        mLtview.setOnItemClickListener(this);
        mLtview.setOnItemLongClickListener(this);
        adapter.setLtData(DbOpertor.getTableBy(1, MyApplication.User.getObjectId()));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TableEntity tableEntity = (TableEntity) adapter.getLtData().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("msg", tableEntity);
        bundle.putBoolean("change", true);
        jumpActivity(CreateProgramActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        currentPosition = position;
        new AlertView("操作", null, "取消", null, new String[]{"上传", "删除", "删除并同步"}, getActivity(), AlertView.Style.ActionSheet, this)
                .setCancelable(true).show();
        return true;
    }

    @Override
    public void onItemClick(Object o, int i) {
        if (i == 0) {
            EventBus.getDefault().post(new PullEvent());
        } else if (i == 1) {
            TableEntity tableEntity = (TableEntity) adapter.getLtData().get(currentPosition);
            DbOpertor.deleteTable(tableEntity);
            adapter.getLtData().remove(tableEntity);
            adapter.notifyDataSetChanged();
        } else if (i == 2) {
            TableEntity tableEntity = (TableEntity) adapter.getLtData().get(currentPosition);
            deleteWeb(tableEntity);
            DbOpertor.deleteTable(tableEntity);
            adapter.getLtData().remove(tableEntity);
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 后台上传
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void event(PullEvent event) {
        final TableEntity tableEntity = (TableEntity) adapter.getLtData().get(currentPosition);
        deleteWeb(tableEntity);
        tableEntity.setLtPg(DbOpertor.getProgramData(tableEntity.getIds()));
        tableEntity.save(getActivity());
    }

    /**
     * 更新数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(TableUpdateEvent event) {
        adapter.setLtData(DbOpertor.getTableBy(1, MyApplication.User.getObjectId()));
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


    /**
     * 删除服务器的数据
     *
     * @param tableEntity
     */
    private void deleteWeb(TableEntity tableEntity) {
        BmobQuery<TableEntity> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("ids", tableEntity.getIds());
        query1.setLimit(2);
        query1.findObjects(getActivity(), new FindListener<TableEntity>() {
            @Override
            public void onSuccess(List<TableEntity> object) {
                object.get(0).delete(getActivity());
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }
}
