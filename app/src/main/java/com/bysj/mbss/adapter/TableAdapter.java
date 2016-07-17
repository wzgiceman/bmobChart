package com.bysj.mbss.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bysj.mbss.R;
import com.bysj.mbss.common.ImageOptionsUtils;
import com.bysj.mbss.entity.TableEntity;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.x;

/**
 * 表数据
 * Created by WZG on 2016/6/7.
 */
public class TableAdapter extends BaseAdapters {
    private Context context;

    public TableAdapter(Context context) {
        super(null);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_table, null);
            AutoUtils.autoSize(convertView);
        }

        TextView name = (TextView) get(convertView, R.id.tv_name);
        ImageView img = (ImageView) get(convertView, R.id.img);

        TableEntity tableEntity = (TableEntity) getLtData().get(position);
        x.image().bind(img, "http://img02.tooopen.com/images/20150512/tooopen_sy_123873123632.jpg", ImageOptionsUtils.get(40, 40,
                90));
        name.setText(tableEntity.getName());

        return convertView;
    }
}
