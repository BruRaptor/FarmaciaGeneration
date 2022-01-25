package com.generation.farmacia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.farmacia.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	public List <Produto> findAllByProdutoContainingIgnoreCase(String produto);
	public List <Produto> findAll();
}
