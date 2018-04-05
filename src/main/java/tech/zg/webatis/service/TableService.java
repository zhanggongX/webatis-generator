package tech.zg.webatis.service;

import tech.zg.webatis.entity.TableEntity;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.service.base.BaseService;

/**
 * 数据库表的服务类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */

public interface TableService extends BaseService<Integer, TableEntity> {

    /**
     * 分页查询数据库中的表
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param pager 分页信息
     * @param tableName 表名
     * @return Pager
     */
    Pager listByPager(Pager pager, String tableName);

    /**
     * 通过表名，查询表信息
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param tableName
     * @return TableEntity
     */
    TableEntity getByTableName(String tableName);
}
