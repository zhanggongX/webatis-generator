package tech.zg.webatis.common;

/**
 * 常量
 * <p>
 *
 * @author: 张弓
 * @version: 1.0.0
 */
public class WebatisConstants {

    public static final Integer MYSQL = 1;
    public static final Integer ORACLE = 2;

    /**
     * 数据源bean name 使用
     */
    public static final String DATA_SOURCE_BEAN_NAME = "dataSource";
    /**
     * jdbcTemplate bean name 使用
     */
    public static final String JDBC_TEMPLATE_NAME = "jdbcTemplate";
    /**
     * 向spring中注入bean的最大可失败次数
     */
    public static final int REGISTER_BEAN_MOST_COUNT = 3;
}
