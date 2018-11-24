package tech.zg.webatis.service.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tech.zg.webatis.common.WebatisConstants;
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.service.DynamicDateSourceService;
import tech.zg.webatis.service.DynamicJdbcTemplateService;
import tech.zg.webatis.service.WebatisContextService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取jdbcTemplate的服务
 * <p>
 *
 * @author: 张弓
 * @date: 2018/7/21
 * @version: 1.0.0
 */
@Service("dynamicJdbcTemplateService")
public class DynamicJdbcTemplateServiceImpl implements DynamicJdbcTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicJdbcTemplateServiceImpl.class);

    @Autowired
    private WebatisContextService webatisContextService;
    @Autowired
    private DynamicDateSourceService dynamicDateSourceService;

    /**
     * 注册JDBC模板
     * <p>
     *
     * @param webatisDatabaseEntity
     * @return JdbcTemplate
     * @author: 张弓
     * @date: 2018/7/21
     * @version: 1.0.0
     */
    @Override
    public JdbcTemplate getJdbcTemplate(WebatisDatabaseEntity webatisDatabaseEntity) {

        //根据主键的唯一性，拼装jdbcTemplate的beanName
        String jdbcTemplateBeanName = WebatisConstants.JDBC_TEMPLATE_NAME + webatisDatabaseEntity.getId();
        JdbcTemplate jdbcTemplate = null;

        try {
            jdbcTemplate = webatisContextService.getBean(jdbcTemplateBeanName, JdbcTemplate.class);
        } catch (Exception e) {
            //如果抛出异常，则说明还没有注入的spring容器中。则注入。
            LOGGER.error("获取JdbcTemplate异常: {}", e);
        }
        return jdbcTemplate;
    }

    /**
     * 向spring容器中，注入jdbcTemplate
     * <p>
     *
     * @param webatisDatabaseEntity 配置的数据库实体
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     */
    @Override
    public boolean regiseterJdbcTemplate(WebatisDatabaseEntity webatisDatabaseEntity, DataSource dataSource) {

        try {
            //根据主键的唯一性，拼装jdbcTemplate的beanName
            String beanName = WebatisConstants.JDBC_TEMPLATE_NAME + webatisDatabaseEntity.getId();
            Map<String, Object> varMap = new HashMap();
            varMap.put("dataSource", dataSource);
            //把数据源传入到jdbcTemplate中并向spring容器注册。
            webatisContextService.registerBean(beanName, varMap, JdbcTemplate.class);
            return true;
        }catch (Exception e){
            LOGGER.error("注册JdbcTemplate异常: {}", e);
            return false;
        }
    }
}
