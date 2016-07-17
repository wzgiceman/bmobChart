package com.bysj.mbss.event;

import com.bysj.mbss.entity.ProgramEntity;

/**
 * 节目表更新
 * Created by WZG on 2016/6/7.
 */
public class ProgramUpdateEvent {
    ProgramEntity entity;

    public ProgramUpdateEvent(ProgramEntity entity) {
        this.entity = entity;
    }

    public ProgramEntity getEntity() {
        return entity;
    }

    public void setEntity(ProgramEntity entity) {
        this.entity = entity;
    }
}
