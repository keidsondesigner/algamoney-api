package com.keidson.algamoney_api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.keidson.algamoney_api.model.CategoriaModel;
import com.keidson.algamoney_api.repository.CategoriaRepository;

import jakarta.servlet.http.HttpServletResponse;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

  private CategoriaRepository categoriaRepository;

  public CategoriaController(CategoriaRepository categoriaRepository) {
    this.categoriaRepository = categoriaRepository;
  }
  
  @GetMapping
  public List<CategoriaModel> listar() {
    return categoriaRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<CategoriaModel> criar(@Valid @RequestBody CategoriaModel categoriaModel, HttpServletResponse response) {
    CategoriaModel categoriaSalva = this.categoriaRepository.save(categoriaModel);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
        .buildAndExpand(categoriaSalva.getCodigo()).toUri();

    response.setHeader("Location", uri.toASCIIString());
    return ResponseEntity.created(uri).body(categoriaSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<CategoriaModel> buscarPorCodigo(@PathVariable Long codigo) {
    Optional<CategoriaModel> categoriaModelOptional = this.categoriaRepository.findById(codigo);
    return categoriaModelOptional.isPresent() ? 
      ResponseEntity.ok(categoriaModelOptional.get()) : 
      ResponseEntity.notFound().build();
  }
}
