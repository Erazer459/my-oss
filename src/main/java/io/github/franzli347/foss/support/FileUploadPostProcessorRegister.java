package io.github.franzli347.foss.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FranzLi
 */
public abstract class FileUploadPostProcessorRegister {
    private final List<FileUploadPostProcessor> fileUploadPostProcessors = new ArrayList<>();

    public void register(FileUploadPostProcessor fileUploadPostProcessor) {
        fileUploadPostProcessors.add(fileUploadPostProcessor);
    }

    public List<FileUploadPostProcessor> getFileUploadPostProcessors() {
        return fileUploadPostProcessors;
    }

}
