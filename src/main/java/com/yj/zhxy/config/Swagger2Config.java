package com.yj.zhxy.config;



import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
import java.util.List;

/**
 * @author YangJian
 * @date 2023/12/25 10:12
 * @description Swagger2配置信息
 */

@Configuration
public class Swagger2Config {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(
                new Info().title("网站-API文档")
                        .description("十六进制说API文档")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                .description("外部文档")
                .url("https://springshop.wiki.github.org/docs"));
    }

}
