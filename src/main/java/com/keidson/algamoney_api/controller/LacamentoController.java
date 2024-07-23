package com.keidson.algamoney_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keidson.algamoney_api.model.LancamentoModel;
import com.keidson.algamoney_api.repository.LacamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LacamentoController {

  private final LacamentoRepository lancamentoRepository;

  public LacamentoController(LacamentoRepository lancamentoRepository) {
    this.lancamentoRepository = lancamentoRepository;
  }

  @GetMapping
  public List<LancamentoModel> listar() {
    return this.lancamentoRepository.findAll();
  }

}
