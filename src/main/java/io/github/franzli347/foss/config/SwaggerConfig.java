package io.github.franzli347.foss.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FranzLi
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi userApi(){
        String[] paths = { "/**" };
        String[] packagedToMatch = { "io.github.franzli347.foss.web.controller" };
        return GroupedOpenApi.builder().group("用户模块")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("turing-oss")
                        .version("1.0")
                        .description( "turing-oss-API")
                ).schemaRequirement("token",this.securityScheme())
                .addSecurityItem(new SecurityRequirement().addList("token"));
    }

    private SecurityScheme securityScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.APIKEY);
        securityScheme.setName("token");
        securityScheme.setIn(SecurityScheme.In.HEADER);
        return securityScheme;
    }

}