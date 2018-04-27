package tech.zg.webatis.service;

import java.util.Map;

/**
 * webatis上下文访问
 * <p>
 *
 * @author: 张弓
 * @date: 2018/4/21
 * @version: 1.0.0
 */
public interface WebatisContextService {

    /**
     * 想spring中动态注入bean
     * <p>
     *
     * @param beanName 参数说明
     * @param varMap   bean的参数
     * @param tClass   bean的类型
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     */
    void registerBean(String beanName, Map<String, Object> varMap, Class<?> tClass);

    /**
     * 方法说明
     * <p>
     *
     * @param beanName bean的名称
     * @param tClass   bean的类型
     * @return 获取到的bean
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     */
    <T> T getBean(String beanName, Class<T> tClass);
}
