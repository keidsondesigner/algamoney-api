package com.keidson.algamoney_api.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.keidson.algamoney_api.event.RecursoCriadoEvent;
import com.keidson.algamoney_api.model.PessoaModel;
import com.keidson.algamoney_api.repository.PessoaRepository;
import com.keidson.algamoney_api.service.PessoaService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private final PessoaRepository pessoaRepository;
	private final ApplicationEventPublisher publisher; //Publicador de eventos RecursoCriadoEvent()
	private final PessoaService pessoaService;

	public PessoaController(
		PessoaRepository pessoaRepository,
		ApplicationEventPublisher publisher,
		PessoaService pessoaService

	) {
    this.pessoaRepository = pessoaRepository;
    this.publisher = publisher;
		this.pessoaService = pessoaService;
  }

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESSOA_PESQUISAR')")
	public ResponseEntity<List<PessoaModel>> listar() {
		List<PessoaModel> pessoas = pessoaService.listar();
		return ResponseEntity.ok(pessoas);
	}

  @PostMapping
	@PreAuthorize("hasAuthority('ROLE_PESSOA_CADASTRAR')")
	public ResponseEntity<PessoaModel> criar(@Valid @RequestBody PessoaModel pessoa, HttpServletResponse response) {
		PessoaModel pessoaSalva = pessoaRepository.save(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESSOA_PESQUISAR')")
	public ResponseEntity<PessoaModel> buscarPeloCodigo(@PathVariable Long codigo) {
			PessoaModel pessoa = pessoaService.buscarPeloCodigo(codigo);
			return ResponseEntity.ok(pessoa);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_PESSOA_REMOVER')")
	public void remover(@PathVariable Long codigo) {
		PessoaModel pessoa = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
    pessoaRepository.deleteById(pessoa.getCodigo());
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESSOA_ATUALIZAR')")
	public ResponseEntity<PessoaModel> atualizar(@PathVariable Long codigo, @Valid @RequestBody PessoaModel pessoa) {
		PessoaModel pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}

	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_PESSOA_ATUALIZAR')")
	public void atualizaPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizaPropriedadeAtivo(codigo, ativo);
	}

}
