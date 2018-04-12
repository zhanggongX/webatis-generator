package tech.zg.webatis.service;

import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.service.base.BaseService;

import java.util.List;

/**
 * 数据库服务
 * <p>
 * @author: 张弓
 * @version: 1.0.0
 */
public interface WebatisDatabaseService extends BaseService<Integer, WebatisDatabaseEntity>{
    Pager listByPager(Pager pager, String dbsName);
    List<WebatisDatabaseEntity> list();
}
