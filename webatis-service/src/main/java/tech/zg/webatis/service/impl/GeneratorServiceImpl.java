package tech.zg.webatis.service.impl;

/**
 * 代码生成服务
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */

import com.alibaba.fastjson.JSON;
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
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.exception.BaseException;
import tech.zg.webatis.exception.BaseExceptionCode;
import tech.zg.webatis.exception.BaseRunTimeException;
import tech.zg.webatis.mapper.WebatisDatabaseMapper;
import tech.zg.webatis.service.DynamicJdbcTemplateService;
import tech.zg.webatis.service.GeneratorService;
import tech.zg.webatis.service.WebatisJdbcTemplateService;
import tech.zg.webatis.util.GeneratorUtils;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private WebatisJdbcTemplateService webatisJdbcTemplateService;

    /**
     * 生成代码
     * <p>
     *
     * @param tableNames 表名
     * @return byte[]
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    @Override
    public byte[] genCode(Integer dbId, String[] tableNames) {

        WebatisDatabaseEntity webatisDatabaseEntity = webatisDatabaseMapper.get(dbId);
        JdbcTemplate jdbcTemplate = webatisJdbcTemplateService.getJdbcTemplate(webatisDatabaseEntity);
        if (jdbcTemplate == null) {
            throw new BaseRunTimeException(BaseExceptionCode.DATASOURCE_CONFIG_FAIL);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            TableBean tableBean = getTableInfoByTableName(jdbcTemplate, tableName);
            //查询列信息
            List<ColumnBean> columns = queryColumnInfoByTableName(jdbcTemplate, tableName);
            //生成代码
            GeneratorUtils.generatorCode(tableBean, columns, webatisDatabaseEntity.getPath(), zip);
        }
        GeneratorUtils.generatorBaseCode(webatisDatabaseEntity.getPath(), zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询数据库中的表
     * <p>
     *
     * @param tableName 表名
     * @param dbId      数据库id
     * @return Pager
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    @Override
    public List<TableBean> list(Integer dbId, String tableName){
        List<TableBean> tableBeans = new ArrayList<>();

        WebatisDatabaseEntity webatisDatabaseEntity = webatisDatabaseMapper.get(dbId);
        JdbcTemplate jdbcTemplate = webatisJdbcTemplateService.getJdbcTemplate(webatisDatabaseEntity);
        if (jdbcTemplate == null) {
            throw new BaseRunTimeException(BaseExceptionCode.DATASOURCE_CONFIG_FAIL);
        }
        StringBuffer querySql = new StringBuffer();
        querySql.append("select t.table_name, t.engine, t.table_comment, t.create_time from information_schema.tables t ");
        querySql.append("where table_schema = (select database()) ");
        if (StringUtils.isNotEmpty(tableName)) {
            querySql.append("and table_name like '%").append(tableName).append("%'");
        }
        LOGGER.info("query SQL is : " + querySql.toString());
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(querySql.toString());
        LOGGER.info("query success !");
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

        LOGGER.info("获取到数据表信息: {}", JSON.toJSONString(tableBeans));
        return tableBeans;
    }

    /**
     * 通过表名，查询表信息
     * <p>
     *
     * @param tableName
     * @return TableEntity
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    private TableBean getTableInfoByTableName(JdbcTemplate jdbcTemplate, String tableName) {

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

    /**
     * 根据表名查询所有的列信息
     * <p>
     *
     * @param jdbcTemplate spring jdbcTemplate模板
     * @param tableName    表名
     * @return List<ColumnBean> 字段信息列表
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date :
     */
    private List<ColumnBean> queryColumnInfoByTableName(JdbcTemplate jdbcTemplate, String tableName) {

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
