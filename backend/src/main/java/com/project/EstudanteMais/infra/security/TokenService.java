package com.project.EstudanteMais.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.project.EstudanteMais.Entity.schoolAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  @Value("${tokenGenerate.secret}")
  private String secret;
  public String GenerateToken(UserDetails user){
      try{
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer("estudantemais")
                .withSubject(user.getUsername())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
        return token;
      }catch(JWTCreationException exception){
        throw new RuntimeException("Error on generating token", exception);
    }
  }

  public String validateToken(String token){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
              .withIssuer("estudantemais")
              .build()
              .verify(token)
              .getSubject();
    }catch(JWTCreationException exception){
      return "";
    }

  }

  private Instant genExpirationDate(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
