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
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public interface GeneratorService {

    /**
     * 生成代码
     * <p>
     *
     * @param tableNames 表名
     * @return byte[]
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    byte[] genCode(Integer dbId, String[] tableNames) throws Exception;


    /**
     * 分页查询数据库中的表
     * <p>
     *
     * @param tableName 表名
     * @param dbId      数据库id
     * @return Pager
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    List<TableBean> list(Integer dbId, String tableName) throws Exception;
}
