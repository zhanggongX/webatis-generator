package tech.zg.webatis.service;

import tech.zg.webatis.entity.UserRoleEntity;

import java.util.List;


/**
 * 用户角色表
 *
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
public interface UserRoleService extends BaseService<Integer, UserRoleEntity> {

    List<UserRoleEntity> findByUserId(Integer id);
}
