package com.keidson.algamoney_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keidson.algamoney_api.model.CategoriaModel;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> { }
