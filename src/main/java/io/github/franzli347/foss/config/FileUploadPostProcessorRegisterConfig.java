package io.github.franzli347.foss.config;

import io.github.franzli347.foss.service.FilesService;
import io.github.franzli347.foss.support.DBFileUploadPostprocessor;
import io.github.franzli347.foss.support.FileUploadPostProcessorRegister;
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
    public FileUploadPostProcessorRegister fileUploadPostProcessorRegister(DBFileUploadPostprocessor dbFileUploadPostprocessor) {
        return new FileUploadPostProcessorRegister() {}
        .register((filePath, param) -> {
            System.out.println("filePath = " + filePath);
            return true;
        })
        .register(dbFileUploadPostprocessor);
    }



}
