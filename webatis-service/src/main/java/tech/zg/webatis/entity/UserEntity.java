package tech.zg.webatis.entity;

import java.util.Date;


/**
 * 用户表
 * <p>
 * @author WeBatis
 * @email 18523019@qq.com
 * @date 2019-02-23 17:11:50
 */
public class UserEntity extends BaseEntity {
	
	/**
	 * 主键
 	 */
	private Integer id;
	/**
	 * 姓名
 	 */
	private String userName;
	/**
	 * 密码，测试用明文
 	 */
	private String password;
	/**
	 * 用户号
 	 */
	private String userNo;
	/**
	 * 用户名称
 	 */
	private String realName;
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
	 * 设置：姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：密码，测试用明文
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码，测试用明文
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：用户号
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	/**
	 * 获取：用户号
	 */
	public String getUserNo() {
		return userNo;
	}
	/**
	 * 设置：用户名称
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 获取：用户名称
	 */
	public String getRealName() {
		return realName;
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
