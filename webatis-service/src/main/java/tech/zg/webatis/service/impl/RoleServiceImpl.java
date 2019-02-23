package tech.zg.webatis.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.zg.webatis.entity.RoleEntity;
import tech.zg.webatis.mapper.RoleMapper;
import tech.zg.webatis.service.RoleService;

/**
 * 角色表
 *
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Integer, RoleEntity> implements RoleService, InitializingBean {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setBaseMapper(roleMapper);
    }
}
