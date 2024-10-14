package com.keidson.algamoney_api.controller;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keidson.algamoney_api.dto.LoginRequestDTO;
import com.keidson.algamoney_api.dto.RegisterRequestDTO;
import com.keidson.algamoney_api.dto.ResponseDTO;
import com.keidson.algamoney_api.model.PermissaoModel;
import com.keidson.algamoney_api.model.UsuarioModel;
import com.keidson.algamoney_api.repository.UsuarioRepository;
import com.keidson.algamoney_api.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  public AuthController(
    TokenService tokenService,
    PasswordEncoder passwordEncoder,
    UsuarioRepository usuarioRepository
  ) {
    this.tokenService = tokenService;
    this.passwordEncoder = passwordEncoder;
    this.usuarioRepository = usuarioRepository;
  }

  @PostMapping("/login")
public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
    UsuarioModel user = this.usuarioRepository.findByEmail(body.email())
        .orElseThrow(() -> new RuntimeException("Email não encontrado"));

    if (this.passwordEncoder.matches(body.senha(), user.getSenha())) {
        String token = this.tokenService.generateToken(user);
        
        // Obter as permissões do usuário
        Set<String> permissoes = user.getPermissoes().stream()
            .map(PermissaoModel::getDescricao)
            .collect(Collectors.toSet());
        
        return ResponseEntity.ok(new ResponseDTO(user.getEmail(), token, permissoes));
    }

    return ResponseEntity.badRequest().build();
}

@PostMapping("/register")
public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
    Optional<UsuarioModel> user = this.usuarioRepository.findByEmail(body.email());

    if (user.isEmpty()) {
        UsuarioModel newUser = new UsuarioModel();
        newUser.setSenha(passwordEncoder.encode(body.senha()));
        newUser.setEmail(body.email());
        newUser.setNome(body.nome());
        this.usuarioRepository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        
        // Obter as permissões do novo usuário (pode ser um conjunto vazio se não houver permissões)
        Set<String> permissoes = newUser.getPermissoes().stream()
            .map(PermissaoModel::getDescricao)
            .collect(Collectors.toSet());

        return ResponseEntity.ok(new ResponseDTO(newUser.getEmail(), token, permissoes));
    }

    return ResponseEntity.badRequest().build();
}
}
