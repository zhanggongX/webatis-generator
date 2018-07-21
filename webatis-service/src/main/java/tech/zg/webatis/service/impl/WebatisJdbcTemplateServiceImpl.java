package tech.zg.webatis.service.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
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
import tech.zg.webatis.service.WebatisJdbcTemplateService;

import java.sql.SQLException;

/**
 * Webatis 项目获取 jdbcTemplate
 * <p>
 *
 * @author: 张弓
 * @date: 2018/7/21
 * @version: 1.0.0
 */
@Service("webatisJdbcTemplateService")
public class WebatisJdbcTemplateServiceImpl implements WebatisJdbcTemplateService {

    Logger LOGGER = LoggerFactory.getLogger(WebatisJdbcTemplateServiceImpl.class);

    @Autowired
    private DynamicDateSourceService dynamicDateSourceService;
    @Autowired
    private DynamicJdbcTemplateService dynamicJdbcTemplateService;
    @Autowired
    private WebatisContextService webatisContextService;

    /**
     * 获取jdbcTemplate
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

        ComboPooledDataSource comboPooledDataSource = null;
        JdbcTemplate jdbcTemplate = null;
        // 获取数据源，最大可获取三次
        for (int i = 0; i < WebatisConstants.REGISTER_BEAN_MOST_COUNT; i++) {

            comboPooledDataSource = dynamicDateSourceService.getDataSource(webatisDatabaseEntity);
            if (comboPooledDataSource == null) {
                LOGGER.error("第" + (i + 1) + "次获取数据源失败!");
                dynamicDateSourceService.registerDataSource(webatisDatabaseEntity);
            } else {
                LOGGER.error("第" + (i + 1) + "次获取数据源成功!");
                break;
            }
        }

        // JdbcTemplate，最大可获取三次
        if (comboPooledDataSource == null) {
            return null;
        } else {
            for (int i = 0; i < WebatisConstants.REGISTER_BEAN_MOST_COUNT; i++) {

                jdbcTemplate = dynamicJdbcTemplateService.getJdbcTemplate(webatisDatabaseEntity);
                if (jdbcTemplate == null) {
                    LOGGER.error("第" + (i + 1) + "次获取JdbcTemplate失败!");
                    dynamicJdbcTemplateService.regiseterJdbcTemplate(webatisDatabaseEntity, comboPooledDataSource);
                } else {
                    jdbcTemplate.setQueryTimeout(5);
                    LOGGER.error("第" + (i + 1) + "次获取JdbcTemplate成功!");
                    break;
                }
            }
        }

        return jdbcTemplate;
    }

    /**
     * 删除异常的数据源和jdbcTemplate
     * <p>
     *
     * @param dbId
     * @author: 张弓
     * @date: 2018/7/21
     * @version: 1.0.0
     */
    @Override
    public void deleteDataSourceAndJdbcTemplate(Integer dbId) {

        String datasourceBeanName = WebatisConstants.DATA_SOURCE_BEAN_NAME + dbId;
        String jdbcTemplateBeanName = WebatisConstants.JDBC_TEMPLATE_NAME + dbId;

        try{
            JdbcTemplate jdbcTemplate = webatisContextService.getBean(jdbcTemplateBeanName, JdbcTemplate.class);
            if(jdbcTemplate != null){
                jdbcTemplate.setDataSource(null);
                webatisContextService.deleteBean(jdbcTemplateBeanName);
            }
        }catch (Exception e){
        }

        try {
            ComboPooledDataSource dataSource = webatisContextService.getBean(datasourceBeanName, ComboPooledDataSource.class);
            if(dataSource != null){
                dataSource.close();
                webatisContextService.deleteBean(datasourceBeanName);
            }
        }catch (Exception e){
        }
    }
}
