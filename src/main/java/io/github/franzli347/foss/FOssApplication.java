package io.github.franzli347.foss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@MapperScan("io.github.franzli347.foss.mapper")
@EnableTransactionManagement
@EnableWebSocket
public class FOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(FOssApplication.class, args);
    }

}
