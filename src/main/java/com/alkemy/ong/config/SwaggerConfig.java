package com.alkemy.ong.config;


import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
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
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()))
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey(API_KEY_NAME, API_KEY_KEYNAME, API_KEY_PASS_AS);
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope(SCOPE, SCOPE_DESCRIPTION);
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference(API_KEY_NAME, authorizationScopes));
  }

  private ApiInfo getApiInfo() {
    return new ApiInfoBuilder()
        .title(TITLE)
        .description(DESCRIPTION)
        .build();
  }

  /*
  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .info(new Info().title("Prueba"));
  }
  */

}
