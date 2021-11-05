package com.alkemy.ong;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@OpenAPIDefinition
public class OngApplication {

  public static void main(String[] args) {
    SpringApplication.run(OngApplication.class, args);
  }

}
