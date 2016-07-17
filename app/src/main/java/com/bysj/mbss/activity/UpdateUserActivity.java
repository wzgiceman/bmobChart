package com.bysj.mbss.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.ab.util.AbStrUtil;
import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bysj.mbss.R;
import com.bysj.mbss.common.ImageOptionsUtils;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.entity.UserEntity;
import com.yancy.imageselector.ImageSelectorActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 修改用户信息界面
 * wzg
 */
@ContentView(R.layout.activity_update_user)
public class UpdateUserActivity extends BaseFramentActivity {
    @ViewInject(R.id.img_photo)
    private ImageView mImgPhoto;
    @ViewInject(R.id.etv_name)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvName;
    @ViewInject(R.id.etv_psd)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvPsd;
    @ViewInject(R.id.etv_tel)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvTel;
    private UserEntity userEntity;
    //    图片地址
    private String targetPath;
    //    获取照片标示
    private static int REQUEST_IMAGE = 1;


    @Override
    protected void initResource() {
        userEntity= MyApplication.User;
    }

    @Override
    protected void initWidget() {
        x.image().bind(mImgPhoto, userEntity.getPhotoUrl(), ImageOptionsUtils.get(0, 0, 90));
        mEtvName.setText(userEntity.getUsername());
        mEtvTel.setText(userEntity.getMobilePhoneNumber());
        mEtvPsd.setText(userEntity.getPsd());
    }

    @Event(R.id.img_photo)
    private void mImgPhotoEvent(View view) {
        Intent intent = new Intent(this, ImageSelectorActivity.class);
        // 是否开启相机  默认 开启
        intent.putExtra(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        //  如果开启多选，则配置可选图片的最大数量 默认 6 张
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        // 多选
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            List<String> path = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            targetPath=path.get(0);
            x.image().bind(mImgPhoto,targetPath,ImageOptionsUtils.get(0,0,90));
        }
    }


    @Event(R.id.btn_determinal)
    private void mBtnDeterminalEvent(View view) {
        String name = mEtvName.getText().toString();
        String tel = mEtvTel.getText().toString();
        String psd = mEtvPsd.getText().toString();
        if (AbStrUtil.isEmpty(name) || AbStrUtil.isEmpty(tel) || AbStrUtil.isEmpty(psd)) {
            showMsg(R.string.simple_data);
            return;
        }
        if (!AbStrUtil.isMobileNo(tel)) {
            showMsg("输入正确的手机号码");
            return;
        }
        userEntity.setUsername(name);
        userEntity.setMobilePhoneNumber(tel);
        userEntity.setPassword(psd);
        if (new File(targetPath).exists()) {
            commitFile(targetPath);
        } else {
            userEntity.update(this);
            finish();
        }
    }


    /**
     * 文件上传
     */
    public void commitFile(String filePath) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("上传中");
        progressDialog.setCancelable(true);
        progressDialog.show();
        BTPFileResponse response = BmobProFile.getInstance(this).upload(filePath, new UploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                userEntity.setPhotoUrl(file.getUrl());
                userEntity.update(UpdateUserActivity.this);
                finish();
                progressDialog.dismiss();
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                showMsg("失败失败" + errormsg);
                progressDialog.dismiss();
            }
        });
    }
}
