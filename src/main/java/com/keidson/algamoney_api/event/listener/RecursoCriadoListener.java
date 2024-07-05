package com.keidson.algamoney_api.event.listener;

import java.net.URI;

import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.keidson.algamoney_api.event.RecursoCriadoEvent;

import jakarta.servlet.http.HttpServletResponse;

public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

  @Override
  public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
    HttpServletResponse response = recursoCriadoEvent.getResponse();
    Long codigo = recursoCriadoEvent.getCodigo();

    adicionarHeaderLocation(response, codigo);
  }

  private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
        .buildAndExpand(codigo).toUri();

    response.setHeader("Location", uri.toASCIIString());
  }
}
