package io.github.franzli347.foss.common;

public class FileConstant {

    private FileConstant(){}

    /**
     * 判断文件名是否非法正则
     */
    public static final String ILLEGAL_FILE_RE = "[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$";

    /**
     * 文件名非法消息提示
     */
    public static final String FILE_NAME_ILLEGAL_MSG = "文件名非法,文件名中不能含有 / \\ \" : | * ? < >等字符";

    /**
     * 最小总分片数量
     */
    public static final int MIN_CHUNKS_NUM = 2;

    /**
     * 最小分片数量消息提示
     */
    public static final String MIN_CHUNKS_MSG = "分块数最少为两块";

    /**
     * 最小文件大小
     */
    public static final long MIN_FILE_SIZE = 1;

    /**
     * 文件大小消息提示
     */
    public static final String MIN_FILE_SIZE_MSG = "文件大小至少为1byte";
}
