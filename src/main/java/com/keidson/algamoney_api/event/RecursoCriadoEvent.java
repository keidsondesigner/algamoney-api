package com.keidson.algamoney_api.event;

import org.springframework.context.ApplicationEvent;

import jakarta.servlet.http.HttpServletResponse;

public class RecursoCriadoEvent extends ApplicationEvent {

  private HttpServletResponse response;
  private Long codigo;

  public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
    super(source);
    this.response = response;
    this.codigo = codigo;
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public Long getCodigo() {
    return codigo;
  }  
}
