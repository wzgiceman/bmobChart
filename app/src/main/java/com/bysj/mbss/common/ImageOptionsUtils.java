package com.bysj.mbss.common;

import android.widget.ImageView;

import com.bysj.mbss.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

/**
 * 下载图片相关设置
 * Created by WZG on 2016/1/19.
 */
public class ImageOptionsUtils {

    public  static ImageOptions get(int w,int h,int radius){
        return  new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(w), DensityUtil.dip2px(h))
                .setRadius(DensityUtil.dip2px(radius))
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.image_loading)
                .setFailureDrawableId(R.mipmap.image_error)
                .build();
    }
}
