package tech.zg.webatis.bean;

import java.util.List;

/**
 * 用户角色表
 * @author zg
 */
public class UserRoleBean {
    private String userName;
    private String password;
    private List<String> roleNames;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
