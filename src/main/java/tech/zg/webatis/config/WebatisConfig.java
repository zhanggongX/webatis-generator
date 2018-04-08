package tech.zg.webatis.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 项目配置
 * <p>
 * @author: 张弓
 * @version: 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "webatis")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebatisConfig {

    /**
     * 项目域名
     */
    public static String appDomain;
    /**
     * 项目名
     */
    public static String contextPath;
    /**
     * 版本号
     */
    public static String version;


    public static String getAppDomain() {
        return appDomain;
    }

    public static void setAppDomain(String appDomain) {
        WebatisConfig.appDomain = appDomain;
    }

    public static String getContextPath() {
        return contextPath;
    }

    public static void setContextPath(String contextPath) {
        WebatisConfig.contextPath = contextPath;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        WebatisConfig.version = version;
    }
}
