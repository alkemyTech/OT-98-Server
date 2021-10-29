package com.alkemy.ong.config;

import com.alkemy.ong.common.JwtUtil;
import com.alkemy.ong.service.UserServiceImpl;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private static final String BEARER_PART = "Bearer ";
  private static final String EMPTY = "";
  private static final String AUTHORITIES = "authorities";

  @Autowired
  private UserServiceImpl userDetailService;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (isTokenSet(authorizationHeader)) {
      authentication(authorizationHeader);
    } else {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }

  private void authentication(String authorizationHeader) {
    String jwtToken = authorizationHeader.replace(BEARER_PART, EMPTY);
    Claims claims = jwtUtil.extractAllClaims(jwtToken);
    List<String> authorities = (List) claims.get(AUTHORITIES);
    if (authorities != null) {
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
          claims.getSubject(), null,
          authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
      SecurityContextHolder.getContext().setAuthentication(auth);
    } else {
      SecurityContextHolder.clearContext();
    }
  }

  private boolean isTokenSet(String authorizationHeader) {
    return authorizationHeader != null && authorizationHeader.startsWith(BEARER_PART);
  }

}
