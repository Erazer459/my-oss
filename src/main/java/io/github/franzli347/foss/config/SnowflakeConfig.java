package io.github.franzli347.foss.config;

import io.github.franzli347.foss.utils.SnowflakeDistributeId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {
    @Value("${Snowflake.workerId}")
    private long workerId;

    @Value("${Snowflake.datacenterId}")
    private long datacenterId;

    @Bean
    public SnowflakeDistributeId snowflakeDistributeId(){
        return new SnowflakeDistributeId(workerId,datacenterId);
    }
}
