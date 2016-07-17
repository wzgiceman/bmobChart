package com.bysj.mbss.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bysj.mbss.R;
import com.bysj.mbss.entity.ProgramEntity;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * 演员
 * Created by WZG on 2016/6/7.
 */
public class ProgramAdapter extends BaseAdapters {
    private Context context;

    public ProgramAdapter(Context context) {
        super(null);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_program, null);
            AutoUtils.autoSize(convertView);
        }

        TextView number = (TextView) get(convertView, R.id.jmh);
        TextView name = (TextView) get(convertView, R.id.jmm);
        TextView time = (TextView) get(convertView, R.id.sc);
        TextView role = (TextView) get(convertView, R.id.yy);
        TextView dj = (TextView) get(convertView, R.id.dj);
        TextView mk = (TextView) get(convertView, R.id.mk);
        TextView dg = (TextView) get(convertView, R.id.dg);
        TextView fzr = (TextView) get(convertView, R.id.fzr);

        ProgramEntity programEntity = (ProgramEntity) getLtData().get(position);
        programEntity.setNumber(position + 1);
        number.setText(String.valueOf(position + 1));
        name.setText(programEntity.getName());
        time.setText(programEntity.getTime());
        role.setText(programEntity.getActor());
        dj.setText(programEntity.getProps());
        mk.setText(programEntity.getMike());
        dg.setText(programEntity.getLight());
        fzr.setText(programEntity.getPrincipal());

        return convertView;
    }
}
