package com.keidson.algamoney_api.service;

import org.springframework.stereotype.Service;

import com.keidson.algamoney_api.exceptionHandler.PessoaInexistenteOuInativaException;
import com.keidson.algamoney_api.model.LancamentoModel;
import com.keidson.algamoney_api.model.PessoaModel;
import com.keidson.algamoney_api.repository.LacamentoRepository;
import com.keidson.algamoney_api.repository.PessoaRepository;


@Service
public class LancamentoService {

  private final PessoaRepository pessoaRepository;
  private final LacamentoRepository lacamentoRepository;

  public LancamentoService(PessoaRepository pessoaRepository, LacamentoRepository lacamentoRepository) {
    this.pessoaRepository = pessoaRepository;
    this.lacamentoRepository = lacamentoRepository;
  }

	public LancamentoModel salvar(LancamentoModel lancamento) {
    PessoaModel pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo())
        .orElseThrow(() -> new PessoaInexistenteOuInativaException());

    if (!pessoa.getAtivo()) {
      throw new PessoaInexistenteOuInativaException();
    }

    return lacamentoRepository.save(lancamento);
  }

}
