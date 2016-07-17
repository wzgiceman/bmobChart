package com.bysj.mbss.activity;

import android.view.View;

import com.bysj.mbss.R;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.entity.UserEntity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 主界面
 * wzg
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseFramentActivity {
    @ViewInject(R.id.etv_name)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvName;
    @ViewInject(R.id.etv_psd)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvPsd;
    //    权限
    private String line;


    @Override
    protected void initResource() {
        setSwipeBackEnable(false);
        line = "2";
    }

    @Override
    protected void initWidget() {
    }

    @Event(value = R.id.rbnt_l)
    private void onRbntLClick(View view) {
        line = "2";
    }

    @Event(value = R.id.rbnt_r)
    private void onRbntRClick(View view) {
        line = "1";
    }

    @Event(R.id.btn_determinal)
    private void mBtnDeterminalEvent(View view) {
        String name = mEtvName.getText().toString();
        final String psd = mEtvPsd.getText().toString();
        if ("".equals(name) || "".equals(psd)) {
            showMsg(R.string.simple_data);
            return;
        }
        UserEntity.loginByAccount(this, name, psd, new LogInListener<UserEntity>() {
            @Override
            public void done(UserEntity UserEntity, BmobException e) {
                if (UserEntity != null) {
                    if(UserEntity.getLine().equals(line)){
                        MyApplication.User = UserEntity;
                        MyApplication.User.setPsd(psd);
                        if(line.equals("1")){
                            jumpActivity(ControllerActivity.class);
                        }else{
                            jumpActivity(UserSecendActivity.class);
                        }
                    }else{
                        showMsg("选择正确的登录权限!");
                    }
                } else {
                    showMsg("登录失败,请检查网络或用户名密码!");
                }
            }
        });
    }

    @Event(R.id.tv_register)
    private void mTvRegisterEvent(View view) {
        jumpActivity(RegisterActivity.class);
    }

}
