package tech.zg.webatis.exception;

/**
 * 基础运行时异常
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public class BaseRunTimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public BaseRunTimeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseRunTimeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BaseRunTimeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BaseRunTimeException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
