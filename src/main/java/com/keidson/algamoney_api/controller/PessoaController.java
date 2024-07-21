package com.keidson.algamoney_api.controller;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private final PessoaRepository pessoaRepository;
	private final ApplicationEventPublisher publisher; //Publicador de eventos RecursoCriadoEvent()

	public PessoaController(PessoaRepository pessoaRepository, ApplicationEventPublisher publisher) {
    this.pessoaRepository = pessoaRepository;
    this.publisher = publisher;
  }

  @PostMapping
	public ResponseEntity<PessoaModel> criar(@Valid @RequestBody PessoaModel pessoa, HttpServletResponse response) {
		PessoaModel pessoaSalva = pessoaRepository.save(pessoa);		

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<PessoaModel> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<PessoaModel> pessoa = pessoaRepository.findById(codigo);
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		PessoaModel pessoa = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
    pessoaRepository.deleteById(pessoa.getCodigo());
	}

}
