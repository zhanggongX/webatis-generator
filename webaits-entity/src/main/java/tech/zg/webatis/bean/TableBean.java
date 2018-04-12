package tech.zg.webatis.bean;

import tech.zg.webatis.entity.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 一个表的实体类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public class TableBean extends BaseEntity {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 表存储类型
     */
    private String engine;
    /**
     * 表注释
     */
    private String tableComment;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 类名，例如sys_user --> SysUser;
     */
    private String className;
    /**
     * 类名，例如sys_user --> sysUser;
     */
    private String classname;
    /**
     * 表的列名(不包含主键)
     */
    private List<ColumnBean> columnEntityList;
    /**
     * 表的主键
     */
    private ColumnBean pk;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public List<ColumnBean> getColumnEntityList() {
        return columnEntityList;
    }

    public void setColumnEntityList(List<ColumnBean> columnEntityList) {
        this.columnEntityList = columnEntityList;
    }

    @Override
    public ColumnBean getPk() {
        return pk;
    }

    public void setPk(ColumnBean pk) {
        this.pk = pk;
    }
}
