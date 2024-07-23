package com.keidson.algamoney_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keidson.algamoney_api.event.RecursoCriadoEvent;
import com.keidson.algamoney_api.model.LancamentoModel;
import com.keidson.algamoney_api.repository.LacamentoRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/lancamentos")
public class LacamentoController {

  private final LacamentoRepository lancamentoRepository;
  private final ApplicationEventPublisher publisher; //Publicador de eventos RecursoCriadoEvent()

  public LacamentoController(
    LacamentoRepository lancamentoRepository,
    ApplicationEventPublisher publisher
  ) {
    this.lancamentoRepository = lancamentoRepository;
    this.publisher = publisher;
  }

  @GetMapping
  public List<LancamentoModel> listar() {
    return this.lancamentoRepository.findAll();
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<LancamentoModel> buscarPorCodigo(@PathVariable Long codigo) {
    Optional<LancamentoModel> lancamento = this.lancamentoRepository.findById(codigo);
    return lancamento.isPresent() ? 
      ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<LancamentoModel> criar(@Valid @RequestBody LancamentoModel lancamentoModel, HttpServletResponse response) {
    LancamentoModel lancamentoSalvo = this.lancamentoRepository.save(lancamentoModel);

    publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));

    return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
  }

}
