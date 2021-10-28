package com.alkemy.ong.config;
import com.alkemy.ong.service.UserServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private static final String BEARER_PART = "Bearer ";

  @Autowired
  private UserServiceImpl userDetailService;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String username = null;
    String jwt = null;
    final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    Boolean isTokenSet = authorizationHeader != null && authorizationHeader.startsWith(BEARER_PART);

    if (isTokenSet) {
        jwt = authorizationHeader.replace(BEARER_PART, SPACE);
        username = jwtUtil.extractUsername(jwt);
    }

    Boolean userAuthenticated = username != null &&
        SecurityContextHolder.getContext().getAuthentication() == null;

    if (userAuthenticated) {
      UserDetails userDetails = userDetailService.loadUserByUsername(username);
      if (jwtUtil.validateToken(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }

}
