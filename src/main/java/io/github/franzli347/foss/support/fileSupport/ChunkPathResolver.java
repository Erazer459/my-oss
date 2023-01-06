package io.github.franzli347.foss.support.fileSupport;

import java.util.List;
import java.util.Set;

public interface ChunkPathResolver {
    List<String> getChunkPaths(String md5,int chunks);

    List<String> getChunkPaths(String md5, Set<String> chunks);
}
