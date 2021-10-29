package com.alkemy.ong.config;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.service.UserServiceImpl;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private static final String BEARER_PART = "Bearer ";
  private static final String SPACE = " ";
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
        User user = (User) userDetails;
        //Claims claims2 = jwtUtil.extractClaim(jwt,Claims::get);
         Claims claim = jwtUtil.extractAllClaims(jwt);
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) userDetails.getAuthorities();
        ;
        //List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) userDetails.getAuthorities();
//        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();


//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//            new UsernamePasswordAuthenticationToken(userDetails, null,
//                userDetails.getAuthorities());
//        usernamePasswordAuthenticationToken
//            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//        ;
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//            jwtUtil.extractClaim(jwt,Claims::getSubject),
//            null,
//            user.getRoles().stream().map(role -> {new SimpleGrantedAuthority(role.getName())}).collect(Collectors.toList()) );
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            username,
            null,
            userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//        claims.getSubject(),
//        null,
//            authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

      }
    }

//    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//        claims.getSubject(),
//        null,
//        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);`


    filterChain.doFilter(request, response);
  }

}
