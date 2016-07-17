package com.bysj.mbss.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bysj.mbss.R;
import com.bysj.mbss.common.ImageOptionsUtils;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.db.DbOpertor;
import com.bysj.mbss.entity.ProgramEntity;
import com.bysj.mbss.entity.TableEntity;
import com.bysj.mbss.event.NotingEvent;
import com.bysj.mbss.event.PaintSrcUpdateEvent;
import com.bysj.mbss.event.TableUpdateEvent;
import com.bysj.mbss.fragment.BaseFragment;
import com.bysj.mbss.fragment.BlankFragment;
import com.bysj.mbss.fragment.PaintSrcFragment;
import com.bysj.mbss.fragment.ProgrameFragment;
import com.bysj.mbss.view.DragLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 管理員界面
 */
@ContentView(R.layout.activity_controller)
public class ControllerActivity extends BaseFramentActivity implements BottomNavigationBar.OnTabSelectedListener, DragLayout
        .DragListener {
    @ViewInject(R.id.bottom_navigation_bar)
    private com.ashokvarma.bottomnavigation.BottomNavigationBar bottomNavigationBar;
    @ViewInject(R.id.dl)
    private com.bysj.mbss.view.DragLayout mDl;
    @ViewInject(R.id.image_photo)
    private ImageView mImagePhoto;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.img_big_photo)
    private ImageView mImgBigPhoto;
    @ViewInject(R.id.img_update)
    private ImageView mImgUpdate;

    @Override
    protected void initResource() {
        List<BaseFragment> ltFragment = new ArrayList<>();
        ltFragment.add(new ProgrameFragment());
        ltFragment.add(PaintSrcFragment.newInstance(true));
        ltFragment.add(new BlankFragment());
        ltFragment.add(new BlankFragment());
        ltFragment.add(new BlankFragment());
        initFragment(R.id.rlyout, ltFragment);
        showFragment(0);
    }

    @Override
    protected void initWidget() {
        setSwipeBackEnable(false);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "表格"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "涂鸦"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_music_note_white_24dp, "**"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tv_white_24dp, "**"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_videogame_asset_white_24dp, "**"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.setBackgroundColor(getResources().getColor(R.color.bg_low));
        mDl.setLeftRange(350);
        //        mDl.setDragListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvName.setText(MyApplication.User.getUsername());
        x.image().bind(mImagePhoto, MyApplication.User.getPhotoUrl(), ImageOptionsUtils.get(0, 0, 90));
        x.image().bind(mImgBigPhoto, MyApplication.User.getPhotoUrl(), ImageOptionsUtils.get(0, 0, 90));
    }

    @Event(value = R.id.btn_create)
    private void onBtnCreateClick(View view) {
        jumpActivity(CreateProgramActivity.class);
    }

    @Event(value = R.id.btn_creates)
    private void onBtnCreatesClick(View view) {
        jumpActivity(CreateProgramActivity.class);
    }

    @Event(R.id.image_photo)
    private void mImagePhotoEvent(View view) {
        mDl.open();
    }


    @Event(R.id.img_big_photo)
    private void mImgBigPhotoEvent(View view) {
        jumpActivity(UpdateUserActivity.class);
    }

    @Event(value = R.id.btn_exit)
    private void onBtnExitClick(View view) {
        finish();
    }

    @Event(value = R.id.btn_paint)
    private void onBtnPaintClick(View view) {
        jumpActivity(PaintSrcActivity.class);
    }

    @Event(value = R.id.img_update)
    private void onImgUpdateClick(View view) {
        switch (show) {
            case 0:
                updateTable();
                break;
            case 1:
                //                更新涂鸦
                EventBus.getDefault().post(new PaintSrcUpdateEvent());
                break;
            default:
                break;
        }
        showMsg("更新成功");
    }

    @Override
    public void onTabSelected(int position) {
        showFragment(position);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void onDrag(float percent) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(NotingEvent event) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 更新表格
     */
    private void updateTable() {
        BmobQuery<TableEntity> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("UserId", MyApplication.User.getObjectId());
        query1.setLimit(50);
        query1.findObjects(this, new FindListener<TableEntity>() {
            @Override
            public void onSuccess(List<TableEntity> object) {
                for (TableEntity tableEntity : object) {
                    if (!DbOpertor.exit(tableEntity)) {
                        try {
                            MyApplication.db.save(tableEntity);
                            for (ProgramEntity pg : tableEntity.getLtPg()) {
                                MyApplication.db.save(pg);
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //                更新完以后通知界面刷新
                EventBus.getDefault().post(new TableUpdateEvent());
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }

}
