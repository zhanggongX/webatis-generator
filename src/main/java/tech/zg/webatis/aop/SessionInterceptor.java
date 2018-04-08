package tech.zg.webatis.aop;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import tech.zg.webatis.config.WebatisConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 为会话增加公有的配置
 * <p>
 * @author: 张弓
 * @version: 1.0.0
 */
@Component("sessionInterceptor")
public class SessionInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) object;
        if (object instanceof ResourceHttpRequestHandler
                || object instanceof DefaultServletHttpRequestHandler
                || handlerMethod.getBean() instanceof BasicErrorController) {
            // 如果是访问的静态资源不拦截
            return true;
        }

        request.setAttribute("_appDomain", WebatisConfig.appDomain);
        request.setAttribute("_contextPath", WebatisConfig.contextPath);
        request.setAttribute("_version", WebatisConfig.version);

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
