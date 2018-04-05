package tech.zg.webatis.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 基础异常Code
 *
 * @author 卢益
 * @version 1.0.0 on 2017/8/29
 */
@Configuration
@ConfigurationProperties(prefix = "rainbow")
public class BaseExceptionCode {
    /**
     * EX-000001: 系统异常！
     */
    public static final String SYSTEM_EXCEPTION = "EX-000001";
    /**
     * EX-000002: JSON转化异常！
     */
    public static final String PARSE_JSON_EXCEPTION = "EX-000002";
    /**
     * EX-000003：获取配置文件失败
     */
    public static final String PARSE_CONFIG_FAIL = "EX-000003";

    public static Map<String, String> EX_MAP;

    public Map<String, String> getException() {
        return exception;
    }

    public void setException(Map<String, String> exception) {
        BaseExceptionCode.EX_MAP = exception;
        this.exception = exception;
    }

    private Map<String, String> exception;
}
