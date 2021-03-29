package com.vandelay.rherb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Vandelay Industries API - DRAFT")
                .description("Vandelay Industries is a vertically integrated chemical manufaturing, import/export, and sales enterprise headquartered in the Upper West Side, NYC. It was founded by Art Vandely in 1990.\n" +
                        "\n" +
                        "It is known both for the quality of its product as well as its aggressive salesperson recruiting program.\n" +
                        "\n" +
                        "The Vandelay Industries API platform, documented here, represents a unified view of its ERP and CRM systems for use by both internal applications and 3rd-party strategic partner system integrations.")
                .termsOfServiceUrl("")
                .version("0.0.1")
                .contact(new Contact("Ryan Herb", "https://github.com/rherb94/Vandelay-API", "rherb94@gmail.com"))
                .build();
    }
}