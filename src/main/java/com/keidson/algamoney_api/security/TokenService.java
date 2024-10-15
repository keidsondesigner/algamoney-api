package com.keidson.algamoney_api.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.keidson.algamoney_api.model.PermissaoModel;
import com.keidson.algamoney_api.model.UsuarioModel;

@Service
public class TokenService {
  @Value("${jwt.secret}")
  private String jwtSecret;

  public String generateToken(UsuarioModel usuarioModel) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

      // Obter as permissões do usuário
      List<String> permissoes = usuarioModel.getPermissoes().stream()
          .map(PermissaoModel::getDescricao)
          .collect(Collectors.toList());

      String token = JWT.create()
          .withIssuer("login-auth-api")
          .withSubject(usuarioModel.getEmail())
          .withExpiresAt(this.generateExpirationDate())
          .withArrayClaim("permissoes", permissoes.stream().map(String::valueOf).toArray(String[]::new)) // Adiciona as permissões como um array de strings
          .sign(algorithm);
      return token;
    } catch (Exception e) {
      throw new RuntimeException("Error while authenticating user");
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

      return JWT.require(algorithm)
          .withIssuer("login-auth-api")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException e) {
      return null;
    }
  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
