package tech.zg.webatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.zg.webatis.entity.TableEntity;
import tech.zg.webatis.mapper.base.BaseMapper;

import java.util.List;

/**
 * 数据库表的mapper类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@Mapper
public interface TableMapper extends BaseMapper<Integer, TableEntity> {
    /**
     * 数据库表批量查询
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param
     * @return list
     */
    List<TableEntity> list(@Param("tableName") String tableName);

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
    TableEntity getByTableName(@Param("tableName")String tableName);
}
