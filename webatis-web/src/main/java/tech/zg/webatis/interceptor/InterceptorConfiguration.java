package tech.zg.webatis.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 控制层拦截器配置
 *
 * @author 卢益
 * @version 1.0.0 on 2017/8/16 9:47
 */
@Component("interceptorConfiguration")
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    //为会话增加公有的配置拦截器
    @Autowired
    private HandlerInterceptor sessionInterceptor;

    /**
     * 注册拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册session拦截器
        InterceptorRegistration sessionInter = registry.addInterceptor(sessionInterceptor);
        // 配置拦截的路径
        sessionInter.addPathPatterns("/**");
        // 配置不拦截的路径
        sessionInter.excludePathPatterns(new String[]{
                "/**.js",
                "/**.css"
        });

        super.addInterceptors(registry);
    }
}
