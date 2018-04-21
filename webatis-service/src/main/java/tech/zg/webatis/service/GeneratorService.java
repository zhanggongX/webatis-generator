package tech.zg.webatis.service;

import org.springframework.jdbc.core.JdbcTemplate;
import tech.zg.webatis.bean.ColumnBean;
import tech.zg.webatis.bean.TableBean;
import tech.zg.webatis.pager.Pager;

import java.beans.PropertyVetoException;
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
    byte[] genCode(Integer dbId, String[] tableNames) throws Exception;


    /**
     * 分页查询数据库中的表
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param tableName 表名
     * @param dbId 数据库id
     * @return Pager
     */
    List<TableBean> list(Integer dbId, String tableName) throws Exception;

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
    TableBean getTableInfoByTableName(JdbcTemplate jdbcTemplate, String tableName);

    List<ColumnBean> queryColumnInfoByTableName(JdbcTemplate jdbcTemplate, String tableName);
}
