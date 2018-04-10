package tech.zg.webatis.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.mapper.WebatisDatabaseMapper;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerHelper;
import tech.zg.webatis.service.WebatisDatabaseService;
import tech.zg.webatis.service.base.impl.BaseServiceImpl;

/**
 * 数据库服务
 * <p>
 * @author: 张弓
 * @version: 1.0.0
 */
@Service("databaseService")
public class WebatisDatabaseServiceImpl extends BaseServiceImpl<Integer, WebatisDatabaseEntity> implements WebatisDatabaseService, InitializingBean{

    @Autowired
    private WebatisDatabaseMapper webatisDatabaseMapper;
    @Override
    public void afterPropertiesSet() throws Exception {
        super.setBaseMapper(webatisDatabaseMapper);
    }

    @Override
    public Pager listByPager(Pager pager, String dbsName) {
        PagerHelper.startPage(pager);
        webatisDatabaseMapper.list(dbsName);
        return pager;
    }
}
