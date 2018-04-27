package tech.zg.webatis.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.mapper.WebatisDatabaseMapper;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerHelper;
import tech.zg.webatis.service.WebatisDatabaseService;

import java.util.List;

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

    /**
     * 分页查询数据库信息
     * <p>
     *
     * @param pager   分页信息
     * @param dbsName 数据库名，模糊查询
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/27
     */
    @Override
    public Pager listByPager(Pager pager, String dbsName) {
        PagerHelper.startPage(pager);
        webatisDatabaseMapper.list(dbsName);
        return pager;
    }

    /**
     * 数据库信息列表
     * <p>
     *
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/27
     */
    @Override
    public List<WebatisDatabaseEntity> list() {
        return webatisDatabaseMapper.list(null);
    }
}
