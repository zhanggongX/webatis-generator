package tech.zg.webatis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 读取数据源配置
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@Configuration
@Component()
public class DataSourceConfig {

    /**
     * 驱动全路径名
     */
    @Value("${druid.dataSource.driverClassName}")
    private String driverClassName;

    /**
     * JDBC连接地址
     */
    @Value("${druid.dataSource.master.url}")
    private String masterJdbcUrl;

    /**
     * 访问账户
     */
    @Value("${druid.dataSource.master.username}")
    private String masterUsername;

    /**
     * 访问密码
     */
    @Value("${druid.dataSource.master.password}")
    private String masterPassword;

    /**
     * JDBC连接地址
     * @Value("${druid.dataSource.slave.url}")
     */
    private String slaveJdbcUrl;

    /**
     * 访问账户
     * @Value("${druid.dataSource.slave.username}")
     */
    private String slaveUsername;

    /**
     * 访问密码
     * @Value("${druid.dataSource.slave.password}")
     */
     private String slavePassword;


    /**
     * 连接池最大值
     */
    @Value("${druid.dataSource.maxActive}")
    private int maxActive;

    /**
     * 连接超时时间
     */
    @Value("${druid.dataSource.maxWait}")
    private long maxWait;

    /**
     * 缓存编译后函数的key
     */
    @Value("${druid.dataSource.cachePrepStmts}")
    private boolean cachePrepStmts;

    /**
     * cachePrepStmts设置是否对预编译使用local cache
     */
    @Value("${druid.dataSource.useServerPrepStmts}")
    private boolean useServerPrepStmts;

    /**
     * prepStmtCacheSize指定了local cache的大小，使用了LRU进行逐出
     */
    @Value("${druid.dataSource.prepStmtCacheSize}")
    private int prepStmtCacheSize;

    /**
     * prepStmtCacheSqlLimit长度限制，默认256。超过该长度后，不使用预编译
     */
    @Value("${druid.dataSource.prepStmtCacheSqlLimit}")
    private int prepStmtCacheSqlLimit;


    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getMasterJdbcUrl() {
        return masterJdbcUrl;
    }

    public void setMasterJdbcUrl(String masterJdbcUrl) {
        this.masterJdbcUrl = masterJdbcUrl;
    }

    public String getMasterUsername() {
        return masterUsername;
    }

    public void setMasterUsername(String masterUsername) {
        this.masterUsername = masterUsername;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public String getSlaveJdbcUrl() {
        return slaveJdbcUrl;
    }

    public void setSlaveJdbcUrl(String slaveJdbcUrl) {
        this.slaveJdbcUrl = slaveJdbcUrl;
    }

    public String getSlaveUsername() {
        return slaveUsername;
    }

    public void setSlaveUsername(String slaveUsername) {
        this.slaveUsername = slaveUsername;
    }

    public String getSlavePassword() {
        return slavePassword;
    }

    public void setSlavePassword(String slavePassword) {
        this.slavePassword = slavePassword;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isCachePrepStmts() {
        return cachePrepStmts;
    }

    public void setCachePrepStmts(boolean cachePrepStmts) {
        this.cachePrepStmts = cachePrepStmts;
    }

    public boolean isUseServerPrepStmts() {
        return useServerPrepStmts;
    }

    public void setUseServerPrepStmts(boolean useServerPrepStmts) {
        this.useServerPrepStmts = useServerPrepStmts;
    }

    public int getPrepStmtCacheSize() {
        return prepStmtCacheSize;
    }

    public void setPrepStmtCacheSize(int prepStmtCacheSize) {
        this.prepStmtCacheSize = prepStmtCacheSize;
    }

    public int getPrepStmtCacheSqlLimit() {
        return prepStmtCacheSqlLimit;
    }

    public void setPrepStmtCacheSqlLimit(int prepStmtCacheSqlLimit) {
        this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
    }
}
