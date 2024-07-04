package com.keidson.algamoney_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.keidson.algamoney_api.model.CategoriaModel;
import com.keidson.algamoney_api.repository.CategoriaRepository;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


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
}
