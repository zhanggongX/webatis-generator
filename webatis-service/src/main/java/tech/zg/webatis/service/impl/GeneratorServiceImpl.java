package tech.zg.webatis.service.impl;

/**
 * 代码生成服务
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import tech.zg.webatis.bean.ColumnBean;
import tech.zg.webatis.bean.TableBean;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerHelper;
import tech.zg.webatis.service.GenUtils;
import tech.zg.webatis.service.GeneratorService;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Service("generatorService")
public class GeneratorServiceImpl implements GeneratorService{

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
     * @param pager 分页信息
     * @param tableName 表名
     * @return Pager
     */
    @Override
    public Pager listByPager(Pager pager, String tableName) {
        PagerHelper.startPage(pager);
        //tableMapper.list(tableName);
        return pager;
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
