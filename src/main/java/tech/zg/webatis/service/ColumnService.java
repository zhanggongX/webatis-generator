package tech.zg.webatis.service;

import tech.zg.webatis.entity.ColumnEntity;
import tech.zg.webatis.service.base.BaseService;

import java.util.List;

/**
 * 数据表列信息
 * <p>
 * @author: 张弓
 * @date: 2018/2/1
 * @version: 1.0.0
 */
public interface ColumnService extends BaseService<Integer, ColumnEntity>{

    /**
     * 根据表名，查询当前表的所有列的信息。
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param tableName
     * @return List<ColumnEntity>
     */
    List<ColumnEntity> queryColumnsByTableName(String tableName);
}
