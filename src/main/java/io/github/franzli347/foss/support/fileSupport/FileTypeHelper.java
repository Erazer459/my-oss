package io.github.franzli347.foss.support.fileSupport;

import io.github.franzli347.foss.utils.FileUtil;

import java.util.Set;

/**
 * @author FranzLi
 */
public class FileTypeHelper {

    private FileTypeHelper() {
    }


    private static final  Set<String> VIDEOTYPE_SET;

    private static final  Set<String> IMGTYPE_SET;

    private static final int IMG_TYPE = 1;

    private static final int VIDEO_TYPE = 0;

    private static final int OT_TYPE = 2;

    static {
        VIDEOTYPE_SET = Set.of("mp4");
        IMGTYPE_SET = Set.of("jpg");
    }

    public static int getFileType(String filePath) {
        String extraName = FileUtil.getExtraName(filePath);
        if (VIDEOTYPE_SET.contains(extraName)) {
            return VIDEO_TYPE;
        }
        if (IMGTYPE_SET.contains(extraName)) {
            return IMG_TYPE;
        }
        return OT_TYPE;
    }


}
