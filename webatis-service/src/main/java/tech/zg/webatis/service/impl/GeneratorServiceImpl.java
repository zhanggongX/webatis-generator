package tech.zg.webatis.service.impl;

/**
 * 代码生成服务
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import tech.zg.webatis.bean.ColumnBean;
import tech.zg.webatis.bean.TableBean;
import tech.zg.webatis.common.DateUtils;
import tech.zg.webatis.common.WebatisConstants;
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.mapper.WebatisDatabaseMapper;
import tech.zg.webatis.service.GenService;
import tech.zg.webatis.service.GeneratorService;
import tech.zg.webatis.service.WebatisContextService;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Service("generatorService")
public class GeneratorServiceImpl implements GeneratorService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorServiceImpl.class);

    @Autowired
    private WebatisDatabaseMapper webatisDatabaseMapper;
    @Autowired
    private WebatisContextService webatisContextService;

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
    @Override
    public byte[] genCode(Integer dbId, String[] tableNames) {

        WebatisDatabaseEntity webatisDatabaseEntity = webatisDatabaseMapper.get(dbId);
        JdbcTemplate jdbcTemplate = getJdbcTemplate(webatisDatabaseEntity);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            TableBean tableBean = getTableInfoByTableName(jdbcTemplate, tableName);
            //查询列信息
            List<ColumnBean> columns = queryColumnInfoByTableName(jdbcTemplate, tableName);
            //生成代码
            GenService.generatorCode(tableBean, columns, webatisDatabaseEntity.getPath(), zip);
        }
        GenService.generatorBaseCode(webatisDatabaseEntity.getPath(), zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询数据库中的表
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param tableName 表名
     * @param dbId 数据库id
     * @return Pager
     */
    @Override
    public List<TableBean> list(Integer dbId, String tableName) throws Exception {
        List<TableBean> tableBeans = new ArrayList<>();
        try {
            WebatisDatabaseEntity webatisDatabaseEntity = webatisDatabaseMapper.get(dbId);
            JdbcTemplate jdbcTemplate = getJdbcTemplate(webatisDatabaseEntity);
            StringBuffer querySql = new StringBuffer();
            querySql.append("select t.table_name, t.engine, t.table_comment, t.create_time from information_schema.tables t ");
            querySql.append("where table_schema = (select database()) ");
            if (StringUtils.isNotEmpty(tableName)) {
                querySql.append("and table_name like '%").append(tableName).append("%'");
            }
            LOGGER.info("query SQL is : " + querySql.toString());
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(querySql.toString());
            TableBean tableBean = null;
            for (Map<String, Object> row : rows) {
                tableBean = new TableBean();
                tableBean.setTableName((String) row.get("table_name"));
                tableBean.setEngine((String) row.get("engine"));
                tableBean.setTableComment((String) row.get("table_comment"));
                Timestamp timestamp = (Timestamp) row.get("create_time");
                tableBean.setCreateTime(DateUtils.formatDate(timestamp, null));
                tableBeans.add(tableBean);
            }
        } catch (Exception e) {
            LOGGER.error("获取数据表连接失败:", e);
            throw new Exception();
        }
        return tableBeans;
    }

    /**
     * 根据数据库对象获取jdbcTemplate
     * <p>
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     *
     * @param webatisDatabaseEntity 配置的数据库对象
     * @return JdbcTemplate
     */
    private JdbcTemplate getJdbcTemplate(WebatisDatabaseEntity webatisDatabaseEntity) {

        //根据主键的唯一性，拼装jdbcTemplate的beanName
        String jdbcTemplateBeanName = WebatisConstants.JDBC_TEMPLATE_NAME + webatisDatabaseEntity.getId();
        JdbcTemplate jdbcTemplate = null;
        try {
            jdbcTemplate = webatisContextService.getBean(jdbcTemplateBeanName, JdbcTemplate.class);
        } catch (Exception e) {
            //如果抛出异常，则说明还没有注入的spring容器中。则注入。
            regiseterJdbcTemplate(webatisDatabaseEntity);
        }
        //再重新获取一次
        jdbcTemplate = webatisContextService.getBean(jdbcTemplateBeanName, JdbcTemplate.class);
        return jdbcTemplate;
    }

    /**
     * 向spring容器中，注入jdbcTemplate
     * <p>
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     *
     * @param webatisDatabaseEntity 配置的数据库实体
     */
    private void regiseterJdbcTemplate(WebatisDatabaseEntity webatisDatabaseEntity) {
        //首先从spring容器中获取数据源
        ComboPooledDataSource dataSource = getDataSource(webatisDatabaseEntity);
        //根据主键的唯一性，拼装jdbcTemplate的beanName
        String beanName = WebatisConstants.JDBC_TEMPLATE_NAME + webatisDatabaseEntity.getId();
        Map<String, Object> varMap = new HashMap();
        varMap.put("dataSource", dataSource);
        //把数据源传入到jdbcTemplate中并向spring容器注册。
        webatisContextService.registerBean(beanName, varMap, JdbcTemplate.class);
    }

    /**
     * 获取数据源
     * <p>
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     *
     * @param webatisDatabaseEntity
     * @return ComboPooledDataSource
     */
    private ComboPooledDataSource getDataSource(WebatisDatabaseEntity webatisDatabaseEntity) {
        //根据主键的唯一性，拼装database的beanName
        String beanName = WebatisConstants.DATA_SOURCE_BEAN_NAME + webatisDatabaseEntity.getId();
        ComboPooledDataSource comboPooledDataSource = null;
        try {
            //首先从spring容器中取数据源。
            comboPooledDataSource = webatisContextService.getBean(beanName, ComboPooledDataSource.class);
        } catch (Exception e) {
            //如果抛出异常，则表示spring容器中没有数据源，则注册数据源。
            registerDataSource(webatisDatabaseEntity);
        }
        //再重新获取一次数据源
        comboPooledDataSource = webatisContextService.getBean(beanName, ComboPooledDataSource.class);
        return comboPooledDataSource;
    }

    /**
     * 向spring容器注册数据源
     * <p>
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     *
     * @param webatisDatabaseEntity
     */
    private void registerDataSource(WebatisDatabaseEntity webatisDatabaseEntity) {
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
        varMap.put("maxStatements", 10);
        //向spring容器注册数据源
        webatisContextService.registerBean(beanName, varMap, ComboPooledDataSource.class);
    }

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
    @Override
    public TableBean getTableInfoByTableName(JdbcTemplate jdbcTemplate, String tableName) {

        StringBuffer querySql = new StringBuffer();
        querySql.append("select t.table_name, t.engine, t.table_comment, t.create_time from information_schema.tables t ");
        querySql.append("where table_schema = (select database()) ");
        querySql.append("and table_name = ? ");

        LOGGER.info("Query sql is : " + querySql.toString());

        TableBean tableBean = jdbcTemplate.queryForObject(querySql.toString(), new RowMapper<TableBean>() {
            @Override
            public TableBean mapRow(ResultSet rs, int rowNum) throws SQLException {
                TableBean tableBean = new TableBean();
                tableBean.setTableName((String) rs.getString("table_name"));
                tableBean.setEngine((String) rs.getString("engine"));
                tableBean.setTableComment((String) rs.getString("table_comment"));
                Timestamp timestamp = (Timestamp) rs.getTimestamp("create_time");
                tableBean.setCreateTime(DateUtils.formatDate(timestamp, null));
                return tableBean;
            }
        }, tableName);
        return tableBean;
    }

    @Override
    public List<ColumnBean> queryColumnInfoByTableName(JdbcTemplate jdbcTemplate, String tableName) {

        List<ColumnBean> columnBeans = new ArrayList<>();
        StringBuffer querySql = new StringBuffer();
        querySql.append("select col.column_name, col.data_type, col.column_comment, col.column_key, col.extra from information_schema.columns col ");
        querySql.append("where table_schema = (select database()) ");
        if (StringUtils.isNotEmpty(tableName)) {
            querySql.append("and table_name = '").append(tableName).append("'");
        }
        LOGGER.info("Query sql is : " + querySql.toString());
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(querySql.toString());
        ColumnBean columnBean = null;
        for (Map<String, Object> row : rows) {
            columnBean = new ColumnBean();
            columnBean.setColumnName((String) row.get("column_name"));
            columnBean.setDataType((String) row.get("data_type"));
            columnBean.setColumnComment((String) row.get("column_comment"));
            columnBean.setColumnKey((String) row.get("column_key"));
            columnBean.setExtra((String) row.get("extra"));
            columnBeans.add(columnBean);
        }
        return columnBeans;
    }
}
