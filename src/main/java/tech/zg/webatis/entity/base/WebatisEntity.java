package tech.zg.webatis.entity.base;

/**
 * 类说明
 * <p>
 * @author: 张弓
 * @version: 1.0.0
 */
public class WebatisEntity extends BaseEntity{

    private Integer deleted;
    private String createdAt;
    private String updatedAt;

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
