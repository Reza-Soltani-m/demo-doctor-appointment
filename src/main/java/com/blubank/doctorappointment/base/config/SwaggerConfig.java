package com.blubank.doctorappointment.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket productApiUI() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("All")
            .select().paths(regex("/api/.*"))
            .build()
            .useDefaultResponseMessages(false)
            .apiInfo(new ApiInfoBuilder().title("Doctor's Appointment Demo Project").version("1").build());
  }

}