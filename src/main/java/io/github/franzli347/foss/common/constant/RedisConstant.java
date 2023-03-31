package io.github.franzli347.foss.common.constant;

/**
 * redis常量
 * @author FranzLi
 */
public class RedisConstant {

    private RedisConstant() {
    }

    /**
     * 已上传文件md5前缀
     */
    public static final  String FILE_MD5_LIST = "FILE:MD5:LIST";

    /**
     * 文件已上传块数前缀
     */
    public static final String FILE_CHUNK_LIST = "FILE:CHUNK:LIST";

    /**
     * 文件上传任务前缀
     */
    public static final String FILE_TASK = "FILE:TASK";

    /**
     * 文件上传任务过期时间
     */
    public static final long FILE_TASK_EXPIRE = 50400;
    /**
     * 压缩任务前缀
     **/
    public final static String COMPRESS_TASK="COMPRESS_TASK";
    /**
     * WebSocket连接session
     **/
    public final static String WS_SESSION_LIST ="WS_SESSION_LIST";

}
