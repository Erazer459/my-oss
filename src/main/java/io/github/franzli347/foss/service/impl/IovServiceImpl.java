package io.github.franzli347.foss.service.impl;

import io.github.franzli347.foss.dto.ImageSimilarity;
import io.github.franzli347.foss.entity.Files;
import io.github.franzli347.foss.service.FilesService;
import io.github.franzli347.foss.service.IovService;
import io.github.franzli347.foss.utils.ImageHistogram;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Value("${pathMap.source}")
    private String path;

    public IovServiceImpl(FilesService filesService,ImageHistogram imageHistogram){
        this.filesService = filesService;
        this.imageHistogram = imageHistogram;
    }


    @SneakyThrows
    @Override
    public List<ImageSimilarity> imageDiff(List<String> ids) {
        List<Files> files = filesService.listByIds(ids);
        List<ImageSimilarity> res = new LinkedList<>();
        for (int i = 0; i < files.size() - 1; i++) {
            for (int j = i + 1; j < files.size(); j++) {
                Files files1 = files.get(i);
                Files files2 = files.get(j);
                double similarity = imageHistogram.match(new File(path + files1.getPath()), new File(path + files2.getPath()));
                if (similarity > MINIMUM_SIMILARITY) {
                    res.add(ImageSimilarity.of(files1.getId(), files2.getId(), similarity));
                }
            }
        }
        return res;
    }
}

