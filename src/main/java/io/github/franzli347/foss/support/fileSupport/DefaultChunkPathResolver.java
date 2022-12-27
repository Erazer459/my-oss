package io.github.franzli347.foss.support.fileSupport;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FranzLi
 */
@Component
public class DefaultChunkPathResolver implements ChunkPathResolver{


    @Value("${pathMap.source}")
    String filePath;

    @Value("${tmpFilePattern}")
    String tmpFilePattern;
    @Override
    @SneakyThrows
    public List<String> getChunkPaths(String id,int chunks) {
        // init capacity
        List<String> chunkPaths = new ArrayList<>(chunks);
        for (int chunk = 1; chunk <= chunks; chunk++) {
            chunkPaths.add(tmpFilePattern.formatted(filePath,id,chunk));
        }
        return chunkPaths;
    }
}
