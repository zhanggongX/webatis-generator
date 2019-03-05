package tech.zg.webatis.mapper;

import org.apache.ibatis.annotations.Param;
import tech.zg.webatis.bean.UserRoleBean;
import tech.zg.webatis.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户表
 * 
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
@Mapper
public interface UserMapper extends BaseMapper<Integer, UserEntity> {

    /**
     * 根据用户名查询用户信息
     * <p>
     *
     * @param userName
     * @return UserEntity
     * @author: 张弓
     * @date: 2019/2/23
     * @version: 1.0.0
     */
    UserEntity findByName(@Param("userName") String userName);

    /**
     * 根据用户名查询用户角色
     * <p>
     *
     * @param userName
     * @return List<UserRoleBean>
     * @author: 张弓
     * @date: 2019/2/23
     * @version: 1.0.0
     */
    UserRoleBean findUserRoleByName(@Param("userName") String userName);
}
