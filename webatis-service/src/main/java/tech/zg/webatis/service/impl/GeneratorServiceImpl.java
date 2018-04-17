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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tech.zg.webatis.bean.ColumnBean;
import tech.zg.webatis.bean.TableBean;
import tech.zg.webatis.common.WebatisConstants;
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.mapper.WebatisDatabaseMapper;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerHelper;
import tech.zg.webatis.service.GenUtils;
import tech.zg.webatis.service.GeneratorService;

import java.beans.PropertyVetoException;
import java.io.ByteArrayOutputStream;
import java.util.List;
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
    public Pager list(Integer dbId, String tableName) throws PropertyVetoException {

        WebatisDatabaseEntity webatisDatabaseEntity = webatisDatabaseMapper.get(dbId);
        JdbcTemplate jdbcTemplate = getJdbcTemplate(webatisDatabaseEntity);
        //jdbcTemplate.

        //jdbcTemplate.
        //PagerHelper.startPage(pager);
        //tableMapper.list(tableName);
        return null;
    }

    private JdbcTemplate getJdbcTemplate(WebatisDatabaseEntity webatisDatabaseEntity) throws PropertyVetoException {
        StringBuffer urlBuf = new StringBuffer("jdbc:");
        if(webatisDatabaseEntity.getType() == WebatisConstants.MYSQL){
            urlBuf.append("mysql://");
        }else if(webatisDatabaseEntity.getType() == WebatisConstants.ORACLE){
            //
        }
        urlBuf.append(webatisDatabaseEntity.getUrl()).append(":").append(webatisDatabaseEntity.getPort()).append("/");
        urlBuf.append(webatisDatabaseEntity.getName()).append("?useUnicode=true&characterEncoding=utf-8");
        LOGGER.info("jdbcUrl is" + urlBuf.toString());
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(webatisDatabaseEntity.getUsername());
        dataSource.setPassword(webatisDatabaseEntity.getPassword());
        dataSource.setJdbcUrl(urlBuf.toString());
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setInitialPoolSize(1);
        dataSource.setMaxPoolSize(10);
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
