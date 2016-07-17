package com.bysj.mbss.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bysj.mbss.entity.NewsEntity;

import org.xutils.x;


/**
 * 自动滚动引导页
 * Created by Sai on 15/8/4.
 */
public class LocalImageHolderView implements Holder<NewsEntity> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, NewsEntity data) {
        x.image().bind(imageView,data.getPhoto());
    }
}
