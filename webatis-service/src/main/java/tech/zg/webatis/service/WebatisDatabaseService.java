package tech.zg.webatis.service;

import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.pager.Pager;

import java.util.List;

/**
 * 数据库服务
 * <p>
 *
 * @author: 张弓
 * @version: 1.0.0
 */
public interface WebatisDatabaseService extends BaseService<Integer, WebatisDatabaseEntity> {

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
    Pager listByPager(Pager pager, String dbsName);

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
    List<WebatisDatabaseEntity> list();
}
