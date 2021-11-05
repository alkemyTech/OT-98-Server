package com.alkemy.ong.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  private static final String BASE_PACKAGE = "com.alkemy.ong.controller";
  private static final String TITLE = "SOMOS MAS";
  private static final String API_KEY_KEYNAME = "Authorization";
  private static final String API_KEY_NAME = "JWT";
  private static final String API_KEY_PASS_AS = "header";
  private static final String SCOPE = "global";
  private static final String SCOPE_DESCRIPTION = "accessEverything";
  private static final String DESCRIPTION = "Social Organization aimed to helping children and "
      + "families in vulnerable situations";

  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .info(new Info()
            .title(TITLE)
            .description(DESCRIPTION)
        );
  }

}
