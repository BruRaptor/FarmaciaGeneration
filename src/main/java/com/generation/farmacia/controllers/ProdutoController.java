package com.generation.farmacia.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.models.Produto;
import com.generation.farmacia.repositories.ProdutoRepository;

/*
 * @Author Thiago Batista 
 * 
 */

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;

	/*
	 * Pega todas as informações da tabela produto
	 * 
	 */
	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		List<Produto> list = repository.findAll();

		if (list.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(201).body(list);
		}
	}

	/*
	 * Pega informação da tabela baseada no id
	 * 
	 */

	@GetMapping("{id}")
	public ResponseEntity<Produto> getId(@PathVariable Long id) {
		return repository.findById(id).map(resp -> {
			return ResponseEntity.status(200).body(resp);
		}).orElseGet(() -> {
			return ResponseEntity.status(404).build();
		});
	}

	/*
	 * Pega informações da tabela baseado no nome do produto
	 * 
	 */
	@GetMapping("/produto/{produto}")
	public ResponseEntity<List<Produto>> getByName(@PathVariable String produto) {
		List<Produto> nome_produto = repository.findAllByProdutoContainingIgnoreCase(produto);

		if (nome_produto.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(nome_produto);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<Produto> savePost(@Valid @RequestBody Produto newPost) {
		return ResponseEntity.status(201).body(repository.save(newPost));
	}

	/*
	 * Atualiza informação no banco de dados
	 * 
	 */

	@PutMapping("/update")
	public ResponseEntity<Produto> updatePost(@Valid @RequestBody Produto updatePost) {
		return repository.findById(updatePost.getId()).map(record -> {
			return ResponseEntity.status(201).body(repository.save(updatePost));
		}).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado");
		});
	}

	/*
	 * Deleta informação no banco de dados
	 * 
	 */

	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity deletePost(@PathVariable("id") long id) {
		Optional<Produto> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();

		} else {
			return ResponseEntity.status(404).build();
		}
	}

}
