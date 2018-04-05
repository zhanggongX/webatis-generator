package tech.zg.webatis.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.zg.webatis.entity.ColumnEntity;
import tech.zg.webatis.mapper.ColumnMapper;
import tech.zg.webatis.service.ColumnService;
import tech.zg.webatis.service.base.impl.BaseServiceImpl;

import java.util.List;

/**
 * 数据表列信息
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@Service("columnService")
public class ColumnServiceImpl extends BaseServiceImpl<Integer, ColumnEntity> implements ColumnService, InitializingBean{

    @Autowired
    private ColumnMapper columnMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setBaseMapper(columnMapper);
    }

    /**
     * 根据表名，查询当前表的所有列的信息。
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param tableName
     * @return List<ColumnEntity>
     */
    @Override
    public List<ColumnEntity> queryColumnsByTableName(String tableName) {
        return columnMapper.queryColumnsByTableName(tableName);
    }
}
