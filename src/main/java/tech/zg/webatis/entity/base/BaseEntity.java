package tech.zg.webatis.entity.base;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * 实体基础类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@JsonIgnoreProperties(value = { "pk" })
public abstract class BaseEntity implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger("BaseEntity");

    public BaseEntity() {
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Encoded entity to json error: {}", ex.getMessage());
        }
        return "";
    }

    public String toXML() {
        //return XMLUtil.toXML(this);
        return "";
    }

    /**
     * @param <T>
     * @param text
     * @return
     */
    public static <T> T valueOf(String text) {
        T t = (T) JSON.toJSONString(text);
        return t;
    }

    /**
     * @param <T>
     * @param entityMap
     * @return
     */
    public static <T> T valueOf(Map<String, String> entityMap) {
        return null;
    }

    /**
     * 验证bean合法性
     */
    public boolean check() {
        // TODO
        return true;
    }

    /**
     * 获取主键，可能是ID，也有可能是联合主键
     *
     * @return
     */
    public Object getPk() {
        return "0";
    }
}
