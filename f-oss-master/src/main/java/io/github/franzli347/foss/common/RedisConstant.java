package io.github.franzli347.foss.common;

/**
 * redis常量
 *
 * @author FranzLi
 */
public class RedisConstant {

    /**
     * 已上传文件md5前缀
     */
    public final static String FILE_MD5_LIST = "FILE_MD5_LIST";

    /**
     * 文件已上传块数前缀
     */
    public final static String FILE_CHUNK_LIST = "FILE_CHUNK_LIST";

    /**
     * 文件上传任务前缀
     */
    public final static String FILE_TASK = "FILE_TASK";

    /**
     * 文件上传任务过期时间
     */
    public final static long FILE_TASK_EXPIRE = 3600 * 14;
}
