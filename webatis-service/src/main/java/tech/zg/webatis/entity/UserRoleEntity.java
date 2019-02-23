package tech.zg.webatis.entity;

import java.util.Date;


/**
 * 用户角色表
 * <p>
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
public class UserRoleEntity extends BaseEntity {
	
	/**
	 * 主键
 	 */
	private Integer id;
	/**
	 * 用户id
 	 */
	private Integer userId;
	/**
	 * 用户角色id
 	 */
	private Integer roleId;
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
	 * 设置：用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：用户角色id
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * 获取：用户角色id
	 */
	public Integer getRoleId() {
		return roleId;
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
	/**
	 * 设置：更新时间
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}
}
