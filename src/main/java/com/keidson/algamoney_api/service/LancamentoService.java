package com.keidson.algamoney_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public List<LancamentoModel> filtrarLancamentos(String descricao, LocalDate dataVencimentoDe, LocalDate dataVencimentoAte) {
    return lacamentoRepository.findByDescricaoAndDataVencimento(descricao, dataVencimentoDe, dataVencimentoAte);
  }

  @Transactional
  public void remove(Long codigo) {
    // Buscar o lançamento pelo ID
    Optional<LancamentoModel> lancamento = this.lacamentoRepository.findById(codigo);
    // Verificar se o lançamento não foi encontrado lançar uma exceção
    if (lancamento.isEmpty()) {
        throw new EmptyResultDataAccessException(1);
    }
    // Se o lançamento foi encontrado, removê-lo
    this.lacamentoRepository.deleteById(codigo);
  }

}
