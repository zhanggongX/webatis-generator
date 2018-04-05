package tech.zg.webatis.mapper;

import org.apache.ibatis.annotations.Param;
import tech.zg.webatis.entity.ColumnEntity;
import tech.zg.webatis.mapper.base.BaseMapper;

import java.util.List;

/**
 * 数据列的Mapper类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public interface ColumnMapper extends BaseMapper<Integer, ColumnEntity>{
    /**
     * 根据表名，查询当前表的所有列的信息。
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param tableName
     * @return List<ColumnEntity>
     */
    List<ColumnEntity> queryColumnsByTableName(@Param("tableName") String tableName);
}
