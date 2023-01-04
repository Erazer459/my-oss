package io.github.franzli347.foss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("io.github.franzli347.foss.mapper")
@EnableTransactionManagement
@EnableAsync
public class FOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(FOssApplication.class, args);
    }

}
