package com.keidson.algamoney_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.keidson.algamoney_api.event.RecursoCriadoEvent;
import com.keidson.algamoney_api.model.CategoriaModel;
import com.keidson.algamoney_api.repository.CategoriaRepository;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

  private final CategoriaRepository categoriaRepository;
  private final ApplicationEventPublisher publisher; //Publicador de eventos RecursoCriadoEvent()

  public CategoriaController(CategoriaRepository categoriaRepository, ApplicationEventPublisher publisher) {
    this.categoriaRepository = categoriaRepository;
    this.publisher = publisher;
  }
  
  @GetMapping
  @PreAuthorize("hasAuthority('ROLE_CATEGORIA_PESQUISAR')")
  public List<CategoriaModel> listar() {
    return categoriaRepository.findAll();
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ROLE_CATEGORIA_CADASTRAR')")
  public ResponseEntity<CategoriaModel> criar(@Valid @RequestBody CategoriaModel categoriaModel, HttpServletResponse response) {
    CategoriaModel categoriaSalva = this.categoriaRepository.save(categoriaModel);

    publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

    return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
  }

  @GetMapping("/{codigo}")
  @PreAuthorize("hasAuthority('ROLE_CATEGORIA_PESQUISAR')")
  public ResponseEntity<CategoriaModel> buscarPorCodigo(@PathVariable Long codigo) {
    Optional<CategoriaModel> categoriaModelOptional = this.categoriaRepository.findById(codigo);
    return categoriaModelOptional.isPresent() ? 
      ResponseEntity.ok(categoriaModelOptional.get()) : 
      ResponseEntity.notFound().build();
  }
}
