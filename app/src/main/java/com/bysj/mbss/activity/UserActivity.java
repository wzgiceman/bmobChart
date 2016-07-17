package com.bysj.mbss.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bysj.mbss.R;
import com.bysj.mbss.common.ImageOptionsUtils;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.view.DragLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 用户管理界面
 * wzg
 */
@ContentView(R.layout.activity_user)
public class UserActivity extends BaseFramentActivity implements DragLayout.DragListener {
    @ViewInject(R.id.dl)
    private com.bysj.mbss.view.DragLayout mDl;
    @ViewInject(R.id.image_photo)
    private ImageView mImagePhoto;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.img_big_photo)
    private ImageView mImgBigPhoto;

    @Override
    protected void initResource() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void initWidget() {
        mDl.setLeftRange(150);
        mDl.setDragListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvName.setText(MyApplication.User.getUsername());
        x.image().bind(mImagePhoto, MyApplication.User.getPhotoUrl(), ImageOptionsUtils.get(0, 0, 90));
        x.image().bind(mImgBigPhoto, MyApplication.User.getPhotoUrl(), ImageOptionsUtils.get(0, 0, 90));
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

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void onDrag(float percent) {

    }
}
