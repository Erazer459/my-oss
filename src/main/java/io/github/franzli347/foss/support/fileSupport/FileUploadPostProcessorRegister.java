package io.github.franzli347.foss.support.fileSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FranzLi
 */
public class FileUploadPostProcessorRegister {
    private final List<FileUploadPostProcessor> fileUploadPostProcessors = new ArrayList<>();

    public FileUploadPostProcessorRegister register(FileUploadPostProcessor fileUploadPostProcessor) {
        fileUploadPostProcessors.add(fileUploadPostProcessor);
        return this;
    }
    public List<FileUploadPostProcessor> getFileUploadPostProcessors() {
        return fileUploadPostProcessors;
    }

}
