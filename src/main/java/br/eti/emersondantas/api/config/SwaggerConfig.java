package br.eti.emersondantas.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
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
                .apis(RequestHandlerSelectors.basePackage("br.eti.emersondantas.api"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(metaData())
                .useDefaultResponseMessages(false);

    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("S-W-R-Social-Network REST API")
                .description("\"Spring Boot S-W-R-Social-Network REST API\"")
                .version("0.0.1")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .build();
    }

}
