package tech.zg.webatis.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.zg.webatis.entity.TableEntity;
import tech.zg.webatis.mapper.TableMapper;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerHelper;
import tech.zg.webatis.service.TableService;
import tech.zg.webatis.service.base.impl.BaseServiceImpl;

/**
 * 数据库表的服务类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@Service("tableService")
public class TableServiceImpl extends BaseServiceImpl<Integer, TableEntity> implements TableService, InitializingBean{

    @Autowired
    TableMapper tableMapper;
    @Override
    public void afterPropertiesSet() throws Exception {
        this.setBaseMapper(tableMapper);
    }

    /**
     * 分页查询数据库中的表
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param pager 分页信息
     * @param tableName 表名
     * @return Pager
     */
    @Override
    public Pager listByPager(Pager pager, String tableName) {
        PagerHelper.startPage(pager);
        tableMapper.list(tableName);
        return pager;
    }

    /**
     * 通过表名，查询表信息
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param tableName
     * @return TableEntity
     */
    @Override
    public TableEntity getByTableName(String tableName) {
        return tableMapper.getByTableName(tableName);
    }
}
