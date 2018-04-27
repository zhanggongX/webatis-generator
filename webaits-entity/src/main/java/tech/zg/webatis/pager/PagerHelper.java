package tech.zg.webatis.pager;

/**
 * 分页
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public class PagerHelper<T> {

    protected static final ThreadLocal<Pager> LOCAL_PAGE = new ThreadLocal<Pager>();

    /**
     * 设置 Page 参数
     *
     * @param page
     */
    protected static void setLocalPage(Pager page) {
        LOCAL_PAGE.set(page);
    }

    /**
     * 获取 Page 参数
     *
     * @return
     */
    public static <T> Pager<T> getLocalPage() {
        return LOCAL_PAGE.get();
    }

    /**
     * 移除本地变量
     */
    public static void clearPage() {
        LOCAL_PAGE.remove();
    }

    /**
     * 开始分页
     *
     * @param pagerInfo 分页信息
     * @return PagerInfo
     */
    public static <E> Pager<E> startPage(Pager pagerInfo) {
        if (pagerInfo == null) {
            pagerInfo = new Pager();
        }
        setLocalPage(pagerInfo);
        return pagerInfo;
    }
}
