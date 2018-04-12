package tech.zg.webatis.pager;

import java.util.List;

/**
 * 分页信息类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */

public class Pager<T> {
    /**
     * 第几页
     */
    private int currPage; // required

    /**
     * 每页展示几条
     */
    private int pageSize; // required

    /**
     * 总记录数
     */
    private int totalCount; // required

    /**
     * 总页数
     */
    private int totalPage; // required

    /**
     * 分页查询结果
     */
    private List<T> list;  // 分页数据结果

    /**
     * mapper.xml中定义的统计select语句id(用于提高统计预计性能)
     */
    private String totalMapperId;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getTotalMapperId() {
        return totalMapperId;
    }

    public void setTotalMapperId(String totalMapperId) {
        this.totalMapperId = totalMapperId;
    }
}
