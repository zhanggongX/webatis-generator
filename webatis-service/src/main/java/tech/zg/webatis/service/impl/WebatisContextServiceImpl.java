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

    @Override
    public void registerBean(String beanName, Map<String, Object> varMap, Class<?> tClass) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(tClass);
        for(Map.Entry<String, Object> s : varMap.entrySet()){
            String k = s.getKey();
            Object v = s.getValue();
            beanDefinitionBuilder.addPropertyValue(k, v);
        }
        beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
    }


    @Override
    public <T> T getBean(String beanName, Class<T> tClass) {
        return applicationContext.getBean(beanName, tClass);
    }

}
