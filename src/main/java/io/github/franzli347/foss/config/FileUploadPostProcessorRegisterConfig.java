package io.github.franzli347.foss.config;

import io.github.franzli347.foss.service.FilesService;
import io.github.franzli347.foss.support.fileSupport.DBFileUploadPostprocessor;
import io.github.franzli347.foss.support.fileSupport.FileCompressPostProcessor;
import io.github.franzli347.foss.support.fileSupport.FileUploadPostProcessorRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author FranzLi
 */
@Configuration
public class FileUploadPostProcessorRegisterConfig {

    @Bean
    public DBFileUploadPostprocessor dbFileUploadPostprocessor(FilesService filesService){
        return new DBFileUploadPostprocessor(filesService);
    }
    @Bean
    public FileCompressPostProcessor fileCompressPostProcessor(){
        return new FileCompressPostProcessor();
    }
    @Bean
    public FileUploadPostProcessorRegister fileUploadPostProcessorRegister(DBFileUploadPostprocessor dbFileUploadPostprocessor, FileCompressPostProcessor fileCompressPostProcessor) {
        return new FileUploadPostProcessorRegister() {}
        .register((filePath, param) -> {
            System.out.println("filePath = " + filePath);
            return true;
        }).register(fileCompressPostProcessor)
                .register(dbFileUploadPostprocessor);
    }


}
