package com.bysj.mbss.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.util.AbStrUtil;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bysj.mbss.R;
import com.bysj.mbss.common.ImageOptionsUtils;
import com.bysj.mbss.entity.UserEntity;
import com.yancy.imageselector.ImageSelectorActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册界面
 * wzg
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseFramentActivity {
    @ViewInject(R.id.img_photo)
    private ImageView mImgPhoto;
    @ViewInject(R.id.etv_name)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvName;
    @ViewInject(R.id.etv_psd)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvPsd;
    @ViewInject(R.id.etv_tel)
    private com.rengwuxian.materialedittext.MaterialEditText mEtvTel;
    @ViewInject(R.id.tv_zy)
    private TextView mTvZy;
    private UserEntity userEntity;
    //    图片地址
    private String targetPath="";
    //    获取照片标示
    private static int REQUEST_IMAGE = 1;

    @Override
    protected void initResource() {
        userEntity = new UserEntity();
        userEntity.setLine("2");
        userEntity.setPhotoUrl("http://i0.sinaimg.cn/travel/2015/0507/U9385P704DT20150507151553.jpg");
    }

    @Override
    protected void initWidget() {
        x.image().bind(mImgPhoto, userEntity.getPhotoUrl(), ImageOptionsUtils.get(0, 0, 90));
    }

    @Event(R.id.btn_determinal)
    private void mBtnDeterminalEvent(View view) {
        String name = mEtvName.getText().toString();
        String psd = mEtvPsd.getText().toString();
        String tel = mEtvTel.getText().toString();
        String pref=mTvZy.getText().toString();
        if (AbStrUtil.isEmpty(name) || AbStrUtil.isEmpty(psd) || AbStrUtil.isEmpty(tel)|| AbStrUtil.isEmpty(pref)) {
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
        userEntity.setZy(pref);
        if (new File(targetPath).exists()) {
            commitFile(targetPath);
        } else {
            userEntity.signUp(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    showTaos("注册成功！");
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    showTaos("失败");
                }
            });
        }
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

    @Event(value = R.id.tv_zy)
    private void onTvZyClick(View view) {
        final String[] msg = new String[]{"老师", "学生", "计算机", "工程师", "会计", "教育"};
        new AlertView("选择职业", null, "cancel", null, msg, this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int i) {
                if (i < 0) return;
                mTvZy.setText(msg[i]);
            }
        }).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            List<String> path = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            targetPath = path.get(0);
            x.image().bind(mImgPhoto, targetPath, ImageOptionsUtils.get(0, 0, 90));
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
                userEntity.signUp(RegisterActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        showTaos("注册成功！");
                        progressDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        progressDialog.dismiss();
                        showTaos("失败");
                    }
                });
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
