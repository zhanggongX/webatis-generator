package tech.zg.webatis.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import tech.zg.webatis.service.WebatisContextService;

import java.util.Map;

/**
 * @author 张弓
 * @create 2018-04-21 13:14
 */
@Service("webatisContextService")
public class WebatisContextServiceImpl implements WebatisContextService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 向spring中动态注入bean
     * <p>
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     *
     * @param beanName 参数说明
     * @param varMap bean的参数
     * @param tClass bean的类型
     */
    @Override
    public void registerBean(String beanName, Map<String, Object> varMap, Class<?> tClass) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(tClass);
        for (Map.Entry<String, Object> s : varMap.entrySet()) {
            String k = s.getKey();
            Object v = s.getValue();
            beanDefinitionBuilder.addPropertyValue(k, v);
        }
        beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
    }

    /**
     * 方法说明
     * <p>
     * @author: 张弓
     * @date: 2018/4/21
     * @version: 1.0.0
     *
     * @param beanName bean的名称
     * @param tClass bean的类型
     * @return 获取到的bean
     */
    @Override
    public <T> T getBean(String beanName, Class<T> tClass) {
        return applicationContext.getBean(beanName, tClass);
    }

    /**
     * 从spring容器中，移除bean
     * <p>
     *
     * @param beanName 参数说明
     * @author: 张弓
     * @date: 2018/7/21
     * @version: 1.0.0
     */
    @Override
    public void deleteBean(String beanName) {

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        beanFactory.removeBeanDefinition(beanName);
    }
}
