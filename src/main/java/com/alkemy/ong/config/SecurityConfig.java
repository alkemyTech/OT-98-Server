package com.alkemy.ong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] SWAGGER = {
      "/swagger-resources/**",
      "/swagger-ui/**", "/v2/api-docs",
      "/api/docs",
      "/api/docs/**",
      "/v3/api-docs/**",
      "/api/docs/swagger-ui",
      "/swagger-ui.html"};

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
    managerBuilder.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .cors()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(SWAGGER)
        .permitAll()
        .antMatchers(HttpMethod.POST, "/auth/login", "/auth/register")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/organization/public")
        .hasAnyRole(ApplicationRole.USER.getName(), ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.POST, "/organization/public")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.GET, "/auth/me")
        .hasAnyRole(ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.POST, "/news")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.DELETE, "/news/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.GET, "/news?{.+}")
        .hasAnyRole(ApplicationRole.ADMIN.getName(), ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.GET, "/news/{\\d+}")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.GET, "/news/{\\d+}/comments")
        .hasAnyRole(ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.PUT, "/news/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.POST, "/categories")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.GET, "/categories/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.DELETE, "/categories/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.POST, "/activities")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.GET, "/users")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.DELETE, "/users/**")
        .hasAnyRole(ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.POST, "/contacts")
        .hasAnyRole(ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.GET, "/contacts")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.POST, "/testimonials")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.GET, "/testimonials")
        .hasAnyRole(ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.DELETE, "/testimonials/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.PUT, "/testimonials/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.POST, "/comments")
        .hasAnyRole(ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.GET, "/members")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.PUT, "/members/**")
        .hasAnyRole(ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.DELETE, "/members/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.DELETE, "/slides/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.POST, "/slides")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.GET, "/slides/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.PATCH, "/users/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName(), ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.POST, "/members")
        .hasAnyRole(ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.PUT, "/categories/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.PUT, "/activities/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.DELETE, "/comments/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName(), ApplicationRole.USER.getName())
        .antMatchers(HttpMethod.GET, "/comments")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.PUT, "/comments/**")
        .hasAnyRole(ApplicationRole.USER.getName(), ApplicationRole.ADMIN.getName())
        .antMatchers(HttpMethod.PUT, "/slides/**")
        .hasAnyRole(ApplicationRole.ADMIN.getName())
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
  }
}
