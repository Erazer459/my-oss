package io.github.franzli347.foss.config;

import io.github.franzli347.foss.support.DefaultFilePathResolver;
import io.github.franzli347.foss.support.FilePathResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilePathResolverConfig {
    @Bean
    public FilePathResolver filePathResolver() {
        return new DefaultFilePathResolver();
    }
}