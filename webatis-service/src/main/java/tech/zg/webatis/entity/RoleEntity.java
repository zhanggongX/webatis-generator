package tech.zg.webatis.entity;

import java.util.Date;


/**
 * 角色表
 * <p>
 *
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
public class RoleEntity extends BaseEntity {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色说明
     */
    private String roleComment;
    /**
     * 1=删除
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 设置：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取：角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置：角色说明
     */
    public void setRoleComment(String roleComment) {
        this.roleComment = roleComment;
    }

    /**
     * 获取：角色说明
     */
    public String getRoleComment() {
        return roleComment;
    }

    /**
     * 设置：1=删除
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取：1=删除
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
