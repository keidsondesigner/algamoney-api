package com.keidson.algamoney_api.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
    .allowedOrigins("http://localhost:4200", "https://algamoney-frontend-beige.vercel.app") // Substitua pelo domínio do seu aplicativo
    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    .allowedHeaders("*") // Permite todos os cabeçalhos
    .allowCredentials(true); // Permite credenciais (cookies, etc.)
  }

}
