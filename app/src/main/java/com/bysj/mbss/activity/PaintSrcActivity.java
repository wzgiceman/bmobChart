package com.bysj.mbss.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;

import com.ab.util.AbFileUtil;
import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bysj.mbss.R;
import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.entity.PaintSrcEntity;
import com.bysj.mbss.event.NotingEvent;
import com.bysj.mbss.event.PaintSrcUpdateEvent;
import com.yancy.imageselector.ImageSelectorActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 涂鸦界面
 */
@ContentView(R.layout.activity_paint_src)
public class PaintSrcActivity extends BaseFramentActivity {
    @ViewInject(R.id.harwrite)
    private com.bysj.mbss.view.HandWrite handWrite;
    @ViewInject(R.id.clear)
    private Button clear;
    @ViewInject(R.id.save)
    private Button save;
    //    获取照片标示
    private static int REQUEST_IMAGE = 1;

    @Override
    protected void initResource() {

    }

    @Override
    protected void initWidget() {
    }

    @Event(value = R.id.btn_select)
    private void onBtnSelectClick(View view) {
        Intent intent = new Intent(this, ImageSelectorActivity.class);
        // 是否开启相机  默认 开启
        intent.putExtra(ImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        //  如果开启多选，则配置可选图片的最大数量 默认 6 张
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        // 多选
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_MODE, ImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Event(value = R.id.clear)
    private void onClearClick(View view) {
        handWrite.clear();
    }

    @Event(value = R.id.save)
    private void onSaveClick(View view) {
        File f = new File(AbFileUtil.getFileDownloadDir(this) + File.separator + System.currentTimeMillis() + ".jpg");
        saveMyBitmap(f, handWrite.new1Bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            List<String> path = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            handWrite.setImage(path.get(0));
        }
    }


    /**
     * 保存到本地
     *
     * @param f
     * @param mBitmap
     * @throws IOException
     */
    public void saveMyBitmap(File f, Bitmap mBitmap) {
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            commitFile(f.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 文件上传
     */
    public void commitFile(String filePath) {
        final PaintSrcEntity paintSrcEntity = new PaintSrcEntity();
        paintSrcEntity.setUserId(MyApplication.User.getObjectId());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("上传中");
        progressDialog.setCancelable(true);
        progressDialog.show();
        BTPFileResponse response = BmobProFile.getInstance(this).upload(filePath, new UploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                progressDialog.dismiss();
                paintSrcEntity.setPath(file.getUrl());
                paintSrcEntity.save(PaintSrcActivity.this);
                EventBus.getDefault().post(new PaintSrcUpdateEvent());
                showTaos("保存成功！");
                finish();
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                showTaos("失败失败" + errormsg);
                progressDialog.dismiss();
            }

        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void event(NotingEvent event) {

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
        EventBus.getDefault().unregister(this);
    }
}
