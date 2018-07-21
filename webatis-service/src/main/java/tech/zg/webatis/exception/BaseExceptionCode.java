package tech.zg.webatis.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 基础异常Code
 * <p>
 *
 * @author ：zhanggong
 * @version : 1.0.0
 * @date ：2018/4/27
 */
@Configuration
@ConfigurationProperties(prefix = "webaits")
public class BaseExceptionCode {
    /**
     * EX-000001: 系统异常！
     */
    public static final String SYSTEM_EXCEPTION = "WEX001";
    /**
     * EX-000002: JSON转化异常！
     */
    public static final String PARSE_JSON_EXCEPTION = "WEX002";
    /**
     * WEX003：数据源配置错误
     */
    public static final String DATASOURCE_CONFIG_FAIL = "WEX003";

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
