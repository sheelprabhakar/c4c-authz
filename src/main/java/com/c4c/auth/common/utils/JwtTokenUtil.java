package com.c4c.auth.common.utils;

import static com.c4c.auth.common.Constants.AUTHORITIES_KEY;
import static com.c4c.auth.common.Constants.TOKEN_LIFETIME_SECONDS;

import com.c4c.auth.core.models.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

  @Value("${app.jwt.secret.key}")
  private String jwtSecretKey;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);

    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String createTokenFromAuth(Authentication authentication) {
    return generateToken(authentication.getName());
  }

  public String createTokenFromUser(User user) {
    return generateToken(user.getEmail());
  }

  private String generateToken(String username) {
    long currentTimestampInMillis = System.currentTimeMillis();

    return Jwts.builder()
        .setSubject(username)
        .claim(AUTHORITIES_KEY, "")
        .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
        .setIssuedAt(new Date(currentTimestampInMillis))
        .setExpiration(new Date(currentTimestampInMillis + (TOKEN_LIFETIME_SECONDS * 1000)))
        .compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);

    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
