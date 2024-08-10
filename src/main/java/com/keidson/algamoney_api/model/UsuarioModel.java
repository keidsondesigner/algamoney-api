package com.keidson.algamoney_api.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class UsuarioModel implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long codigo;

  @NotNull(message = "O campo [nome], é obrigatório.")
  @NotBlank(message = "O campo [nome], não poder estar em branco.")
  @Size(min = 5, max = 50, message = "O campo [nome], deve possuir no minimo {min} caracteres e no máximo {max}")
  private String nome;

  @Column(unique = true)
  @NotNull(message = "O campo [email], é obrigatório.")
  @NotBlank(message = "O campo [email], não poder estar em branco.")
  @Size(min = 5, max = 50, message = "O campo [senha], deve possuir no minimo {min} caracteres e no máximo {max}")
  private String email;

  @NotNull(message = "O campo [senha], é obrigatório.")
  @NotBlank(message = "O campo [senha], não poder estar em branco.")
  @Length(min = 5, max = 100, message = "O campo [senha], deve conter entre 5 a 100 caracteres.")
  private String senha;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "usuario_permissao", // Nome da tabela intermediaria, entre tabela usuario e tabela permissao;
    joinColumns = @JoinColumn(name = "codigo_usuario"), // FK - Relacionamento entre tabela usuario;
    inverseJoinColumns = @JoinColumn(name = "codigo_permissao") // FK - Relacionamento entre tabela permissao;
  )
  private Set<PermissaoModel> permissoes = new HashSet<>();

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Set<PermissaoModel> getPermissoes() {
    return permissoes;
  }

  public void setPermissoes(Set<PermissaoModel> permissoes) {
    this.permissoes = permissoes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<SimpleGrantedAuthority> authorities = new HashSet<>();
      for (PermissaoModel permissao : permissoes) {
          authorities.add(new SimpleGrantedAuthority(permissao.getDescricao()));
      }
      return authorities;
  }

  @Override
  public String getPassword() {
    return this.senha;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UsuarioModel other = (UsuarioModel) obj;
    if (codigo == null) {
      if (other.codigo != null)
        return false;
    } else if (!codigo.equals(other.codigo))
      return false;
    return true;
  }

}
