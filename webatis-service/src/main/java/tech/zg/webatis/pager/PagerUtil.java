package tech.zg.webatis.pager;

import java.util.Map;

/**
 * 拼装pager数据
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public class PagerUtil {

    /**
     * 拼装分页数据，为了兼容layui
     * <p>
     *
     * @param params
     * @return Pager
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    public static Pager getPager(Map<String, Object> params) {

        Pager pager = new Pager();
        pager.setCurrPage(1);
        pager.setPageSize(10);

        String page = null;
        String limit = null;
        if (params.containsKey("page")) {
            page = (String) params.get("page");
            if (page != null) {
                pager.setCurrPage(Integer.parseInt(page));
            }
        }
        if (params.containsKey("limit")) {
            limit = (String) params.get("limit");
            if (limit != null) {
                pager.setPageSize(Integer.parseInt(limit));
            }
        }
        return pager;
    }
}
