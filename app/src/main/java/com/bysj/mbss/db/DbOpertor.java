package com.bysj.mbss.db;

import com.bysj.mbss.common.MyApplication;
import com.bysj.mbss.entity.ProgramEntity;
import com.bysj.mbss.entity.TableEntity;

import org.xutils.ex.DbException;

import java.util.Collections;
import java.util.List;

/**
 * 数据库处理类
 * Created by WZG on 2016/6/7.
 */
public class DbOpertor {

    /**
     * 得到表格数据
     *
     * @param type
     * @param userId
     * @return
     */
    public static List<TableEntity> getTableBy(int type, String userId) {
        try {
            return MyApplication.db.selector(TableEntity.class).where("type", "==", type).and("UserId", "==", userId)
                    .findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 得到表下面的所有数据
     *
     * @param tableid
     * @return
     */
    public static List<ProgramEntity> getProgramData(int tableid) {
        try {
            return MyApplication.db.selector(ProgramEntity.class).where("tableId", "==", tableid).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 删除本地table数据
     *
     * @param tableEntity
     */
    public static void deleteTable(TableEntity tableEntity) {
        try {
            MyApplication.db.executeUpdateDelete("delete from ProgramEntity where tableId=" + tableEntity.getIds());
            MyApplication.db.delete(tableEntity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否本地存在tab数据
     *
     * @param tableEntity
     * @return
     */
    public static boolean exit(TableEntity tableEntity) {
        try {
            return MyApplication.db.selector(TableEntity.class).where("ids", "==", tableEntity.getIds()).findFirst()
                    == null ? false : true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

}
