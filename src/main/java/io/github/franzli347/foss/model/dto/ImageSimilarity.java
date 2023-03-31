package io.github.franzli347.foss.model.dto;

import java.io.Serializable;

/**
 * 图片相似度返回对象
 * @param id1
 * @param id2
 * @param similarity
 */
public record ImageSimilarity(Long id1, Long id2, double similarity) implements Serializable {
    public ImageSimilarity {
        if(similarity < 0){
            throw new IllegalArgumentException("similarity must be greater than 0");
        }
    }
    public static ImageSimilarity of(Long id1, Long id2, double similarity) {
        return new ImageSimilarity(id1, id2, similarity);
    }
}
