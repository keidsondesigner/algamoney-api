package com.keidson.algamoney_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keidson.algamoney_api.model.PessoaModel;

public interface PessoaRepository extends JpaRepository<PessoaModel, Long> { }
