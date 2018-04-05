package tech.zg.webatis.aop;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerHelper;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 1.拼接count sql，执行sql获取总记录数
 * 2.计算分页总页数、分页索引起始数量
 * 3.拼接分页sql， 执行分页sql获取结果
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@Intercepts(
    {
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
    }
)
public class PagerInterceptor implements Interceptor {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PagerInterceptor.class);

    /**
     * 空集合
     */
    private static final List<ResultMapping> EMPTY_RESULT_MAPPING = new ArrayList<>(0);

    /**
     * SQL中from关键字，用于去除查询字段部分
     */
    private static final String SQL_FROM = "FROM";

    /**
     * 统计sql头
     */
    private static final String SQL_COUNT = "SELECT COUNT(0) FROM ";

    /**
     * additionalParametersField 放射Field
     */
    private Field additionalParametersField;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {

            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            Executor executor = (Executor) invocation.getTarget();
            CacheKey cacheKey;
            BoundSql boundSql;
            List resultList = null;

            if (args.length == 4) {
                //4 个参数时
                boundSql = ms.getBoundSql(parameter);
                cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            } else {
                //6 个参数时
                cacheKey = (CacheKey) args[4];
                boundSql = (BoundSql) args[5];
            }

            // 1. 判断Dao方法名listPager开头 2. 获取Pager入参
            Pager pager = getPagerInfo();
            // 执行分页
            if (pager != null) {
                Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);
                Long count = getCount(pager, boundSql, executor, ms, parameter, additionalParameters, resultHandler);

                // 统计为0，不查询数据
                if (count > 0) {
                    pager.setTotalCount(count.intValue());
                    //生成分页的缓存 key
                    CacheKey pageKey = cacheKey;
                    //调用方言获取分页 sql
                    String pageSql = getPageSql(pager, boundSql.getSql(), pageKey);
                    BoundSql pageBound = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), parameter);

                    //设置动态参数
                    for (String key : additionalParameters.keySet()) {
                        pageBound.setAdditionalParameter(key, additionalParameters.get(key));
                    }

                    //执行分页查询
                    resultList = executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, pageKey, pageBound);
                } else {
                    // 返回空查询结果
                    pager.setTotalCount(0);
                    resultList = new ArrayList(0);
                }

                pager.setList(resultList);
                return resultList;
            } else {
                // 非分页查询，跳出拦截器
                return invocation.proceed();
            }
        } finally {
            PagerHelper.clearPage();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        try {
            //反射获取 BoundSql 中的 additionalParameters 属性
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            LOGGER.error("加载additionalParameters属性反射异常", e);
        }
    }

    /**
     * 判断是否需要执行分页查询
     *
     * @param ms
     * @return boolean true:执行分页查询 , false:  不执行分页查询
     */
    private boolean isPager(MappedStatement ms) {
        String id = ms.getId();
        String[] ids = id.split("\\.");

        if (ids[ids.length - 1].startsWith("listPager")) {
            return true;
        }

        return false;
    }

    /**
     * 根据Dao查询方法入参，获取Pager 类型参数， 如果有多只获取一个，找不到则不执行分页查询
     *
     * @return Pager 分页对象，null:找不到分页对象
     */
    private Pager getPagerInfo() {
        return PagerHelper.getLocalPage();
    }

    /**
     * 获取count sql
     *
     * @param sql 原查询sql
     * @return count sql
     */
    private String getCountSql(String sql) {
        String sourceSql = sql.toUpperCase();
        String[] sourceSqlArr = sourceSql.split("FROM", 2);
        if (sourceSqlArr.length != 2) {
            LOGGER.error("分页查询错误，拼接count语句异常：" + sql);
            return null;
        } else {
            sourceSql = "SELECT COUNT(0) FROM " + sourceSql.substring(sourceSqlArr[0].length() + 4, sourceSql.length());
            if (sourceSql.matches("([\\s\\S]*)GROUP[\\s\\S]+BY([\\s\\S]*)")) {
                sourceSql = "SELECT COUNT(0) FROM (" + sourceSql + ") _group";
            }

            return sourceSql;
        }
    }

    /**
     * 获取分页查询sql
     */
    private String getPageSql(Pager pager, String sql, CacheKey pageKey) {
        int pageNumber = pager.getCurrPage();
        int pageSize = pager.getPageSize();
        int pageCount;
        int totalCount = pager.getTotalCount();
        int startRow;

        if (pageSize <= 0 || pageSize > 1000) {
            pageSize = 20;
        }

        pageCount = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            pageCount++;
        }

        if (pageNumber <= 0) {
            pageNumber = 1;
        }

        startRow = (pageNumber - 1) * pageSize;

        pager.setCurrPage(pageNumber);
        pager.setPageSize(pageSize);
        pager.setTotalPage(pageCount);

        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 14);
        sqlBuilder.append(sql);
        if (startRow == 0) {
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(pageSize);
        } else {
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(startRow);
            sqlBuilder.append(",");
            sqlBuilder.append(pageSize);

            pageKey.update(startRow);
        }

        pageKey.update(pageSize);
        return sqlBuilder.toString();
    }

    private MappedStatement createCountMappedStatement(MappedStatement ms) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + "_COUNT", ms.getSqlSource(), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());

        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }

        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        //count查询返回值int
        List<ResultMap> resultMaps = new ArrayList<>();
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), Long.class, EMPTY_RESULT_MAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    private Long getCount(Pager pagerInfo, BoundSql boundSql, Executor executor, MappedStatement ms
            , Object parameter, Map<String, Object> additionalParameters, ResultHandler resultHandler) throws SQLException {

        MappedStatement countMappedStatement = null;
        BoundSql countBoundSql = null;
        String countMapperId = pagerInfo.getTotalMapperId();

        // 执行自定义count
        if(countMapperId != null && !countMapperId.equals("")){
            try {
                countMappedStatement = ms.getConfiguration().getMappedStatement(countMapperId, false);
            } catch (Throwable t){
               LOGGER.error("%s MappedStatement不存在", countMapperId);
                return 0L;
            }

            countBoundSql = countMappedStatement.getBoundSql(parameter);
        }else{
            // 拼接count sql
            String countSql = getCountSql(boundSql.getSql());

            countMappedStatement = createCountMappedStatement(ms);
            countBoundSql = new BoundSql(ms.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
        }

        CacheKey countKey = executor.createCacheKey(ms, parameter, RowBounds.DEFAULT, boundSql);
        countKey.update("_Count");


        // 设置动态参数
        for (String key : additionalParameters.keySet()) {
            countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
        }

        // 执行count sql， 获取总记录数
        Object countResultObj = executor.query(countMappedStatement, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        Long count = 0L;
        if (countResultObj != null) {
            List countResultList = (List) countResultObj;
            if (countResultList != null && countResultList.size() > 0 && countResultList.get(0) != null) {
                count = (Long) countResultList.get(0);
            }
        }

        return count;
    }
}
