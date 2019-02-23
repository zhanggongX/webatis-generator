package tech.zg.webatis.mapper;

import tech.zg.webatis.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色表
 * 
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
@Mapper
public interface RoleMapper extends BaseMapper<Integer, RoleEntity> {
	
}
