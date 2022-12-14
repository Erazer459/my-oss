package io.github.franzli347.foss.support;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FranzLi
 */
@Component
public class DefaultChunkPathResolver implements ChunkPathResolver{

    /**
     * TODO:从配置文件中读取
     */
    String filePath = "E:\\ceodes\\f-oss\\src\\main\\resources\\fileStore\\";
    @Override
    @SneakyThrows
    public List<String> getChunkPaths(String id,int chunks) {
        // init capacity
        List<String> chunkPaths = new ArrayList<>(chunks);
        for (int i = 1; i <= chunks; i++) {
            chunkPaths.add(filePath
                    + id + "."
                    + i + ".chunk");
        }
        return chunkPaths;
    }
}
