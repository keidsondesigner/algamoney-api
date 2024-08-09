package com.keidson.algamoney_api.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keidson.algamoney_api.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

  Optional<UsuarioModel> findByEmail(String email);

}
