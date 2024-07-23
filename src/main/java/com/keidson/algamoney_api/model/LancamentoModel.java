package com.keidson.algamoney_api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "lancamento")
public class LancamentoModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long codigo;

  @NotNull
  private String descricao;

  @NotNull
  @Column(name = "data_vencimento")
  private LocalDate dataVencimento;

  @Column(name = "data_pagamento")
  private LocalDate dataPagamento;

  @NotNull
  private BigDecimal valor;

  private String observacao;

  @NotNull
  @Enumerated(EnumType.STRING)
  private TipoLancamento tipo; // "TipoLancamento" é um enum criado;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "codigo_categoria")
  private CategoriaModel categoria; // Relacionamento: vários Lançamento tem 1 Categoria

  @NotNull
  @ManyToOne
  @JoinColumn(name = "codigo_pessoa")
  private PessoaModel pessoa; // Relacionamento: vários Lançamento tem 1 Pesssoa

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public LocalDate getDataVencimento() {
    return dataVencimento;
  }

  public void setDataVencimento(LocalDate dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

  public LocalDate getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(LocalDate dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public TipoLancamento getTipo() {
    return tipo;
  }

  public void setTipo(TipoLancamento tipo) {
    this.tipo = tipo;
  }

  public CategoriaModel getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaModel categoria) {
    this.categoria = categoria;
  }

  public PessoaModel getPessoa() {
    return pessoa;
  }

  public void setPessoa(PessoaModel pessoa) {
    this.pessoa = pessoa;
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
    LancamentoModel other = (LancamentoModel) obj;
    if (codigo == null) {
      if (other.codigo != null)
        return false;
    } else if (!codigo.equals(other.codigo))
      return false;
    return true;
  }
}
