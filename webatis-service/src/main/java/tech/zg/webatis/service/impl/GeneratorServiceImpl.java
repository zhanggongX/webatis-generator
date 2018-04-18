package tech.zg.webatis.service.impl;

/**
 * 代码生成服务
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import tech.zg.webatis.bean.ColumnBean;
import tech.zg.webatis.bean.TableBean;
import tech.zg.webatis.common.DateUtils;
import tech.zg.webatis.common.WebatisConstants;
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.mapper.WebatisDatabaseMapper;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerHelper;
import tech.zg.webatis.service.GenUtils;
import tech.zg.webatis.service.GeneratorService;

import java.beans.PropertyVetoException;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service("generatorService")
public class GeneratorServiceImpl implements GeneratorService{

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorServiceImpl.class);

    @Autowired
    private WebatisDatabaseMapper webatisDatabaseMapper;

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
    public byte[] genCode(String[] tableNames) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for(String tableName : tableNames){
            //查询表信息
            TableBean tableEntity = getByTableName(tableName);
            //查询列信息
            List<ColumnBean> columns = queryColumnsByTableName(tableName);
            //生成代码
            GenUtils.generatorCode(tableEntity, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 分页查询数据库中的表
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
    public List<TableBean> list(Integer dbId, String tableName) throws PropertyVetoException {

        WebatisDatabaseEntity webatisDatabaseEntity = webatisDatabaseMapper.get(dbId);
        JdbcTemplate jdbcTemplate = getJdbcTemplate(webatisDatabaseEntity);
        StringBuffer querySql = new StringBuffer();
        querySql.append("select t.table_name, t.engine, t.table_comment, t.create_time from information_schema.tables t ");
        querySql.append("where table_schema = (select database())");
        if(StringUtils.isNotEmpty(tableName)){
            querySql.append("and table_name = ").append(tableName);
        }
        LOGGER.info("query SQL is : " + querySql.toString());
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(querySql.toString());
        List<TableBean> tableBeans = new ArrayList<>();
        TableBean tableBean = new TableBean();
        for(Map<String, Object> row : rows){
            tableBean.setTableName((String) row.get("table_name"));
            tableBean.setEngine((String) row.get("engine"));
            tableBean.setTableComment((String) row.get("table_comment"));
            Timestamp timestamp = (Timestamp) row.get("create_time");
            tableBean.setCreateTime(DateUtils.formatDate(timestamp, null));
            tableBeans.add(tableBean);
        }
        return tableBeans;
    }

    private JdbcTemplate getJdbcTemplate(WebatisDatabaseEntity webatisDatabaseEntity) throws PropertyVetoException {
        StringBuffer urlBuf = new StringBuffer("jdbc:");
        if(webatisDatabaseEntity.getType().equals(WebatisConstants.MYSQL)){
            urlBuf.append("mysql://");
        }else if(webatisDatabaseEntity.getType().equals(WebatisConstants.ORACLE)) {
            //
        }
        urlBuf.append(webatisDatabaseEntity.getUrl()).append(":").append(webatisDatabaseEntity.getPort()).append("/");
        urlBuf.append(webatisDatabaseEntity.getName());
        LOGGER.info("jdbcUrl is : " + urlBuf.toString());
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl(urlBuf.toString());
        dataSource.setUser(webatisDatabaseEntity.getUsername());
        dataSource.setPassword(webatisDatabaseEntity.getPassword());
        dataSource.setInitialPoolSize(3);
        dataSource.setMaxPoolSize(10);
        dataSource.setMaxStatements(10);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
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
    public TableBean getByTableName(String tableName) {
        //return tableMapper.getByTableName(tableName);
        return null;
    }

    @Override
    public List<ColumnBean> queryColumnsByTableName(String tableName) {
        //return columnMapper.queryColumnsByTableName(tableName);
        return null;
    }
}
