package io.github.franzli347.foss.common;

/**
 * 返回状态码
 * @author FranzLi
 */

public final class ResultCode {


    private ResultCode(){}
    /**
     * 成功
     */
    public static final int CODE_SUCCESS = 200;
    /**
     * 认证错误
     */
    public static final int CODE_AUTH = 401;
    /**
     * 服务器内部错误
     */
    public static final int CODE_ERROR = 500;
}
