package io.github.franzli347.foss.config;

import io.github.franzli347.foss.support.FileUploadPostProcessorRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadPostProcessorRegisterConfig {
    @Bean
    public FileUploadPostProcessorRegister fileUploadPostProcessorRegister() {
        FileUploadPostProcessorRegister fileUploadPostProcessorRegister = new FileUploadPostProcessorRegister() {
        };
        // 这里注册后处理器
        fileUploadPostProcessorRegister.register((filePath, param) -> {
            System.out.println("filePath = " + filePath);
            return true;
        });
        return fileUploadPostProcessorRegister;
    }
}
