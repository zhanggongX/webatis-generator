package tech.zg.webatis.mapper.base;

import java.io.Serializable;

/**
 * BaseMapper
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public interface BaseMapper<PK extends Serializable, E> {

    /**
     * 插入对象
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param model
     * @return int
     */
    int save(E model);

    /**
     * 通过主键, 删除对象
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param id
     * @return int
     */
    int remove(PK id);

    /**
     * 更新对象
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param model
     * @return int
     */
    int update(E model);

    /**
     * 通过主键, 查询对象
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param id
     * @return E
     */
    E get(PK id);
}
