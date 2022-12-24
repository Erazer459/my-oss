package io.github.franzli347.foss.service.impl;

import io.github.franzli347.foss.dto.ImageSimilarity;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.service.FilesService;
import io.github.franzli347.foss.service.IovService;
import io.github.franzli347.foss.utils.ImageHistogram;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author FranzLi
 */
@Service
public class IovServiceImpl implements IovService {

    FilesService filesService;

    ImageHistogram imageHistogram;

    private static final double MINIMUM_SIMILARITY = 0.75;


    @SneakyThrows
    @Override
    public List<ImageSimilarity> imageDiff(List<Serializable> ids) {
        List<Files> files = filesService.listByIds(ids);
        List<ImageSimilarity> res = new LinkedList<>();
        for (int i = 0; i < files.size() - 1; i++) {
            for (int j = i + 1; j < files.size(); j++) {
                Files files1 = files.get(i);
                Files files2 = files.get(j);
                double similarity = imageHistogram.match(new File(files1.getPath()), new File(files2.getPath()));
                if (similarity > MINIMUM_SIMILARITY) {
                    res.add(ImageSimilarity.builder()
                            .id1(String.valueOf(files1.getId()))
                            .id2(String.valueOf(files2.getId()))
                            .similarity(similarity)
                            .build());
                }
            }
        }
        return res;
    }
}

