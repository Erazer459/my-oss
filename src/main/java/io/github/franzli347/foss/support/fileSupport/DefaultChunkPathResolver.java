package io.github.franzli347.foss.support.fileSupport;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public List<String> getChunkPaths(String md5,int chunks) {
        // init capacity
        List<String> chunkPaths = new ArrayList<>(chunks);
        for (int chunk = 1; chunk <= chunks; chunk++) {
            chunkPaths.add(tmpFilePattern.formatted(filePath,md5,chunk));
        }
        return chunkPaths;
    }

    @Override
    public List<String> getChunkPaths(String md5, Set<String> chunks) {
        // init capacity
        List<String> chunkPaths = new ArrayList<>(chunks.size());
        chunks.forEach(v -> chunkPaths.add(tmpFilePattern.formatted(filePath,md5,v)));
        return chunkPaths;
    }
}
