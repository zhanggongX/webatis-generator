package tech.zg.webatis.mapper;

import java.io.Serializable;

/**
 * BaseMapper
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public interface BaseMapper<PK extends Serializable, E> {

    /**
     * 插入对象
     * <p>
     *
     * @param model
     * @return int
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    int save(E model);

    /**
     * 通过主键, 删除对象
     * <p>
     *
     * @param id
     * @return int
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    int remove(PK id);

    /**
     * 更新对象
     * <p>
     *
     * @param model
     * @return int
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    int update(E model);

    /**
     * 通过主键, 查询对象
     * <p>
     *
     * @param id
     * @return E
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    E get(PK id);
}
