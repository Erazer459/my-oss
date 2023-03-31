package io.github.franzli347.foss.config;

import io.github.franzli347.foss.web.service.FilesService;
import io.github.franzli347.foss.support.fileSupport.DefaultFilePathResolver;
import io.github.franzli347.foss.support.fileSupport.FilePathResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilePathResolverConfig {
    @Bean
    public FilePathResolver filePathResolver(FilesService filesService) {
        return new DefaultFilePathResolver(filesService);
    }
}
