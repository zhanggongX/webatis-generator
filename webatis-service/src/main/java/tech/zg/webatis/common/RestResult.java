package tech.zg.webatis.common;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * rest请求返回数据
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public class RestResult extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实例化RestResult
     * <p>
     *
     * @param
     * @return RestResult
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    public RestResult() {
        put("code", WebatisCode.SUCCESS.getCode());
    }

    /**
     * Rest请求失败，无参数
     * <p>
     *
     * @param
     * @return RestResult
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    public static RestResult error() {
        return error(WebatisCode.FAIL.getCode(), WebatisCode.FAIL.getMessage());
    }

    /**
     * Rest请求失败，带参数
     * <p>
     *
     * @param msg 错误信息
     * @return RestResult
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    public static RestResult error(String msg) {
        return error(WebatisCode.FAIL.getCode(), msg);
    }

    /**
     * Rest请求失败，带参数
     * <p>
     *
     * @param code 错误编码
     * @param msg  错误信息
     * @return RestResult
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    public static RestResult error(String code, String msg) {
        RestResult result = new RestResult();
        result.put("code", code);
        result.put("message", msg);
        return result;
    }

    /**
     * Rest请求成功，无参数
     * <p>
     *
     * @return RestResult
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    public static RestResult success() {
        return new RestResult();
    }

    /**
     * Rest请求成功，带参数
     * <p>
     *
     * @param msg 成功信息
     * @return RestResult
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    public static RestResult success(String msg) {
        RestResult result = new RestResult();
        result.put("message", msg);
        return result;
    }

    /**
     * Rest请求成功，带参数
     * <p>
     *
     * @param map 成功信息
     * @return RestResult
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    public static RestResult success(Map<String, Object> map) {
        RestResult result = new RestResult();
        result.putAll(map);
        return result;
    }

    /**
     * 往结果中放数据
     * <p>
     *
     * @param key   结果的key
     * @param value 结果的值
     * @return RestResult
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     */
    @Override
    public RestResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
