package tech.zg.webatis.service;

import com.mchange.v2.c3p0.PooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import tech.zg.webatis.entity.WebatisDatabaseEntity;

import javax.sql.DataSource;

/**
 * 动态jdbcTemplate服务
 * <p>
 *
 * @author: 张弓
 * @date: 2018/7/21
 * @version: 1.0.0
 */
public interface DynamicJdbcTemplateService {

    /**
     * 从spring容器中，获取jdbcTemplate
     * <p>
     *
     * @param webatisDatabaseEntity
     * @return JdbcTemplate
     * @author: 张弓
     * @date: 2018/7/21
     * @version: 1.0.0
     */
    public JdbcTemplate getJdbcTemplate(WebatisDatabaseEntity webatisDatabaseEntity);

    /**
     * 向spring容器中，注入jdbcTemplate
     * <p>
     *
     * @param webatisDatabaseEntity 配置的数据库实体
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     */
    public boolean regiseterJdbcTemplate(WebatisDatabaseEntity webatisDatabaseEntity, DataSource dataSource);
}
