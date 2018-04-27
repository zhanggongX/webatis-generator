package tech.zg.webatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.zg.webatis.entity.WebatisDatabaseEntity;

import java.util.List;

/**
 * 数据库表的Mapper
 * <p>
 *
 * @author: 张弓
 * @version: 1.0.0
 */
@Mapper
public interface WebatisDatabaseMapper extends BaseMapper<Integer, WebatisDatabaseEntity> {

    /**
     * 方法说明
     * <p>
     *
     * @param dbsName 数据库名称
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date :
     */
    List<WebatisDatabaseEntity> list(@Param("dbsName") String dbsName);
}
