package tech.zg.webatis.service.impl;

/**
 * 代码生成服务
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.zg.webatis.common.GenUtils;
import tech.zg.webatis.entity.ColumnEntity;
import tech.zg.webatis.entity.TableEntity;
import tech.zg.webatis.service.ColumnService;
import tech.zg.webatis.service.GeneratorService;
import tech.zg.webatis.service.TableService;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Service("generatorService")
public class GeneratorServiceImpl implements GeneratorService{

    @Autowired
    private TableService tableService;
    @Autowired
    private ColumnService columnService;

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
            TableEntity tableEntity = tableService.getByTableName(tableName);
            //查询列信息
            List<ColumnEntity> columns = columnService.queryColumnsByTableName(tableName);
            //生成代码
            GenUtils.generatorCode(tableEntity, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
