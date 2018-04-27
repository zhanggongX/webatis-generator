package tech.zg.webatis.service;


import tech.zg.webatis.entity.BaseEntity;
import tech.zg.webatis.exception.BaseException;

import java.io.Serializable;

/**
 * BaseService
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public interface BaseService<PK extends Serializable, E extends BaseEntity> {

    /**
     * 新增对象
     * <p>
     *
     * @param entity 对象
     * @return int 返回的主键
     * @throws BaseException
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    int save(E entity) throws BaseException;

    /**
     * 通过主键, 删除对象
     * <p>
     *
     * @param id 主键
     * @return int 删除行数
     * @throws BaseException
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    int remove(PK id) throws BaseException;

    /**
     * 更新对象
     * <p>
     *
     * @param entity 对象
     * @return int 成功的行数
     * @throws BaseException
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    int update(E entity) throws BaseException;

    /**
     * 通过主键, 查询对象
     * <p>
     *
     * @param id 主键
     * @return E 实体
     * @throws BaseException
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    E get(PK id) throws BaseException;
}
