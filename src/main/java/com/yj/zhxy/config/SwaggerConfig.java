package com.yj.zhxy.config;



import com.yj.zhxy.pojo.Admin;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YangJian
 * @date 2023/12/25 10:12
 * @description Swagger2配置信息
 */

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("yjzhxy-public")
                .pathsToMatch("/sms/gradeController/**","/sms/clazzController/**","/sms/studentController/**","/sms/teacherController/**","/sms/teacherController/**")
                .build();
    }
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("yjzhxy-admin")
                .pathsToMatch("/sms/system/**","/sms/adminController/**")
                // 接口过滤，据此增加接口扫描规则（扫描@Operation注解标注的接口）。想皮一下的话，亦可自定义注解
                //.addOpenApiMethodFilter(method -> method.isAnnotationPresent(Operation.class))
                .build();
    }
    @Bean
    public OpenAPI webApiInfo() {
        return new OpenAPI().info(
                new Info().title("智慧校园-API文档")
                        .description("本文档描述了网站微服务接口定义")
                        .version("1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                .description("智慧校园API文档")
                .url("http://www.yjzhxy.com"));
    }

}
