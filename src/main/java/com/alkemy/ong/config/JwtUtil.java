package com.alkemy.ong.config;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
  private static final String SECRET_KEY = "3c1dab2b9a676e66b332d2deef2129b0d775bcb8";

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

//  public String getUsernameFromToken(String token){
//    return extractAllClaims(token).;
//  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
  public Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(User user) {
    System.out.println("llego");
    System.out.println(user.getAuthorities());
    //List<String> claims = new List<String>();
   // Map<String,List<String>> claims2 = new HashMap<String,List<String>>();
    //claims = ;
    UserDetails userDetails = (UserDetails) user;
    List<String> claims = new ArrayList<String>();
    claims = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

    claims = user.getRoles().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    System.out.println(claims);
   // Map <String,String> map = new HashMap <String,String>();

    //claims2.put();
//    System.out.println(claims);
//    for(Role role :user.getRoles()) {
//      claims.add(role.getName());
//      //claims.put(role.getId().toString(),role.getDescription());
//
//    }
    //map.put("Roles",claims.get(0));
    return createToken(claims, user.getUsername());
  }

  private String createToken(List<String> claims, String subject) {

    return Jwts.builder()
        .claim("Roles",claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .signWith(SignatureAlgorithm.HS256,SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
