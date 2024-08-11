package com.keidson.algamoney_api.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.keidson.algamoney_api.model.UsuarioModel;
import com.keidson.algamoney_api.repository.UsuarioRepository;

@Component
public class UserService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UsuarioModel usuarioModel = this.usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        return new org.springframework.security.core.userdetails.User(usuarioModel.getEmail(), usuarioModel.getPassword(), new ArrayList<>());
  }

}
