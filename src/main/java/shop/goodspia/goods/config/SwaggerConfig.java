package shop.goodspia.goods.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi artistApi() {
        return GroupedOpenApi.builder()
                .group("Artist")
                .packagesToScan("shop.goodspia.goods.controller")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .version("v1.0.0")
                .title("GoodsPia API")
                .description("GoodsPia CRUD API");
    }
}