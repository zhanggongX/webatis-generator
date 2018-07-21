package tech.zg.webatis.service;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.collections.map.HashedMap;
import tech.zg.webatis.common.WebatisConstants;
import tech.zg.webatis.entity.WebatisDatabaseEntity;

import java.util.Map;

/**
 * 动态数据源服务
 * <p>
 * @author: 张弓
 * @date: 2018/7/21
 * @version: 1.0.0
 */
public interface DynamicDateSourceService {

    /**
     * 从spring容器获取数据源
     * <p>
     *
     * @param webatisDatabaseEntity
     * @return ComboPooledDataSource
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     */
    public ComboPooledDataSource getDataSource(WebatisDatabaseEntity webatisDatabaseEntity);

    /**
     * 向spring容器注册数据源
     * <p>
     *
     * @param webatisDatabaseEntity
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     */
    public boolean registerDataSource(WebatisDatabaseEntity webatisDatabaseEntity);
}
