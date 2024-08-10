package com.keidson.algamoney_api.security;

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

    UsuarioModel usuarioModel = usuarioRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Email não encontrado"));
    return usuarioModel;
  }

}
