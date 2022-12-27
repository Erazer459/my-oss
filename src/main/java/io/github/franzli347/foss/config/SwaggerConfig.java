package io.github.franzli347.foss.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi userApi(){
        String[] paths = { "/**" };
        String[] packagedToMatch = { "io.github.franzli347.foss.controller" };
        return GroupedOpenApi.builder().group("用户模块")
                .pathsToMatch(paths)
                /*.addOperationCustomizer((operation, handlerMethod) -> {
                    return operation.addParametersItem(new HeaderParameter().name("groupCode").example("测试").description("集团code").schema(new StringSchema()._default("BR").name("groupCode").description("集团code")));
                })*/
                .packagesToScan(packagedToMatch).build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("turing-oss")
                        .version("1.0")
                        .description( "turing-oss-API")
                );
                        /*.termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")*/
                                /*.url("http://doc.xiaominfo.com")))*/
    }
}