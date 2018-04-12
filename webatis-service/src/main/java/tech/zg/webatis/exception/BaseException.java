package tech.zg.webatis.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * 公共异常
 *
 * @author 卢益
 * @version 1.0.0 on 2017/8/29
 */
public class BaseException extends Exception {
    
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final long serialVersionUID = 8563126331361802238L;

    public BaseException(String exCode) {
        super(exCode);

        String exDesc = null;
        if (BaseExceptionCode.EX_MAP != null) {
            exDesc = BaseExceptionCode.EX_MAP.get(exCode);
        } else {
            exDesc = exCode;
        }

        if (BaseExceptionCode.SYSTEM_EXCEPTION.equals(exCode)) {
            exDesc = "[" + UUID.randomUUID().toString() + "]" + exDesc;
        }
        if (exDesc == null) {
            exDesc = BaseExceptionCode.EX_MAP.get(BaseExceptionCode.SYSTEM_EXCEPTION);
            exDesc = "[" + UUID.randomUUID().toString() + "]" + exDesc;
        }

        this.setExCode(exCode);
        this.setExDesc(exDesc);
    }

    public BaseException(String exCode, Object... params) {
        super(exCode);

        String exDesc = null;
        if (BaseExceptionCode.EX_MAP != null) {
            exDesc = BaseExceptionCode.EX_MAP.get(exCode);
        } else {
            exDesc = exCode;
        }

        if (BaseExceptionCode.SYSTEM_EXCEPTION.equals(exCode)) {
            exDesc = "[" + UUID.randomUUID().toString() + "]" + exDesc;
        }
        if (exDesc == null) {
            exDesc = BaseExceptionCode.EX_MAP.get(BaseExceptionCode.SYSTEM_EXCEPTION);
            exDesc = "[" + UUID.randomUUID().toString() + "]" + exDesc;
        }

        this.setExCode(exCode);
        this.params = params;
        this.setExDesc(exDesc);
    }

    public BaseException(String exCode, Throwable e, Object... params) {
        super(exCode);

        String exDesc = null;
        if (BaseExceptionCode.EX_MAP != null) {
            exDesc = BaseExceptionCode.EX_MAP.get(exCode);
        } else {
            exDesc = exCode;
        }

        if (BaseExceptionCode.SYSTEM_EXCEPTION.equals(exCode)) {
            exDesc = "[" + UUID.randomUUID().toString() + "]" + exDesc;
        }
        if (exDesc == null) {
            exDesc = BaseExceptionCode.EX_MAP.get(BaseExceptionCode.SYSTEM_EXCEPTION);
            exDesc = "[" + UUID.randomUUID().toString() + "]" + exDesc;
        }

        this.setExCode(exCode);
        this.params = params;
        this.setExDesc(exDesc);
        this.setExStack(getStackTraceMessage(e));
    }

    public BaseException(String exCode, Throwable e) {
        super(exCode);

        String exDesc = null;
        if (BaseExceptionCode.EX_MAP != null) {
            exDesc = BaseExceptionCode.EX_MAP.get(exCode);
        } else {
            exDesc = exCode;
        }

        if (BaseExceptionCode.SYSTEM_EXCEPTION.equals(exCode)) {
            exDesc = "[" + UUID.randomUUID().toString() + "]" + exDesc;
        }
        if (exDesc == null) {
            exDesc = BaseExceptionCode.EX_MAP.get(BaseExceptionCode.SYSTEM_EXCEPTION);
            exDesc = "[" + UUID.randomUUID().toString() + "]" + exDesc;
        }

        this.setExCode(exCode);
        this.setExDesc(exDesc);
        this.exStack = getStackTraceMessage(e);
    }

    private Object[] params;
    private String exCode;
    private String exDesc;
    private String exStack;

    public String getExStack() {
        return exStack;
    }

    public void setExStack(String exStack) {
        this.exStack = exStack;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getExDesc() {
        return exDesc;
    }

    public void setExDesc(String exDesc) {
        if (params != null) {
            this.exDesc = MessageFormat.format(exDesc, params);
        } else {
            this.exDesc = exDesc;
        }
    }

    @Override
    public String getMessage() {
        String _exCode = super.getMessage();
        return "[" + _exCode + "]" + this.exDesc;
    }

    private String getStackTraceMessage(Throwable e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);

            e.printStackTrace(pw);

            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    LOGGER.error(e1.getMessage(), e1);
                }
            }

            if (pw != null) {
                pw.close();
            }
        }

        return sw.toString();
    }
}
