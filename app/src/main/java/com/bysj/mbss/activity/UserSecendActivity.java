package com.bysj.mbss.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bysj.mbss.R;
import com.bysj.mbss.common.ImageOptionsUtils;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.event.NotingEvent;
import com.bysj.mbss.event.PaintSrcUpdateEvent;
import com.bysj.mbss.event.TableUpdateEvent;
import com.bysj.mbss.fragment.BaseFragment;
import com.bysj.mbss.fragment.PaintSrcFragment;
import com.bysj.mbss.fragment.ProgrameUserFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * 用户1界面
 */
@ContentView(R.layout.activity_user_secend)
public class UserSecendActivity extends BaseFramentActivity implements NavigationView.OnNavigationItemSelectedListener, View
        .OnClickListener {
    @ViewInject(R.id.navigationView)
    private android.support.design.widget.NavigationView mNavigationView;
    @ViewInject(R.id.drawer_layout)
    private android.support.v4.widget.DrawerLayout mDrawerLayout;
    @ViewInject(R.id.img_open)
    private ImageView imgOpen;


    @Override
    protected void initResource() {
        setSwipeBackEnable(false);
        List<BaseFragment> ltFragment = new ArrayList<>();
        ltFragment.add(new ProgrameUserFragment());
        ltFragment.add(PaintSrcFragment.newInstance(false));
        initFragment(R.id.rlyout, ltFragment);
        showFragment(0);
        connectNotify();
    }

    @Override
    protected void initWidget() {
        View headerLayout = mNavigationView.getHeaderView(0);
        TextView tvName = (TextView) headerLayout.findViewById(R.id.tv_name);
        ImageView imgUser = (ImageView) headerLayout.findViewById(R.id.img_user);
        tvName.setText(MyApplication.User.getUsername());
        x.image().bind(imgUser, MyApplication.User.getPhotoUrl(), ImageOptionsUtils.get(0, 0, 90));
        x.image().bind(imgOpen, MyApplication.User.getPhotoUrl(), ImageOptionsUtils.get(40, 40, 90));

        imgUser.setOnClickListener(this);
        //设置侧滑菜单选择监听事件
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Event(value = R.id.img_open)
    private void onBtnOpenClick(View view) {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Event(value = R.id.rbnt_table)
    private void onRbntTableClick(View view) {
        showFragment(0);
    }

    @Event(value = R.id.rbnt_paint)
    private void onRbntPaintClick(View view) {
        showFragment(1);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {  //关闭抽屉侧滑菜单
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.poor:
                break;
            case R.id.student:
                break;
            case R.id.my_poor:
                break;
            case R.id.sub_exit:
                finish();
                break;
            case R.id.my_emotion:
                break;
            case R.id.my_pull_commit:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        mDrawerLayout.closeDrawers();
        jumpActivity(UpdateUserActivity.class);
    }

    private BmobRealTimeData rtd;
    /**
     * 实时通知，服务器改变更新提醒
     */
    private void connectNotify() {
        rtd = new BmobRealTimeData();
        rtd.start(this, new ValueEventListener() {
            @Override
            public void onDataChange(JSONObject data) {
                try {
                    if (data.getString("tableName").equals("PaintSrcEntity")) {
                        new AlertView("涂鸦有更新啦", null, null, null, new String[]{"忽略", "更新"}, UserSecendActivity.this, AlertView.Style
                                .Alert,
                                new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int i) {
                                        if (i == 1) {
                                            EventBus.getDefault().post(new PaintSrcUpdateEvent());
                                        }
                                    }
                                }).setCancelable(true).show();

                    } else if (data.getString("tableName").equals("TableEntity")) {
                        new AlertView("表格有更新啦", null, null, null, new String[]{"忽略", "更新"}, UserSecendActivity.this, AlertView.Style
                                .Alert,
                                new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int i) {
                                        if (i == 1) {
                                            EventBus.getDefault().post(new TableUpdateEvent());
                                        }
                                    }
                                }).setCancelable(true).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onConnectCompleted() {
                rtd.subTableUpdate("TableEntity");
                rtd.subTableDelete("TableEntity");
                rtd.subTableUpdate("PaintSrcEntity");
                rtd.subTableDelete("PaintSrcEntity");
            }
        });
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
        // 取消监听表更新
        rtd.unsubTableUpdate("TableEntity");
        rtd.unsubTableUpdate("PaintSrcEntity");
        // 取消监听表删除
        rtd.unsubTableDelete("TableEntity");
        rtd.unsubTableDelete("PaintSrcEntity");
    }
}
