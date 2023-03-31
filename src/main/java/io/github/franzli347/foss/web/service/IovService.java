package io.github.franzli347.foss.web.service;

import io.github.franzli347.foss.model.dto.ImageSimilarity;

import java.util.List;

public interface IovService {
    List<ImageSimilarity> imageDiff(List<String> ids);
}
