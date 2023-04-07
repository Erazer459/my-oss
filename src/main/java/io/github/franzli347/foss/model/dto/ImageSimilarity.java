package io.github.franzli347.foss.model.dto;

import io.github.franzli347.foss.common.ValidatedGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 图片相似度返回对象
 * @param id1
 * @param id2
 * @param similarity
 */
public record ImageSimilarity(String id1, String id2, double similarity) implements Serializable {
    public ImageSimilarity {
        if(similarity < 0){
            throw new IllegalArgumentException("similarity must be greater than 0");
        }
    }
    public static ImageSimilarity of(@NotNull(message = "文件id不能为空", groups = {ValidatedGroup.Update.class}) String id1, @NotNull(message = "文件id不能为空", groups = {ValidatedGroup.Update.class}) String id2, double similarity) {
        return new ImageSimilarity(id1, id2, similarity);
    }
}
