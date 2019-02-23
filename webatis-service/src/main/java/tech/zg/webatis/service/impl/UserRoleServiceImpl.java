package tech.zg.webatis.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.zg.webatis.entity.UserRoleEntity;
import tech.zg.webatis.mapper.UserRoleMapper;
import tech.zg.webatis.service.UserRoleService;

import java.util.List;


/**
 * 用户角色表
 *
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl<Integer, UserRoleEntity> implements UserRoleService, InitializingBean {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setBaseMapper(userRoleMapper);
    }

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
    @Override
    public List<UserRoleEntity> findByUserId(Integer userId) {
        if(userId != null){
            return userRoleMapper.findByUserId(userId);
        }
        return null;
    }
}
