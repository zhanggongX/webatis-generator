package tech.zg.webatis.service;

import tech.zg.webatis.bean.ColumnBean;
import tech.zg.webatis.bean.TableBean;
import tech.zg.webatis.pager.Pager;

import java.util.List;

/**
 * 代码生成服务
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public interface GeneratorService{

    /**
     * 生成代码
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param tableNames 表名
     * @return byte[]
     */
    byte[] genCode(String[] tableNames);


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
    TableBean getByTableName(String tableName);

    List<ColumnBean> queryColumnsByTableName(String tableName);
}
