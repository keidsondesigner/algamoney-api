package com.keidson.algamoney_api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.keidson.algamoney_api.model.PessoaModel;
import com.keidson.algamoney_api.repository.PessoaRepository;

@Service
public class PessoaService {

  private final PessoaRepository pessoaRepository;

	public PessoaService(PessoaRepository pessoaRepository) {
    this.pessoaRepository = pessoaRepository;
  }

  public PessoaModel atualizar(Long codigo, PessoaModel pessoa) {
    PessoaModel pessoaSalva = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));

    BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
    return pessoaRepository.save(pessoaSalva);
  }

  public void atualizaPropriedadeAtivo(Long codigo, Boolean ativo){
    PessoaModel pessoaSalva = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
    pessoaSalva.setAtivo(ativo);
    pessoaRepository.save(pessoaSalva);
  }
}
