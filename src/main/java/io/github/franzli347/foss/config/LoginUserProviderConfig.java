package io.github.franzli347.foss.config;

import io.github.franzli347.foss.support.userSupport.LoginUserProvider;
import io.github.franzli347.foss.support.userSupport.MockLoginUserProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FranzLi
 */
@Configuration
public class LoginUserProviderConfig {
    /**
     * TODO: 完成鉴权模块后切换
     * @return
     */
    @Bean
    public LoginUserProvider loginUserProvider() {
        return new MockLoginUserProvider();
    }
}
