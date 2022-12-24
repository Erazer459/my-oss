package io.github.franzli347.foss.service;

import io.github.franzli347.foss.dto.ImageSimilarity;

import java.io.Serializable;
import java.util.List;

public interface IovService {
    List<ImageSimilarity> imageDiff(List<Serializable> ids);
}
