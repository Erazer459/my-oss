package io.github.franzli347.foss.dto;

import java.io.Serializable;

public record ImageSimilarity(Long id1, Long id2, double similarity) implements Serializable {
    public ImageSimilarity {
        if(similarity > 0){
            throw new IllegalArgumentException("similarity must be greater than 0");
        }
    }
    public static ImageSimilarity of(Long id1, Long id2, double similarity) {
        return new ImageSimilarity(id1, id2, similarity);
    }
}
