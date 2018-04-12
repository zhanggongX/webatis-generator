package tech.zg.webatis.mapper;

import org.apache.ibatis.annotations.Param;
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.mapper.base.BaseMapper;

import java.util.List;

/**
 * 类说明
 * <p>
 * @author: 张弓
 * @version: 1.0.0
 */
public interface WebatisDatabaseMapper extends BaseMapper<Integer, WebatisDatabaseEntity>{

    List<WebatisDatabaseEntity> list(@Param("dbsName") String dbsName);
}
