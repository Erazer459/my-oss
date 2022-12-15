package io.github.franzli347.foss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.github.franzli347.foss.mapper")
public class FOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(FOssApplication.class, args);
    }

}
