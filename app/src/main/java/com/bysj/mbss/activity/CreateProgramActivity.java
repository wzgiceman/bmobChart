package com.bysj.mbss.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ab.util.AbStrUtil;
import com.bigkoo.alertview.AlertView;
import com.bysj.mbss.R;
import com.bysj.mbss.adapter.ProgramAdapter;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.db.DbOpertor;
import com.bysj.mbss.entity.ProgramEntity;
import com.bysj.mbss.entity.TableEntity;
import com.bysj.mbss.event.ProgramUpdateEvent;
import com.bysj.mbss.event.TableUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建节目表界面
 */
@ContentView(R.layout.activity_create_table)
public class CreateProgramActivity extends BaseFramentActivity implements OnItemClickListener, AdapterView
        .OnItemLongClickListener {
    @ViewInject(R.id.llyotu_title)
    private LinearLayout mLlyotuTitle;
    @ViewInject(R.id.ltview)
    private ListView mLtview;
    @ViewInject(R.id.etv_title)
    private EditText mEtvTitle;
    @ViewInject(R.id.btn_determinal)
    private ImageView mBtnDeterminal;
    @ViewInject(R.id.btn_finish)
    private ImageView mBtnFinish;
    //    数据封装
    ProgramAdapter adapter;
    //    表详情数据
    private List<ProgramEntity> ltProgram;
    //    表数据
    private TableEntity tableEntity;
    //    是否是新建模式
    private boolean create;
    //    是否能修改操作
    private boolean change;

    @Override
    protected void initResource() {
        adapter = new ProgramAdapter(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            create = true;
            change=true;
            ltProgram = new ArrayList<>();
            tableEntity = new TableEntity();
            tableEntity.setIds((int) System.currentTimeMillis());
            tableEntity.setUserId(MyApplication.User.getObjectId());
            tableEntity.setType(1);
        } else {
            create = false;
            tableEntity = (TableEntity) bundle.getSerializable("msg");
            change = bundle.getBoolean("change");
            if (change) {
                ltProgram = DbOpertor.getProgramData(tableEntity.getIds());
            } else {
                ltProgram = tableEntity.getLtPg();
            }
        }
    }

    @Override
    protected void initWidget() {
        mLtview.setAdapter(adapter);
        mLtview.setOnItemClickListener(this);
        mLtview.setOnItemLongClickListener(this);
        if (create) {
            onBtnDeterminalClick(null);
        } else {
            mEtvTitle.setText(tableEntity.getName());
            adapter.setLtData(ltProgram);
        }
        if (!change) {
            mBtnDeterminal.setVisibility(View.INVISIBLE);
            mBtnFinish.setVisibility(View.INVISIBLE);
            mEtvTitle.setEnabled(false);
        }
    }

    @Event(value = R.id.btn_determinal)
    private void onBtnDeterminalClick(View view) {
        ProgramEntity programEntity = new ProgramEntity();
        ltProgram.add(programEntity);
        adapter.setLtData(ltProgram);
    }

    @Event(value = R.id.btn_finish)
    private void onBtnFinishClick(View view) {
        String table = mEtvTitle.getText().toString();
        if (AbStrUtil.isEmpty(table)) {
            showMsg(R.string.simple_data);
            return;
        }
        tableEntity.setName(table);
        try {
            MyApplication.db.saveOrUpdate(tableEntity);
            for (ProgramEntity entity : ltProgram) {
                entity.setTableId(tableEntity.getIds());
                MyApplication.db.saveOrUpdate(entity);
            }
            EventBus.getDefault().post(new TableUpdateEvent());
            showTaos("保存成功！");
            finish();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyApplication.PG = (ProgramEntity) adapter.getLtData().get(position);
        Bundle bundle = new Bundle();
//        是否能修改ProgramUpdateEvent
        bundle.putBoolean("change", change);
        jumpActivity(ProgramDetailActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        new AlertView("提示", "确定要删除该行？", null, null, new String[]{"取消", "确定"}, this, AlertView.Style.Alert, new com.bigkoo
                .alertview
                .OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int i) {
                if (i == 1) {
                    ProgramEntity programEntity = ltProgram.get(position);
                    ltProgram.remove(programEntity);
                    adapter.setLtData(ltProgram);
                    if (!create) {
                        try {
                            MyApplication.db.delete(programEntity);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).show();
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void event(ProgramUpdateEvent event) {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.PG = null;
        EventBus.getDefault().unregister(this);
    }
}
