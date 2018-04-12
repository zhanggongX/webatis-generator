package tech.zg.webatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * 数据源配置已经SqlSessionFactory创建
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DataSourceMaster {

    public static final String MASTER_TRANSACTION_MANAGER="masterTransactionManager";

    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private MybatisProperties properties;

    /**
     * masterSqlSessionFactory
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param
     * @return SqlSessionFactory
     */
    @Primary
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(masterDataSource());
        factory.setVfs(SpringBootVFS.class);
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }

        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }

        if (StringUtils.hasLength(this.properties.getConfigLocation())) {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(this.properties.getConfigLocation());

            if(resources!=null && resources.length>0){
                factory.setConfigLocation(resources[0]);
            }
        }

        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            factory.setMapperLocations(this.properties.resolveMapperLocations());
        }
        return factory.getObject();
    }

    /**
     * 主数据源配置
     * <p>
     * @author: 张弓
     * @date: 
     * @version: 1.0.0
     *
     * @return DataSource
     */
    @Primary
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        final DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(dataSourceConfig.getDriverClassName());
        druidDataSource.setMaxActive(dataSourceConfig.getMaxActive());
        druidDataSource.setMaxWait(dataSourceConfig.getMaxWait());
        druidDataSource.setUrl(dataSourceConfig.getMasterJdbcUrl());
        druidDataSource.setUsername(dataSourceConfig.getMasterUsername());
        druidDataSource.setPassword(dataSourceConfig.getMasterPassword());
        druidDataSource.setMinIdle(5);
        return druidDataSource;
    }

    /**
     * 事务管理器
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @return DataSource
     */
    @Primary
    @Bean(name = MASTER_TRANSACTION_MANAGER)
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }
}


