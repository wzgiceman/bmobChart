package com.bysj.mbss.activity;

import android.widget.ImageView;

import com.bysj.mbss.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 涂鸦详情界面
 */
@ContentView(R.layout.activity_paint_src_detail)
public class PaintSrcDetailActivity extends BaseFramentActivity {
    @ViewInject(R.id.image)
    private ImageView mImage;

    @Override
    protected void initResource() {
    }

    @Override
    protected void initWidget() {
        String path = getIntent().getExtras().getString("path");
        x.image().bind(mImage, path);
    }
}
