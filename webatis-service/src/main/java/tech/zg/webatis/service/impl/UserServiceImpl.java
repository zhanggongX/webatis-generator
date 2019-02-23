package tech.zg.webatis.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.zg.webatis.entity.UserEntity;
import tech.zg.webatis.mapper.UserMapper;
import tech.zg.webatis.service.UserService;

/**
 * 用户表
 *
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<Integer, UserEntity> implements UserService, InitializingBean {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setBaseMapper(userMapper);
    }

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
    @Override
    public UserEntity findByName(String userName) {
        if(!StringUtils.isEmpty(userName)){
            return userMapper.findByName(userName);
        }
        return null;
    }
}
