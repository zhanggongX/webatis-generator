package tech.zg.webatis.common;

/**
 * 状态码
 * <p>
 *
 * @author: 张弓
 * @version: 1.0.0
 */
public enum WebatisCode {

    //0 到 5000 为系统通用预留
    SUCCESS("200", "成功"),
    FAIL("201", "失败"),
    SYS_ERR("202", "系统异常"),
    DATA_NOT_FOUND("203", "无查询数据"),
    AUTH_ERR("204", "鉴权失败");

    private String code;
    private String message;

    WebatisCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
