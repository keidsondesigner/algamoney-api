package com.keidson.algamoney_api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.keidson.algamoney_api.model.PessoaModel;
import com.keidson.algamoney_api.repository.PessoaRepository;

@Service
public class PessoaService {

  private final PessoaRepository pessoaRepository;

	public PessoaService(PessoaRepository pessoaRepository) {
    this.pessoaRepository = pessoaRepository;
  }

  public List<PessoaModel> listar() {
      List<PessoaModel> pessoas = pessoaRepository.findAll();
      if (pessoas.isEmpty()) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma pessoa encontrada.");
      }
      return pessoas;
  }

  public PessoaModel buscarPeloCodigo(Long codigo) {
    return pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
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
