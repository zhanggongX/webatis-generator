package tech.zg.webatis.service;

import tech.zg.webatis.entity.UserEntity;


/**
 * 用户表
 *
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
public interface UserService extends BaseService<Integer, UserEntity> {

    UserEntity findByName(String name);
}
