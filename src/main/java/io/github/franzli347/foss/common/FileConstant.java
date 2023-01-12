package io.github.franzli347.foss.common;

public class FileConstant {
    public static final String ILLEGAL_FILE_RE = "[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$";

    public static final String FILE_NAME_ILLEGAL_MSG = "文件名非法,文件名中不能含有 / \\ \" : | * ? < >等字符";

    public static final int MIN_CHUNKS_NUM = 2;

    public static final String MIN_CHUNKS_MSG = "分块数最少为两块";

    public static final long MIN_FILE_SIZE = 1;

    public static final String MIN_FILE_SIZE_MSG = "文件大小至少为1byte";

}
