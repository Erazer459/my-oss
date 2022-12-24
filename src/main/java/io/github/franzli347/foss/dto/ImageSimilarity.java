package io.github.franzli347.foss.dto;

import lombok.*;

/**
 * @author FranzLi
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ImageSimilarity {
    private String id1;
    private String id2;
    private double similarity;
}
