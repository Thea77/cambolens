package co.istad.cambolens.config;


import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
    private ApiInfo apiInfo() {
        return new ApiInfo("Cambolens Reading APIs",
                "APIs for Cambolens Reading App",
                "1.0",
                "Terms of service",
                new Contact("Cambolens", "www.org.com", "cambolen@emaildomain.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }


    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .apiInfo(apiInfo())
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
}
