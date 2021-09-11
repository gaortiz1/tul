package com.tul.shoppingcart.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun shoppingCartApi(): Docket =
            Docket(DocumentationType.SWAGGER_2)
                    .groupName("api")
                    .forCodeGeneration(false)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.tul.shoppingcart.controller"))
                    .paths(PathSelectors.any())
                    .build()

    private fun apiInfo(): ApiInfo =
            ApiInfoBuilder()
                    .title("Tul Services REST API")
                    .version("1.0.0")
                    .build()
}