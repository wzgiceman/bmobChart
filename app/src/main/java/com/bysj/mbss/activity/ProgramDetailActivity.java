package com.bysj.mbss.activity;

import android.view.View;
import android.widget.ImageView;

import com.bysj.mbss.R;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.entity.ProgramEntity;
import com.bysj.mbss.event.NotingEvent;
import com.bysj.mbss.event.ProgramUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 节目详情-编辑节目
 */
@ContentView(R.layout.activity_program_detail)
public class ProgramDetailActivity extends BaseFramentActivity {
    @ViewInject(R.id.etv_name)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvName;
    @ViewInject(R.id.etv_tel)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvTel;
    @ViewInject(R.id.etv_role)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvRole;
    @ViewInject(R.id.etv_dj)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvDj;
    @ViewInject(R.id.etv_mik)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvMik;
    @ViewInject(R.id.etv_light)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvLight;
    @ViewInject(R.id.etv_fzr)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvFzr;
    @ViewInject(R.id.btn_finish)
    private ImageView mBtnFinish;
    //        是否可以编辑
    boolean change;
    ProgramEntity programEntity;

    @Override
    protected void initResource() {
        programEntity = MyApplication.PG;
        change = getIntent().getExtras().getBoolean("change", true);
    }

    @Override
    protected void initWidget() {
        mEtvName.setText(programEntity.getName());
        mEtvTel.setText(programEntity.getTime());
        mEtvRole.setText(programEntity.getActor());
        mEtvDj.setText(programEntity.getProps());
        mEtvMik.setText(programEntity.getMike());
        mEtvLight.setText(programEntity.getLight());
        mEtvFzr.setText(programEntity.getPrincipal());
        if (!change) {
            mEtvName.setEnabled(false);
            mEtvTel.setEnabled(false);
            mEtvRole.setEnabled(false);
            mEtvDj.setEnabled(false);
            mEtvMik.setEnabled(false);
            mEtvLight.setEnabled(false);
            mEtvFzr.setEnabled(false);
            mBtnFinish.setVisibility(View.GONE);
        }
    }

    @Event(value = R.id.btn_finish)
    private void onBtnFinishClick(View view) {
        String name = mEtvName.getText().toString();
        String time = mEtvTel.getText().toString();
        String role = mEtvRole.getText().toString();
        String dj = mEtvDj.getText().toString();
        String mik = mEtvMik.getText().toString();
        String light = mEtvLight.getText().toString();
        String fzr = mEtvFzr.getText().toString();
        programEntity.setName(name);
        programEntity.setTime(time);
        programEntity.setActor(role);
        programEntity.setProps(dj);
        programEntity.setMike(mik);
        programEntity.setLight(light);
        programEntity.setPrincipal(fzr);
        EventBus.getDefault().post(new ProgramUpdateEvent(programEntity));
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(NotingEvent event) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
