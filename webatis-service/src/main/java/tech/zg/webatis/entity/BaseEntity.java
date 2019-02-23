package tech.zg.webatis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 实体基础类
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@JsonIgnoreProperties({"pk"})
public abstract class BaseEntity implements Serializable {

}
