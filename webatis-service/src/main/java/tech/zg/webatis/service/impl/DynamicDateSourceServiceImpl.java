package tech.zg.webatis.service.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.zg.webatis.common.WebatisConstants;
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.service.DynamicDateSourceService;
import tech.zg.webatis.service.WebatisContextService;

import java.util.Map;

/**
 * 动态数据源服务
 * <p>
 *
 * @author: 张弓
 * @date: 2018/7/21
 * @version: 1.0.0
 */
@Service("dynamicDateSourceService")
public class DynamicDateSourceServiceImpl implements DynamicDateSourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicJdbcTemplateServiceImpl.class);

    @Autowired
    private WebatisContextService webatisContextService;

    /**
     * 获取数据源
     * <p>
     *
     * @param webatisDatabaseEntity
     * @return ComboPooledDataSource
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     */
    @Override
    public ComboPooledDataSource getDataSource(WebatisDatabaseEntity webatisDatabaseEntity) {

        ComboPooledDataSource comboPooledDataSource = null;
        //根据主键的唯一性，拼装database的beanName
        String beanName = WebatisConstants.DATA_SOURCE_BEAN_NAME + webatisDatabaseEntity.getId();

        try {
            //首先从spring容器中取数据源。
            comboPooledDataSource = webatisContextService.getBean(beanName, ComboPooledDataSource.class);
        } catch (Exception e) {
            //如果抛出异常，则表示spring容器中没有数据源，则注册数据源。
            LOGGER.error("获取数据源异常: {}", e);
        }

        return comboPooledDataSource;
    }

    /**
     * 向spring容器注册数据源
     * <p>
     *
     * @param webatisDatabaseEntity
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     */
    @Override
    public boolean registerDataSource(WebatisDatabaseEntity webatisDatabaseEntity) {

        try {

            //拼装jdbc url
            StringBuffer urlBuf = new StringBuffer("jdbc:");
            if (webatisDatabaseEntity.getType().equals(WebatisConstants.MYSQL)) {
                urlBuf.append("mysql://");
            } else if (webatisDatabaseEntity.getType().equals(WebatisConstants.ORACLE)) {
            }
            urlBuf.append(webatisDatabaseEntity.getUrl()).append(":").append(webatisDatabaseEntity.getPort()).append("/");
            urlBuf.append(webatisDatabaseEntity.getName());
            LOGGER.info("jdbcUrl is : " + urlBuf.toString());
            //获取bean名称
            String beanName = WebatisConstants.DATA_SOURCE_BEAN_NAME + webatisDatabaseEntity.getId();
            Map<String, Object> varMap = new HashedMap();
            varMap.put("driverClass", "com.mysql.jdbc.Driver");
            varMap.put("jdbcUrl", urlBuf.toString());
            varMap.put("user", webatisDatabaseEntity.getUsername());
            varMap.put("password", webatisDatabaseEntity.getPassword());
            varMap.put("initialPoolSize", 3);
            varMap.put("maxPoolSize", 10);
            varMap.put("maxStatements", 100);
            // 从数据库获取连接失败后重试次数
            varMap.put("acquireRetryAttempts", 2);
            // 重试间隔时间
            varMap.put("acquireRetryDelay", 500);
            // -获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效, 保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试, 获取连接失败后该数据源将申明已断开并永久关闭。Default: false
            varMap.put("breakAfterAcquireFailure", true);
            // 当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException,如设为0则无限期等待。单位毫秒。Default: 0
             varMap.put("checkoutTimeout", 100);
            // 如果设为true那么在取得连接的同时将校验连接的有效性。Default: false
            varMap.put("testConnectionOnCheckin", true);

            //向spring容器注册数据源
            webatisContextService.registerBean(beanName, varMap, ComboPooledDataSource.class);

            return true;
        }catch (Exception e){
            LOGGER.error("注册数据源异常!");
            return false;
        }
    }

}
