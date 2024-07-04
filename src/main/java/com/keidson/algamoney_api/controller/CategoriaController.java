package com.keidson.algamoney_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.keidson.algamoney_api.model.CategoriaModel;
import com.keidson.algamoney_api.repository.CategoriaRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;



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
  @ResponseStatus(HttpStatus.CREATED)
  public void criar(@RequestBody CategoriaModel categoriaModel) {
    this.categoriaRepository.save(categoriaModel);
  }
}
