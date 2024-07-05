package com.keidson.algamoney_api.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pessoa")
public class PessoaModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotNull(message = "O campo [nome], é obrigatório.")
  @NotBlank(message = "O campo [nome], não poder estar em branco.")
	private String nome;

	@Embedded
  @JsonProperty("endereco")
	private EnderecoModel enderecoModel;

	@NotNull(message = "O campo [ativo], é obrigatório.")
	private Boolean ativo;

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

  public EnderecoModel getEnderecoModel() {
    return enderecoModel;
  }

  public void setEnderecoModel(EnderecoModel enderecoModel) {
    this.enderecoModel = enderecoModel;
  }

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
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
    PessoaModel other = (PessoaModel) obj;
    if (codigo == null) {
      if (other.codigo != null)
        return false;
    } else if (!codigo.equals(other.codigo))
      return false;
    return true;
  }
}
