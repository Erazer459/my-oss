package io.github.franzli347.foss.support;

import java.util.List;

public interface ChunkPathResolver {
    List<String> getChunkPaths(String id,int chunks);
}
