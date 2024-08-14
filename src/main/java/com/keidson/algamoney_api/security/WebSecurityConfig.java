package com.keidson.algamoney_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
      this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf(csrf -> csrf.disable())
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(authz -> authz
              .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
              .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
              .requestMatchers(HttpMethod.POST, "/lancamentos/**").hasAuthority("ROLE_LANCAMENTO_CADASTRAR")
              .requestMatchers(HttpMethod.GET, "/lancamentos/**").hasAuthority("ROLE_LANCAMENTO_PESQUISAR")
              .requestMatchers(HttpMethod.DELETE, "/lancamentos/**").hasAuthority("ROLE_LANCAMENTO_REMOVER")
              .requestMatchers(HttpMethod.POST, "/categorias/**").hasAuthority("ROLE_CATEGORIA_CADASTRAR")
              .requestMatchers(HttpMethod.GET, "/categorias/**").hasAuthority("ROLE_CATEGORIA_PESQUISAR")
              .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasAuthority("ROLE_CATEGORIA_REMOVER")
              .requestMatchers(HttpMethod.POST, "/pessoas/**").hasAuthority("ROLE_PESSOA_CADASTRAR")
              .requestMatchers(HttpMethod.GET, "/pessoas/**").hasAuthority("ROLE_PESSOA_PESQUISAR")
              .requestMatchers(HttpMethod.DELETE, "/pessoas/**").hasAuthority("ROLE_PESSOA_REMOVER")
              .requestMatchers(HttpMethod.PUT, "/pessoas/**").hasAuthority("ROLE_PESSOA_ATUALIZAR")

              .anyRequest().authenticated()
          ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
  }



  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
