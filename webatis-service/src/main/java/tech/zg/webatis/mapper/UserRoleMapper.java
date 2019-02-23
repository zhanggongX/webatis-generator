package tech.zg.webatis.mapper;

import org.apache.ibatis.annotations.Param;
import tech.zg.webatis.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户角色表
 * 
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<Integer, UserRoleEntity> {

    /**
     * 根据userId查询当前用户的所有角色
     * <p>
     *
     * @param userId
     * @return List<UserRoleEntity>
     * @author: 张弓
     * @date: 2019/2/23
     * @version: 1.0.0
     */
    List<UserRoleEntity> findByUserId(@Param("userId") Integer userId);
}
