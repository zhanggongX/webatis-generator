package tech.zg.webatis.service;

import org.springframework.jdbc.core.JdbcTemplate;
import tech.zg.webatis.entity.WebatisDatabaseEntity;

/**
 * Webatis 项目获取 jdbcTemplate
 * <p>
 *
 * @author: 张弓
 * @date: 2018/7/21
 * @version: 1.0.0
 */
public interface WebatisJdbcTemplateService {

    /**
     * 获取jdbcTemplate
     * <p>
     * @author: 张弓
     * @date: 2018/7/21
     * @version: 1.0.0
     *
     * @param webatisDatabaseEntity
     * @return JdbcTemplate
     */
    JdbcTemplate getJdbcTemplate(WebatisDatabaseEntity webatisDatabaseEntity);

    /**
     * 删除异常的数据源和jdbcTemplate
     * <p>
     *
     * @param dbId
     * @author: 张弓
     * @date: 2018/7/21
     * @version: 1.0.0
     */
    void deleteDataSourceAndJdbcTemplate(Integer dbId);
}
