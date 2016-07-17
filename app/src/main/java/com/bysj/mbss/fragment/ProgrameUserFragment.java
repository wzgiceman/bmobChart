package com.bysj.mbss.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bysj.mbss.R;
import com.bysj.mbss.activity.CreateProgramActivity;
import com.bysj.mbss.adapter.TableAdapter;
import com.bysj.mbss.entity.TableEntity;
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
 * 创建的节目界面-用户查看
 */
@ContentView(R.layout.fragment_program)
public class ProgrameUserFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.ltview)
    private ListView mLtview;
    private TableAdapter adapter;

    @Override
    protected void initResource() {
        adapter = new TableAdapter(getActivity());
    }


    @Override
    protected void initWidget() {
        mLtview.setAdapter(adapter);
        mLtview.setOnItemClickListener(this);
        event(null);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TableEntity tableEntity = (TableEntity) adapter.getLtData().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("msg", tableEntity);
        bundle.putBoolean("change", false);
        jumpActivity(CreateProgramActivity.class, bundle);
    }

    /**
     * 更新数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void event(TableUpdateEvent event) {
        BmobQuery<TableEntity> query1 = new BmobQuery<>();
        query1.setLimit(50);
        query1.findObjects(getActivity(), new FindListener<TableEntity>() {
            @Override
            public void onSuccess(List<TableEntity> object) {
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
