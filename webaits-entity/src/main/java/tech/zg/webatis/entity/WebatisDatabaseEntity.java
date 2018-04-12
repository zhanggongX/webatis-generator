package tech.zg.webatis.entity;

import tech.zg.webatis.entity.base.WebatisEntity;

/**
 * 数据库实体类
 * <p>
 * @author: 张弓
 * @version: 1.0.0
 */
public class WebatisDatabaseEntity extends WebatisEntity {

    private Integer id;
    /**
     * 数据库名称
     */
    private String name;
    /**
     * 数据库的url，ip
     */
    private String url;
    /**
     * 数据库的端口
     */
    private Integer port;
    /**
     * 数据库的类型
     */
    private Integer type;
    /**
     * 数据库登录名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 生成的包路径
     */
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
