package com.keidson.algamoney_api.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.keidson.algamoney_api.event.RecursoCriadoEvent;
import com.keidson.algamoney_api.exceptionHandler.PessoaInexistenteOuInativaException;
import com.keidson.algamoney_api.exceptionHandler.AlgamoneyExceptionHandler.Erro;
import com.keidson.algamoney_api.model.LancamentoModel;
import com.keidson.algamoney_api.repository.LacamentoRepository;
import com.keidson.algamoney_api.service.LancamentoService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/lancamentos")
public class LacamentoController {

  private final LacamentoRepository lancamentoRepository;
  private final ApplicationEventPublisher publisher; //Publicador de eventos RecursoCriadoEvent()
  private final LancamentoService lancamentoService;
  private final MessageSource messageSource;

  public LacamentoController(
    LacamentoRepository lancamentoRepository,
    ApplicationEventPublisher publisher,
    LancamentoService lancamentoService,
    MessageSource messageSource
  ) {
    this.lancamentoRepository = lancamentoRepository;
    this.publisher = publisher;
    this.lancamentoService = lancamentoService;
    this.messageSource = messageSource;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ROLE_LANCAMENTO_PESQUISAR')")
  public List<LancamentoModel> listar() {
    return this.lancamentoRepository.findAll();
  }

  @GetMapping("/paginados")
  @PreAuthorize("hasAuthority('ROLE_LANCAMENTO_PESQUISAR')")
  public Page<LancamentoModel> listarPaginados(@PageableDefault(size = 5) Pageable page) {
    return this.lancamentoRepository.findAll(page);
  }

  @GetMapping("/{codigo}")
  @PreAuthorize("hasAuthority('ROLE_LANCAMENTO_PESQUISAR')")
  public ResponseEntity<LancamentoModel> buscarPorCodigo(@PathVariable Long codigo) {
    Optional<LancamentoModel> lancamento = this.lancamentoRepository.findById(codigo);
    return lancamento.isPresent() ? 
      ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
  }

  @GetMapping("/filtrar")
  @PreAuthorize("hasAuthority('ROLE_LANCAMENTO_PESQUISAR')")
  public ResponseEntity<List<LancamentoModel>> filtrarLancamentos(
      @RequestParam(required = false) String descricao,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimentoDe,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimentoAte
    ) {
    
    List<LancamentoModel> lancamentos = lancamentoService.filtrarLancamentos(descricao, dataVencimentoDe, dataVencimentoAte);
    return ResponseEntity.ok(lancamentos);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ROLE_LANCAMENTO_CADASTRAR')")
  public ResponseEntity<LancamentoModel> criar(@Valid @RequestBody LancamentoModel lancamentoModel, HttpServletResponse response) {
    LancamentoModel lancamentoSalvo = this.lancamentoService.salvar(lancamentoModel);

    publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
    return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('ROLE_LANCAMENTO_REMOVER')")
  public void remover(@PathVariable Long codigo) {
    this.lancamentoService.remove(codigo);
  }

  @ExceptionHandler({ PessoaInexistenteOuInativaException.class })
  public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
    String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
    String mensagemDesenvolvedor = ex.toString();
    List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor)); 
    return ResponseEntity.badRequest().body(erros);
  }

}
